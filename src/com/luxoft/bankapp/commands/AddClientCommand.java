package com.luxoft.bankapp.commands;

import java.util.Scanner;

import com.luxoft.bankapp.dao.BaseDAOImpl;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankCommander;
import com.luxoft.bankapp.service.BankServiceImpl;
import com.luxoft.bankapp.service.CheckingAccount;
import com.luxoft.bankapp.service.Gender;
import com.luxoft.bankapp.service.SavingAccount;

public class AddClientCommand extends BaseDAOImpl implements Command {
	private String name, email, phone, city;
	private Bank bank;
	private BankServiceImpl bankService;
	private Client client;
	private ClientDAOImpl clientDao;
	private int clientId;
	private Gender gender;
	private Account account;
	private Scanner scanner;

	public AddClientCommand() {
		bank = BankCommander.currentBank;
		bankService = new BankServiceImpl();
	}

	public BankServiceImpl getBankService() {
		return bankService;
	}

	public void setBankService(BankServiceImpl bankService) {
		this.bankService = bankService;
	}

	@Override
	public void execute() {
		boolean emailIncorrect = true;
		boolean phoneIncorrect = true;
		scanner = new Scanner(System.in);
		System.out.println("Enter client's id");
		clientId = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Enter client's name");
		name = scanner.nextLine();
		// System.out.println("MALE of FEMALE?");
		// String sex = scanner.nextLine();
		// if (sex.equals("MALE"))
		// gender = Gender.MALE;
		// else
		// gender = Gender.FEMALE;
		// System.out.println("Enter client's city");
		// city = scanner.nextLine();
		// while (emailIncorrect) {
		// System.out.println("Enter client's e-mail");
		// email = scanner.nextLine();
		// if (verifyEmail(email)) {
		//
		// emailIncorrect = false;
		// } else {
		// System.out.println("Incorrect format of e-mail");
		// }
		// }
		// while (phoneIncorrect) {
		// System.out.println("Enter client's phone number in format
		// ddd-ddd-ddd");
		// phone = scanner.nextLine();
		// if (verifyPhone(phone)) {
		//
		// phoneIncorrect = false;
		// } else {
		// System.out.println("Incorrect format of phone number ");
		// }
		// }
		client = new Client(name, gender, email, phone, city);
		client.setId(clientId);
		System.out.println("Enter type of account");
		String accType = scanner.nextLine();
		System.out.println("Enter balance");
		float balance = scanner.nextFloat();
		if (accType.equals("c")) {
			System.out.println("Enter overdraft");
			float overdraft = scanner.nextFloat();

			account = new CheckingAccount(balance, overdraft);
		} else
			account = new SavingAccount(balance);
		System.out.println("Enter account id");
		int idAccount = scanner.nextInt();
		account.setId(idAccount);
		client.setActiveAccount(account);
		try {
			// bank.addClient(client);
			bankService.addClient(BankCommander.currentBank, client);
			BankCommander.currentClient = client;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// clientDao = new ClientDAOImpl();
		// try {
		// clientDao.add(BankCommander.currentBank, client);
		// } catch (DAOException e) {
		// e.printStackTrace();
		// }
	}

	private boolean verifyEmail(String s) {
		String EMAIL_REGEX = "^[A-Za-z\\.-0-9]{2,}@[A-Za-z\\.-0-9]{2,}\\.[A-Za-z]{2,3}$";
		if (s.matches(EMAIL_REGEX))
			return true;
		return false;
	}

	private boolean verifyPhone(String s) {
		String PHONE_REGEX = "\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}";
		if (s.matches(PHONE_REGEX))
			return true;
		return false;
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Adds client to the current bank");
	}

}
