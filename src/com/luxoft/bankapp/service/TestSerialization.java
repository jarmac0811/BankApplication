package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;

public class TestSerialization {

	public static void main(String[] args) {
		BankService bankService = new BankServiceImpl();
		Account acc1 = new SavingAccount(1500);
		Client customer1 = new Client("Jan", Gender.MALE, "jan@wp.pl", "888-888-888", "Warsaw");
		
		bankService.addAccount(customer1, acc1);
		bankService.setActiveAccount(customer1, acc1);
		customer1.printReport();
		bankService.saveClient(customer1);
		Client customer5 = bankService.loadClient();
		bankService.setActiveAccount(customer5, acc1);
		customer5.printReport();

	}

}
