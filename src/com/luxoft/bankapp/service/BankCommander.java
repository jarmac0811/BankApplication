package com.luxoft.bankapp.service;

import java.util.Map;
import java.util.Scanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public class BankCommander {
	public static Bank currentBank = new Bank("My Bank");
	public static Client currentClient;

	public static Client getCurrentClient() {
		return currentClient;
	}
	static Map<String, Command> commands;
	public static void main(String args[]) {
		DBSelectBankCommander.getBankByName("My Bank");
		DBSelectClientCommander.setActiveClient("Ivan Ivanov");
		ApplicationContext context =
                new ClassPathXmlApplicationContext("application-context.xml");
		currentBank.setApplicationContext(context);
          BankCommander bankCommander =(BankCommander) context.getBean("bankCommander");
		Scanner s = new Scanner(System.in);
	
		while (true) {
			int i = 1;
			System.out.println("Current client: " + currentClient.getName());
			for (Command command : commands.values()) { // show menu
				System.out.print(i + ") ");
				command.printCommandInfo();
				i++;
			}

			String comm = s.nextLine(); // System.console().readLine();
			commands.get(comm).execute();

		}
	}


	public static Map<String, Command> getCommands() {
		return commands;
	}


	public static void setCommands(Map<String, Command> commands) {
		BankCommander.commands = commands;
	}
}