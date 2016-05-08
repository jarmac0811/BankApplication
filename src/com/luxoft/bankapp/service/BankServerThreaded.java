package com.luxoft.bankapp.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class BankServerThreaded {
	private static int POOL_SIZE = 10;
	private ServerSocket serverSocket;
	private ExecutorService pool;
	private static AtomicInteger counterAll=new AtomicInteger(0);
	Object object=new Object();

	void run() {
		BankApplication.initialize();
		pool = Executors.newFixedThreadPool(POOL_SIZE);
		BankServerMonitor monitor = new BankServerMonitor();
		Thread t = new Thread(monitor);
		t.start();
		try
		{
			serverSocket = new ServerSocket(2004);
		} catch (IOException e)

		{
			e.printStackTrace();
		}
		while (true) {
			Socket clientSocket;
			try {
				clientSocket = serverSocket.accept();
				counterAll.incrementAndGet();			
				pool.execute(new ServerThread(clientSocket));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static synchronized void decrement() {
		counterAll.decrementAndGet();
	}

	public static int getPOOL_SIZE() {
		return POOL_SIZE;
	}

	public synchronized static AtomicInteger getCounter() {
		return counterAll;
	}

	public static void main(final String args[]) {
		BankServerThreaded server = new BankServerThreaded();
		server.run();
	}
}