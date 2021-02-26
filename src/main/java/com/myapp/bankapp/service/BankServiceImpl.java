package com.myapp.bankapp.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.myapp.bankapp.exceptions.ClientExistsException;
import com.myapp.bankapp.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.bankapp.dao.BankDAOImpl;
import com.myapp.bankapp.dao.ClientDAOImpl;
import com.myapp.bankapp.model.Account;
import com.myapp.bankapp.model.Bank;
import com.myapp.bankapp.model.Client;

@Service
public class BankServiceImpl implements BankService {
	private Client client;
	@Autowired
	private ClientDAOImpl clientDao;
	@Autowired
	private BankDAOImpl bankDao;

	//ApplicationContext context = new ClassPathXmlApplicationContext("file:resources/application-context.xml");
	private static final Logger logger = Logger.getLogger(BankServerThreadedTest.class.getName());

	public ClientDAOImpl getClientDao() {
		return clientDao;
	}
	public BankDAOImpl getBankDao() {
		return bankDao;
	}

	public void setBankDao(BankDAOImpl bankDao) {
		this.bankDao = bankDao;
	}
	public void setClientDao(ClientDAOImpl clientDao) {
		this.clientDao = clientDao;
	}
	@Override
	public void addClient(Bank bank, Client client) {
		//System.out.println( clientDao.toString());
		try {
			bank.addClient(client);
			clientDao.add(bank, client);
		} catch (DAOException | ClientExistsException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public void removeClient(Bank bank, Client client) {
		bank.removeClient(client);
		try {
			clientDao.remove(client);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public void addAccount(Client client, Account account) {
		client.addAccount(account);
		try {
			clientDao.save(new Bank("My Bank"), client);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public void setActiveAccount(Client client, Account account) {
		client.setActiveAccount(account);

		try {
			clientDao.save(new Bank("My Bank"), client);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public Client getClient(Bank bank, String clientName) {
		//BankDAOImpl bankDao = context.getBean("bankDao",BankDAOImpl.class);
		try {
			client = clientDao.findClientByName(bank, clientName);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return client;
		// return bank.getClientsByName().get(clientName);

	}

	@Override
	public void saveClient(Client client) {
		try {
			FileOutputStream fos = new FileOutputStream("test.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(client);
			fos.flush();
			fos.close();
			oos.close();
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public Client loadClient() {
		client = new Client("");
		try {
			FileInputStream fis = new FileInputStream("test.ser");
			ObjectInputStream oos = new ObjectInputStream(fis);
			client = (Client) oos.readObject();
			fis.close();
			oos.close();
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		return client;
	}
	public void clearData(){
		bankDao.clearData();
	}
//	public static void main(String[] args) {
//		BankServiceImpl bankService = new BankServiceImpl();
//		Client client = new Client("Janek", Gender.MALE, "janek@gmail.com", "678-879-678", "Warsaw");
//		client.setId(10);
//		try {
//			bankService.addClient(new Bank("My Bank"), client);
//		} catch (ClientExistsException e) {
//			logger.log(Level.SEVERE, e.getMessage(), e);
//		}
//
//	}

}
