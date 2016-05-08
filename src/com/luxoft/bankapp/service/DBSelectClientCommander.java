package com.luxoft.bankapp.service;

import java.util.Scanner;

import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Client;

public class DBSelectClientCommander {
	private static Scanner scanner;
	private static String bankName;
	private static ClientDAOImpl clientdao = new ClientDAOImpl();

	public static void setActiveClient(String name) {
		 scanner=new Scanner(System.in);
		String clientName = name;
		if (BankCommander.currentBank == null) {
			System.out.println("Enter name of the bank to set active");
			bankName = scanner.nextLine();
			DBSelectBankCommander.getBankByName(bankName);
		}
		 
		try {
			
			Client client = clientdao.findClientByName(BankCommander.currentBank, clientName);
			BankCommander.currentClient = client;
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
}
