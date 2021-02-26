package com.myapp.bankapp;

import static org.junit.Assert.assertTrue;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.myapp.bankapp.dao.BankDAOImpl;
import com.myapp.bankapp.exceptions.ClientExistsException;
import com.myapp.bankapp.exceptions.DAOException;
import com.myapp.bankapp.model.Bank;
import com.myapp.bankapp.model.Client;
import com.myapp.bankapp.service.Gender;
import com.myapp.bankapp.service.SavingAccount;
import com.myapp.bankapp.service.TestService;

public class BankDaoTest {
	static private Bank bank, bank2;
	static private BankDAOImpl bankDao;
	static private Client client, client2, client3, client4;
	private static final Logger logger = Logger.getLogger(BankDaoTest.class.getName());

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
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Test
	public void testInsert() {


		try {

			bankDao.add(bank);
			System.out.println("in insert");
			bank2 = bankDao.getBankByName("My Bank");
			System.out.println(bank2);
//			bank.printReport();
//			bank2.printReport();
			assertTrue(TestService.isEquals(bank, bank2));
		} catch (DAOException e) {
			System.out.println("in exception");
			logger.log(Level.SEVERE, e.getMessage(), e);
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
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
			bankDao.save(bank);
			bank2 = bankDao.getBankByName("My Bank");
			bank.printReport();
			bank2.printReport();
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		assertTrue(TestService.isEquals(bank, bank2));
	}

}
