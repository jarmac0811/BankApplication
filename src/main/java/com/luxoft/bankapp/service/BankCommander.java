package main.java.com.luxoft.bankapp.service;

import java.util.Map;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import main.java.com.luxoft.bankapp.commands.Command;
import main.java.com.luxoft.bankapp.commands.DBSelectBankCommander;
import main.java.com.luxoft.bankapp.commands.DBSelectClientCommander;
import main.java.com.luxoft.bankapp.model.Bank;
import main.java.com.luxoft.bankapp.model.Client;

@Component
public class BankCommander {
	public static Bank currentBank;

	public static Client currentClient;
	static Map<String, Command> commands;

	public static Bank getCurrentBank() {
		return currentBank;
	}

	public static void setCurrentBank(Bank currentBank) {
		BankCommander.currentBank = currentBank;
	}

	public Client getCurrentClient() {
		return currentClient;
	}

	public void setCurrentClient(Client currentClient) {
		this.currentClient = currentClient;
	}

	public static void main(String args[]) {
		ApplicationContext context = new ClassPathXmlApplicationContext("file:resources/application-context.xml");
		DBSelectClientCommander dbsClient = (DBSelectClientCommander) context.getBean("DBSelectClientCommander");
		DBSelectBankCommander dbsBank = (DBSelectBankCommander) context.getBean("DBSelectBankCommander");
		BankCommander bankCommander = (BankCommander) context.getBean("bankCommander");
		Bank bank = (Bank) context.getBean("bank");
		dbsBank.getBankByName("My Bank");
		// bankCommander.currentBank.printReport();
		dbsClient.setActiveClient("Jarek Zegarek");

		bank.setApplicationContext(context);

		Scanner s = new Scanner(System.in);
		//Scanner s = new Scanner("3 4 5 6 7");
		String option = "";

		while (true) {
			int i = 1;
			System.out.println("Current client: " + bankCommander.currentClient.getName());
			for (Command command : commands.values()) { // show menu
				System.out.print(i + ") ");
				command.printCommandInfo();
				i++;
			}
		 
//		        option=s.next();
//				System.out.println(option);
		

			String comm =s.nextLine(); //
										// System.console().readLine();
			commands.get(comm).execute();
			try {
				Thread.currentThread();
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static Map<String, Command> getCommands() {
		return commands;
	}

	public static void setCommands(Map<String, Command> commands) {
		BankCommander.commands = commands;
	}
}