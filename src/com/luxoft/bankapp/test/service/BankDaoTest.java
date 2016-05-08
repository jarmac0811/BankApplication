package com.luxoft.bankapp.test.service;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.luxoft.bankapp.dao.BankDAOImpl;
import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.Gender;
import com.luxoft.bankapp.service.SavingAccount;

public class BankDaoTest {
	private static Bank bank, bank2;
	private static BankDAOImpl bankDao;
	private static Client client, client2, client3, client4;

	@Before
	public void initBank() {
		bankDao = new BankDAOImpl();
		bank = new Bank("My Bank");
		client = new Client("Ivan Ivanov", Gender.MALE, "i.ivanov@gmail.com", "888-999-667", "Kiev");
		client.setActiveAccount(new SavingAccount(1000));
		client.activeAccount.setId(1);
		client.setId(1);
		client3 = new Client("Jan Wolski", Gender.MALE, "j.wolski@gmail.com", "657-878-663", "Warsaw");
		client3.setActiveAccount(new SavingAccount(2000));
		client3.activeAccount.setId(4);
		client3.setId(3);
		client4 = new Client("Zosia Samosia", Gender.FEMALE, "z.samosia@gmail.com", "633-768-965", "Warsaw");
		client4.setActiveAccount(new SavingAccount(5000));
		client4.activeAccount.setId(5);
		client4.setId(4);
		try {
			bank.addClient(client);
			bank.addClient(client3);
			bank.addClient(client4);
		} catch (ClientExistsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInsert() {

		try {
			bankDao.add(bank);
			bank2 = bankDao.getBankByName("My Bank");
			bank.printReport();
			bank2.printReport();
			assertTrue(TestService.isEquals(bank, bank2));
		} catch (DAOException | BankNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testUpdate() {

		try {

			client.setActiveAccount(new SavingAccount(3000));
			client.activeAccount.setId(2);
			client2 = new Client("Olek Drozd", Gender.MALE, "o.drozd@gmail.com", "789-667-567", "Cracow");
			client2.setActiveAccount(new SavingAccount(1000));
			client2.activeAccount.setId(3);
			client2.setId(2);
			try {
				bank.addClient(client2);
			} catch (ClientExistsException e) {
				e.printStackTrace();
			}
			bankDao.save(bank);
			bank2 = bankDao.getBankByName("My Bank");

		} catch (DAOException e) {
			e.printStackTrace();
		}

		catch (BankNotFoundException e) {
			e.printStackTrace();
		}

		assertTrue(TestService.isEquals(bank, bank2));
	}

}
