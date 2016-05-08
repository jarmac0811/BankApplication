package com.luxoft.bankapp.service;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public class ServerThread implements Runnable {
	private Socket connection;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String name, option, amount;
	private boolean finish;
	private Bank bank;
	private Client client;
	private BankService banService;

	ServerThread(Socket socket) {
		connection = socket;
	}

	@Override
	public void run() {
		banService = new BankServiceImpl();
		bank = BankApplication.getBank();

		try {

			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			sendMessage("Connection successful");
			do {
				try {
					option = (String) in.readObject();
					// System.out.println(option);
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
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				BankServerThreaded.decrement();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	private void getBalance() {
		try {
			name = (String) in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (NumberFormatException e2) {
			e2.printStackTrace();
		} catch (BankException e3) {
			e3.toString();
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void findClientByName() {
		try {
			name = (String) in.readObject();
			client = banService.getClient(bank, name);
			out.writeObject(client);

			out.flush();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void addClient() {
		try {
			client = (Client) in.readObject();
			try {
				banService.addClient(bank, client);
			} catch (com.luxoft.bankapp.exceptions.ClientExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 

	}

	void sendMessage(final String msg) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
}
