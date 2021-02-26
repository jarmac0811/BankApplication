package com.myapp.bankapp;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.myapp.bankapp.commands.DBSelectBankCommander;
import com.myapp.bankapp.commands.DBSelectClientCommander;
import com.myapp.bankapp.commands.WithdrawCommand;
import com.myapp.bankapp.service.BankCommander;

public class WithdrawCommandTest {
	private WithdrawCommand withdrawCommand;
	BankCommander bankCommander;

	@Before
	public void init() {
		ApplicationContext context = new ClassPathXmlApplicationContext("file:resources/application-context.xml");
		withdrawCommand = context.getBean("withdrawCommand", WithdrawCommand.class);
		DBSelectBankCommander dbsBank = (DBSelectBankCommander) context.getBean("DBSelectBankCommander");
		DBSelectClientCommander dbsClient = (DBSelectClientCommander) context.getBean("DBSelectClientCommander");
		dbsBank.getBankByName("My Bank");
		bankCommander = (BankCommander) context.getBean("bankCommander");
		dbsClient.setActiveClient("Ivan Ivanov");
	}

	@Test
	public void testDeposit() {
		bankCommander.currentBank.printReport();
		int amountBefore = (int) BankCommander.currentClient.activeAccount.getBalance();
		withdrawCommand.execute();
		int amountAfter = (int) BankCommander.currentClient.activeAccount.getBalance();
		assertEquals(amountBefore -1000, amountAfter);
	}
}
