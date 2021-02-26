package com.myapp.bankapp.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;

import com.myapp.bankapp.servlets.LoginServlet;
import org.springframework.beans.factory.annotation.Autowired;

import com.myapp.bankapp.model.Client;
import com.myapp.bankapp.service.BankCommander;
import com.myapp.bankapp.service.BankServiceImpl;

public class FindClientCommand implements Command {
	private Client client;
	private Scanner scanner;
	private BankServiceImpl bankService;
	@Autowired
	private BankCommander bankCommander;
	private static final  Logger logger = Logger.getLogger(LoginServlet.class.getName());

	@Override
	public void execute() {
		
		//scanner = new Scanner(System.in);
		File f=new File("findClientTest.txt");
		try {
			scanner = new Scanner(f);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.info("Give the name of the client");
		String name = scanner.nextLine();
		client = bankService.getClient(BankCommander.currentBank, name);
		BankCommander.currentClient = client;
		

	}

	public BankServiceImpl getBankService() {
		return bankService;
	}

	@Autowired
	public void setBankService(BankServiceImpl bankService) {
		this.bankService = bankService;
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Finds a client by specified name");
	}
}