package main.java.com.luxoft.bankapp.service;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import main.java.com.luxoft.bankapp.model.Account;
import main.java.com.luxoft.bankapp.model.Bank;
import main.java.com.luxoft.bankapp.model.Client;

public class BankReport {
	public void getNumberOfClients(Bank bank) {
		int numberOfClients = bank.getClients().size();
		System.out.println("Number of clients is: " + numberOfClients);

	}

	public void getAccountsNumber(Bank bank) {
		int sum = 0;
		for (Client client : bank.getClients())
			sum += client.getAccounts().size();
		System.out.println("Number of accounts is: " + sum);
	}

	public void getClientsSorted(Bank bank) {
		Set<Client> sorted = new TreeSet<>(bank.getClients());
		for (Client client : sorted)
			client.printReport();
	}

	public void getBankCreditSum(Bank bank) {
		float credits = 0;
		for (Client client : bank.getClients())
			for (Account account : client.getAccounts())
				if (account instanceof CheckingAccount)
					if (account.getBalance() < 0)
						credits += account.getBalance();

		System.out.println("Amount of credits: " + credits);
	}

	public void getClientsByCity(Bank bank) {
		TreeMap<String, ArrayList<Client>> table = new TreeMap<String, ArrayList<Client>>();
		ArrayList<Client> list;
		for (Client client : bank.getClients()) {
			list = table.get(client.getCity());
			if (list == null) {
				list = new ArrayList<Client>();
				list.add(client);
				table.put(client.getCity(), list);
			} else if (!list.contains(client))
				list.add(client);
		}
		for (String city : table.keySet()) {
			System.out.println("City: " + city);
			for (Client client : table.get(city))
				client.printReport();
		}
	}
}
