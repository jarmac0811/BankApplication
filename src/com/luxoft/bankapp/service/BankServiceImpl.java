package com.luxoft.bankapp.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

@Service
public class BankServiceImpl implements BankService {
	private Client client;
	@Resource
	ClientDAOImpl clientDao;

	@Override
	public void addClient(Bank bank, Client client) throws ClientExistsException {
		bank.addClient(client);
		ApplicationContext context = new FileSystemXmlApplicationContext("application-context2.xml");
		clientDao = (ClientDAOImpl) context.getBean("clientDao");
		try {
			clientDao.add(bank, client);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void removeClient(Bank bank, Client client) {
		bank.removeClient(client);
		ApplicationContext context = new FileSystemXmlApplicationContext("application-context2.xml");
		clientDao = (ClientDAOImpl) context.getBean("clientDao");
		try {
			clientDao.remove(client);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addAccount(Client client, Account account) {
		client.addAccount(account);
		ApplicationContext context = new FileSystemXmlApplicationContext("application-context2.xml");
		clientDao = (ClientDAOImpl) context.getBean("clientDao");
		try {
			clientDao.save(new Bank("My Bank"), client);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setActiveAccount(Client client, Account account) {
		client.setActiveAccount(account);
		ApplicationContext context = new FileSystemXmlApplicationContext("application-context2.xml");
		clientDao = (ClientDAOImpl) context.getBean("clientDao");
		try {
			clientDao.save(new Bank("My Bank"), client);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Client getClient(Bank bank, String clientName) {
		ApplicationContext context = new FileSystemXmlApplicationContext("application-context2.xml");
		clientDao = (ClientDAOImpl) context.getBean("clientDao");
		try {
			client = clientDao.findClientByName(bank, clientName);
		} catch (DAOException e) {
			e.printStackTrace();
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
		} catch (IOException e) {
		}
	}

	@Override
	public Client loadClient() {
		Client client = new Client("");
		try {
			FileInputStream fis = new FileInputStream("test.ser");
			ObjectInputStream oos = new ObjectInputStream(fis);
			client = (Client) oos.readObject();
			fis.close();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return client;
	}

	public static void main(String[] args) {
		BankServiceImpl bankService = new BankServiceImpl();
		Client client = new Client("Janek", Gender.MALE, "fghj", "678-879-678", "Warsaw");
		client.setId(10);
		try {
			bankService.addClient(new Bank("My Bank"), client);
		} catch (ClientExistsException e) {
			e.printStackTrace();
		}

	}

}
