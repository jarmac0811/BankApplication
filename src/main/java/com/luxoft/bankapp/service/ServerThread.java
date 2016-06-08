package main.java.com.luxoft.bankapp.service;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import main.java.com.luxoft.bankapp.exceptions.BankException;
import main.java.com.luxoft.bankapp.model.Account;
import main.java.com.luxoft.bankapp.model.Bank;
import main.java.com.luxoft.bankapp.model.Client;
@Service
public class ServerThread implements Runnable {
	public ServerThread() {
		super();
	}

	private Socket connection;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String name, option, amount;
	private boolean finish;
	@Autowired
	private Bank bank;
	private Client client;
	@Resource
	private BankService bankService;
	

	private static final  Logger logger = Logger.getLogger(ServerThread.class.getName());
	private static ApplicationContext context = new ClassPathXmlApplicationContext("file:resources/application-context.xml");
	ServerThread(Socket socket) {
		connection = socket;
	}

	@Override
	public void run() {
		//bankService = new BankServiceImpl();
		bank = new BankApplication().getBank();
		bankService=(BankService)context.getBean("bankService");
		

		try {

			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			sendMessage("Connection successful");
			do {
				try {
					option = (String) in.readObject();
					 System.out.println("reading option"+option);
					switch (option) {
					case "11":
						getBalance();
						break;
					case "21":
						withdraw();
						break;
					case "31":
						finish = true;
						break;
					case "10":
						getStatistics();
						break;
					case "20":
						findClientByName();
						break;
					case "30":
						addClient();
						break;
					default:
						System.out.println("not known operation");
					}

				} catch (ClassNotFoundException classnot) {
					System.err.println("Data received in unknown format");
				}

			} while (!finish);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				BankServerThreaded.decrement();
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	private void getBalance() {
		try {
			name = (String) in.readObject();
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		client = bankService.getClient(bank, name);
		amount = Float.toString(client.activeAccount.getBalance());
		sendMessage(amount);
	}

	private void withdraw() {
		try {
			name = (String) in.readObject();
			client = bankService.getClient(bank, name);
			amount = (String) in.readObject();
			client.activeAccount.withdraw(Float.parseFloat(amount));
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (BankException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

	}

	private void getStatistics() {
		int numberOfClients = bank.getClients().size();
		int sum = 0;
		for (Client client : bank.getClients())
			sum += client.getAccounts().size();
		float sumOfcredits = 0;
		for (Client client : bank.getClients())
			for (Account account : client.getAccounts())
				if (account instanceof CheckingAccount)
					if (account.getBalance() < 0)
						sumOfcredits += account.getBalance();
		BankInfo info = new BankInfo();
		info.setNumberOfAccounts(sum);
		info.setNumberOfClients(numberOfClients);
		info.setSumOfCredits(sumOfcredits);
		try {
			out.writeObject(info);
			out.flush();
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void findClientByName() {
		try { 
			name = (String) in.readObject();
			System.out.println("reading name"+name);
			
			System.out.println(bank.toString());
			client = bankService.getClient(bank, name);
			client.printReport();
			System.out.println("getting client from bank"+bank.getName());
			System.out.println("writing object to client"+client.getName());
			out.writeObject(client);

			out.flush();
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (EOFException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

	}

	private void addClient() {
		try {
			client = (Client) in.readObject();
			try {
				bankService.addClient(bank, client);
			} catch (ClientExistsException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} 

	}

	void sendMessage(final String msg) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public BankService getBanService() {
		return bankService;
	}

	public void setBanService(BankService banService) {
		this.bankService = banService;
	}
}
