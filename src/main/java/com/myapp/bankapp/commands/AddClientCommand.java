package com.myapp.bankapp.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.bankapp.dao.BaseDAOImpl;
import com.myapp.bankapp.model.Account;
import com.myapp.bankapp.model.Client;
import com.myapp.bankapp.service.BankCommander;
import com.myapp.bankapp.service.BankServiceImpl;
import com.myapp.bankapp.service.CheckingAccount;
import com.myapp.bankapp.service.Gender;
import com.myapp.bankapp.service.SavingAccount;
@Service
public class AddClientCommand extends BaseDAOImpl implements Command {
	private String name;
	private String email;
	private String phone;
	private String city;
	@Autowired
	private BankServiceImpl bankService;
	private Client client;
	private int clientId;
	private Gender gender;
	private Account account;
	private Scanner scanner;
	@Autowired
	private BankCommander bankCommander;
	private static final  Logger logger = Logger.getLogger(AddClientCommand.class.getName());
	public BankServiceImpl getBankService() {
		return bankService;
	}

	
	public void setBankService(BankServiceImpl bankService) {
		this.bankService = bankService;
	}

	@Override
	public void execute() {
//		ApplicationContext context = new ClassPathXmlApplicationContext("file:resources/application-context.xml");
//		BankService bankService = context.getBean("bankService",BankService.class);
		bankService.clearData();
		boolean emailIncorrect = true;
		boolean phoneIncorrect = true;
		//scanner = new Scanner(System.in);
		File f=new File("addClientTest.txt");
		try {
			scanner = new Scanner(f);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.info("Enter client's id");
		clientId = scanner.nextInt();
		scanner.nextLine();
		logger.info("Enter client's name");
		name = scanner.nextLine();
		logger.info("MALE of FEMALE?");
		String sex = scanner.nextLine();
		if (("MALE").equals(sex))
			gender = Gender.MALE;
		else
			gender = Gender.FEMALE;
		logger.info("Enter client's city?");
		city = scanner.nextLine();
		while (emailIncorrect) {
			logger.info("Enter client's e-mail");
			email = scanner.nextLine();
			if (verifyEmail(email)) {

				emailIncorrect = false;
			} else {
				logger.info("Incorrect format of e-mail");
			}
		}
		while (phoneIncorrect) {
			logger.info("Enter client's phone number in format ddd-ddd-ddd");
			phone = scanner.nextLine();
			if (verifyPhone(phone)) {

				phoneIncorrect = false;
			} else {
				logger.info("Incorrect format of phone number ");
			}
		}
		client = new Client(name, gender, email, phone, city);
		client.setId(clientId);
		logger.info("Enter type of account");
		String accType = scanner.nextLine();
		logger.info("Enter balance");
		float balance = scanner.nextFloat();
		if (("c").equals(accType)) {
			logger.info("Enter overdraft");
			float overdraft = scanner.nextFloat();

			account = new CheckingAccount(balance, overdraft);
		} else
			account = new SavingAccount(balance);
		logger.info("Enter account id");
		int idAccount = scanner.nextInt();
		account.setId(idAccount);
		client.setActiveAccount(account);
		try {
			bankService.addClient(BankCommander.currentBank, client);
			bankCommander.setCurrentClient(client);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public BankCommander getBankCommander() {
		return bankCommander;
	}

	public void setBankCommander(BankCommander bankCommander) {
		this.bankCommander = bankCommander;
	}

	private boolean verifyEmail(String s) {
		String emailRegex = "^[A-Za-z\\.-0-9]{2,}@[A-Za-z\\.-0-9]{2,}\\.[A-Za-z]{2,3}$";
		if (s.matches(emailRegex))
			return true;
		return false;
	}

	private boolean verifyPhone(String s) {
		String phoneRegex = "\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}";
		if (s.matches(phoneRegex))
			return true;
		return false;
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Adds client to the current bank");
	}

}
