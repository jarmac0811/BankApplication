package com.myapp.bankapp.service;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.myapp.bankapp.exceptions.BankException;
import com.myapp.bankapp.model.Account;
import com.myapp.bankapp.model.Bank;
import com.myapp.bankapp.model.Client;

public class BankServer {
	private ServerSocket providerSocket;
	private Socket connection = null;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message, name, option, amount;
	private Bank bank;
	private Client client;
	private BankService banService;
	private boolean finish;
	private Logger logger;
	private Logger logB;
	
	
	public BankServer() {
		logger = Logger.getLogger(BankServer.class.getName());
		logB = Logger.getLogger("LogB");
		try {
			
			LogManager.getLogManager().readConfiguration(new FileInputStream("logger.properties"));
		} catch (SecurityException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	void run() {
		banService = new BankServiceImpl();
		new BankApplication().initialize();
		bank = new BankApplication().getBank();
		Logger logger = Logger.getLogger(BankServer.class.getName());

		try {
			providerSocket = new ServerSocket(2004, 10);
			System.out.println("Waiting for connection");
			connection = providerSocket.accept();
			logger.log(Level.INFO,new Date().toString());
			logB.log(Level.INFO," New client attached to the server");
			System.out.println("Connection received from " + connection.getInetAddress().getHostName());
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			sendMessage("Connection successful");
			
			do {
				try {
					
					option = (String) in.readObject();
					switch (option) {
					case "11":
						getBalance();
						
						break;
					case "21":
						withdraw();
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
					case "40":
						exit();
						break;
					default:
						System.out.println("not known operation");
						throw new Exception("Test");
						

					}

				} catch (ClassNotFoundException classnot) {
					System.err.println("Data received in unknown format");
				}catch (Exception ex) {
		            logger.log(Level.SEVERE, ex.getMessage(), ex);
		        }

			} while (!finish);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} 
		
		finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				providerSocket.close();
				logger.log(Level.INFO,new Date().toString());
				logB.log(Level.INFO," Client detached from the server");
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}
private void exit(){
	finish=true;
	
}
	private void getBalance() {
		try {
			name = (String) in.readObject();
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		client = banService.getClient(bank, name);
		amount = Float.toString(client.activeAccount.getBalance());
		sendMessage(amount);
	}

	private void withdraw() {
		try {
			name = (String) in.readObject();
			client = banService.getClient(bank, name);
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
		}
		 catch (IOException e) {
			 logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void findClientByName() {
		try {
			name = (String) in.readObject();
			client = banService.getClient(bank, name);
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
			banService.addClient(bank, client);
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (ClientExistsException e) {
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

	public static void main(final String args[]) {
		BankServer server = new BankServer();

		server.run();

	}
}