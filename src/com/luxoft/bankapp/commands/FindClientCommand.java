package com.luxoft.bankapp.commands;

import java.util.Scanner;

import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankCommander;

public class FindClientCommand implements Command {
	private ClientDAOImpl clientDao;
	private Client client;
	private Scanner scanner;
	

	@Override
	public void execute() {
		clientDao = new ClientDAOImpl();
		scanner = new Scanner(System.in);
		System.out.println("Give the name of the client");
		String name = scanner.nextLine();
		// BankService bankService=new BankServiceImpl();
		// Client client=bankService.getClient(BankCommander.currentBank, name);
		// BankCommander.currentClient = client;
		// client.printReport();
		try {
			client = clientDao.findClientByName(BankCommander.currentBank, name);
			//client.printReport();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Finds a client by specified name");
	}
}