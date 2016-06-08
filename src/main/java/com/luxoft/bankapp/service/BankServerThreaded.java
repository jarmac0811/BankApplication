package main.java.com.luxoft.bankapp.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
@Service
public class BankServerThreaded {
	private  int POOL_SIZE = 10;
	private ServerSocket serverSocket;
	private ExecutorService pool;
	private static AtomicInteger counterAll=new AtomicInteger(0);
	Object object=new Object();
	@Autowired
	private BankApplication bankApp;
	private static final Logger logger = Logger.getLogger(BankServerThreaded.class.getName());
	private static ApplicationContext context = new ClassPathXmlApplicationContext("file:resources/application-context.xml");
	void run() {
		bankApp.initialize();
		pool = Executors.newFixedThreadPool(POOL_SIZE);
		BankServerMonitor monitor = new BankServerMonitor();
		Thread t = new Thread(monitor);
		t.start();
		try
		{
			serverSocket = new ServerSocket(2004);
		} catch (IOException e)

		{
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		while (true) {
			Socket clientSocket;
			try {
				clientSocket = serverSocket.accept();
				counterAll.incrementAndGet();			
				pool.execute(new ServerThread(clientSocket));

			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	public BankApplication getBankApp() {
		return bankApp;
	}

	public void setBankApp(BankApplication bankApp) {
		this.bankApp = bankApp;
	}

	public static synchronized void decrement() {
		counterAll.decrementAndGet();
	}

	public  int getPOOL_SIZE() {
		return POOL_SIZE;
	}

	public synchronized static AtomicInteger getCounter() {
		return counterAll;
	}

	public static void main(final String args[]) {
		BankServerThreaded server =(BankServerThreaded)context.getBean("bankServerThreaded");
		server.run();
	}
}