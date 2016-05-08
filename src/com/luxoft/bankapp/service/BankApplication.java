package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public class BankApplication {
	private static Bank bank;
	private static Client customer1;
	private static Client customer2;
	private static Client customer3;
	private static Client customer4;
	private static BankService bankService;

	public static void initialize() {
		bank = new Bank("MyBank");
		customer1 = new Client("Jan", Gender.MALE, "jan@wp.pl", "888-888-888", "Warsaw");
		customer2 = new Client("Tadeusz", Gender.MALE, "tadeusz@gmail.com", "999-999-999", "Cracow");
		customer3 = new Client("Zosia", Gender.FEMALE, "zosia@wp.pl", "777-777-777", "Wroclaw");
		// customer4 = new Client("Zosia", Gender.FEMALE);
		bankService = new BankServiceImpl();
		try {
			bankService.addClient(bank, customer1);
			bankService.addClient(bank, customer2);
			bankService.addClient(bank, customer3);
			// //bankService.addClient(bank, customer4); // A client with the
			// given
			// name already exists in the bank
			//
		} catch (ClientExistsException e) {
			e.printStackTrace();

		}

		// bankService.removeClient(bank, customer3);
		Account acc1 = new SavingAccount(1500);
		Account acc2 = new CheckingAccount(1000, 500);
		Account acc3 = new SavingAccount(2000);
		Account acc4 = new SavingAccount(1500);
		Account acc5 = new CheckingAccount(2000, 1000);
		Account acc6 = new SavingAccount(2000.56f);
		// acc6.decimalValue();
		// Account acc7 = new SavingAccount(-2000); //negative balance value is
		// given
		bankService.addAccount(customer1, acc1);
		bankService.addAccount(customer1, acc2);
		bankService.addAccount(customer1, acc3);
		bankService.addAccount(customer2, acc4);
		bankService.addAccount(customer2, acc5);
		bankService.addAccount(customer3, acc6);
		bankService.setActiveAccount(customer1, acc2);
		bankService.setActiveAccount(customer2, acc4);
		bankService.setActiveAccount(customer3, acc6);

	}

	public static Bank getBank() {
		return bank;
	}

	public static void modify() {
		customer1.getActiveAccount().deposit(2000);
		customer2.getActiveAccount().deposit(1000);
		customer3.getActiveAccount().deposit(5000);
		try {
			customer1.getActiveAccount().withdraw(3500);// requests the amount
														// that exceeds the
														// amount that can be
														// given to client
			// customer2.getActiveAccount().withdraw(5000);

			// customer2.getActiveAccount().withdraw(500);
		} catch (BankException e) {
			// e.getInfo();
			e.printStackTrace();
			// System.out.println(e);
		}

	}

	public static void printBankReport() {
		BankReport bankReport = new BankReport();
		bankReport.getNumberOfClients(bank);
		bankReport.getAccountsNumber(bank);
		bankReport.getBankCreditSum(bank);
		bankReport.getClientsSorted(bank);
		bankReport.getClientsByCity(bank);

	}

	public static void main(String[] args) {
		initialize();
		if (args.length > 0 && args[0].equals("-report"))
			printBankReport();
		// System.out.println("\nAfter modifications:\n");
		// modify();
		// printBankReport();
		// System.out.println(customer1);

		// bankService.getClient(bank, "Jan").printReport();

		Bank bank2 = new Bank("my bank");
		BankFeedService bfs = new BankFeedService(bank2);
		bfs.loadFeed("folder");

		BankReport bankReport = new BankReport();
		bankReport.getNumberOfClients(bank2);
		bankReport.getAccountsNumber(bank2);
		bank2.printReport();

		 try {
		  bankService.addClient(bank2, customer1);
		 bankService.addClient(bank2, customer2);
		 bankService.addClient(bank2, customer3);
		  //bankService.addClient(bank2, customer4); // A client with the  given name already exists in the bank
		 
		
		 bankService.saveClient(customer1);
		
		 Client customer5 = bankService.loadClient();
		 customer5.printReport();
		 } catch (ClientExistsException e) {
		 e.printStackTrace();

	}

}
}
