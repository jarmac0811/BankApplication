package com.myapp.bankapp.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankClient {
	private Socket requestSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message;
	private String option;
	private String amount;
	private String optionTosend;
	private Scanner scanner;
	private static final String SERVER = "localhost";
	private boolean finish;
	private long start, stop, timeSpend;
	private static final Logger logger = Logger.getLogger(BankClient.class.getName());

	void run() {
		Logger logger = Logger.getLogger(BankClient.class.getName());
		scanner = new Scanner(System.in);
		try {
			requestSocket = new Socket(SERVER, 2004);
			start = System.currentTimeMillis();
			logger.info( new Date(start).toString());
			System.out.println("Connected to localhost in port 2004");
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			try {
				message = (String) in.readObject();
				System.out.println("serveer>" + message);
			} catch (ClassNotFoundException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}

			do {
				menu();
				switch (option) {
				case "1":
					getBalance();
					break;
				case "2":
					withdraw();
					break;
				case "3":
					exit();

				}

			} while (!finish);
		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				requestSocket.close();
				stop = System.currentTimeMillis();
				timeSpend = stop - start;
				logger.info( new Date(stop).toString());
				logger.info( timeSpend + " ms");
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	private void menu() {
		System.out.println("1) Check account balance\n2)Withdraw\n3)Zakoncz\n");
		System.out.println("Choose number of operation");

		option = scanner.nextLine();
		optionTosend = option + "1";
		if (option != "3")
			sendMessage(optionTosend);
	}

	private void getBalance() {
		try {
			System.out.println("Enter name of the client");

			String name = scanner.nextLine();
			sendMessage(name);
			String balance = (String) in.readObject();
			System.out.println("Your balance is: " + balance);
		} catch (ClassNotFoundException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void withdraw() {
		System.out.println("Enter name of the client");
		String name = scanner.nextLine();
		sendMessage(name);
		System.out.println("Enter amount to withdraw");
		amount = scanner.nextLine();
		sendMessage(amount);
	}

	private void exit() {
		finish = true;
		sendMessage("40");
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
		BankClient client = new BankClient();
		client.run();

	}

}
