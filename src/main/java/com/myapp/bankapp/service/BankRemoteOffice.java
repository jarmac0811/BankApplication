package com.myapp.bankapp.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.myapp.bankapp.model.Account;
import com.myapp.bankapp.model.Client;

public class BankRemoteOffice {
	private String option;
	private Socket requestSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message;
	private BankInfo info;
	private String optionTosend;
	private Scanner scanner;
	private Client client;
	private Gender gender;
	private Account account;
	private static final String SERVER = "localhost";
	private static final Logger logger = Logger.getLogger(BankRemoteOffice.class.getName());

	public void run() {
		try {
			scanner = new Scanner(System.in);
			requestSocket = new Socket(SERVER, 2004);
			System.out.println("Trying to connect to " + SERVER + " in port 2004");
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			try {
				message = (String) in.readObject();
				System.out.println(message);

			} catch (ClassNotFoundException e) {

				logger.log(Level.SEVERE, e.getMessage(), e);
			}
			while (true) {
				menu();

				switch (option) {
				case "1":
					getStatistics();
					break;
				case "2":
					findClientByName();
					break;
				case "3":
					AddClient();
					break;
				case "4":
					System.exit(0);
				}
			}
		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			// 4: Closing connection
			try {
				//in.close();
				//out.close();
				requestSocket.close();
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	private void menu() {
		System.out.println("1)Get statistics of the bank\n2)Find client by name and print info\n3)Add client\n4)Exit");
		System.out.println("Choose number of operation");
		
		option = scanner.nextLine();
		optionTosend = option + "0";
		if (option != "4")
			sendMessage(optionTosend);

	}

	private void getStatistics() {
		try {
			info = (BankInfo) in.readObject();
			
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		System.out.println("Numbers of clients: " + info.getNumberOfClients());
		System.out.println("Numbers of accounts: " + info.getNumberOfAccounts());
		System.out.println("sumOfCredits: " + info.getSumOfCredits());
		System.out.println("\n");
	}

	private void findClientByName() {
		System.out.println("Enter name of the client");
		String name = scanner.nextLine();
		sendMessage(name);
		try {
			client = (Client) in.readObject();
			client.printReport();
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

	}

	private void AddClient() {
		System.out.println("Enter name of the client");
		String name = scanner.nextLine();
		System.out.println("Male of female?");
		String sex = scanner.nextLine();
		if (sex.equals("male"))
			gender = Gender.MALE;
		else
			gender = Gender.FEMALE;
		System.out.println("Enter type of account");
		String accType = scanner.nextLine();
		System.out.println("Enter balance");
		float balance = scanner.nextFloat();
		System.out.println("Enter email");
		String email = scanner.nextLine();
		System.out.println("Enter telephone");
		String phone = scanner.nextLine();
		System.out.println("Enter city");
		String city = scanner.nextLine();
		if (accType.equals("c") ){
			System.out.println("Enter overdraft");
			float overdraft = scanner.nextFloat();
			
			account = new CheckingAccount(1200, overdraft);
		}
		else
			account = new SavingAccount(balance);
		client = new Client(name, gender, email, phone, city);
		client.setActiveAccount(account);

		try {

			out.writeObject(client);
			out.flush();
			
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void sendMessage(final String msg) {
		try {
			out.writeObject(msg);
			out.flush();

		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public static void main(final String args[]) {
		BankRemoteOffice banRemote = new BankRemoteOffice();

		banRemote.run();

	}
}
