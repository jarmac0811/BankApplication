package com.luxoft.bankapp.test.service;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.CheckingAccount;
import com.luxoft.bankapp.service.Gender;

public class TestServiceTest {
	Bank bank1, bank2;

	@Before
	public void initBanks() {
		
		try {
			bank1 = new Bank("My Bank");
			bank1.setId(1);
			Client client = new Client("Ivan Ivanov", Gender.MALE, "i.ivanov@gmail.com", "888-999-667", "Kiev");
			client.addAccount(new CheckingAccount(1000, 50));
			bank1.addClient(client);
			bank2 = new Bank("My Bank");
			bank2.setId(1);
			Client client2 = new Client("Ivan Ivanov", Gender.MALE, "i.ivanov@gmail.com", "888-999-667", "Kiev");
			client2.addAccount(new CheckingAccount(1000, 50));
			bank2.addClient(client2);
		} catch (ClientExistsException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testEquals() {
		assertTrue(TestService.isEquals(bank1, bank2));
	}
}