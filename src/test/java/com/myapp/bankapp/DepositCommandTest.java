package com.myapp.bankapp;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.myapp.bankapp.commands.DBSelectBankCommander;
import com.myapp.bankapp.commands.DBSelectClientCommander;
import com.myapp.bankapp.commands.DepositCommand;
import com.myapp.bankapp.service.BankCommander;

public class DepositCommandTest {
	private DepositCommand depositCommand;
	BankCommander bankCommander;

	@Before
	public void init() {
		ApplicationContext context = new ClassPathXmlApplicationContext("file:resources/application-context.xml");
		depositCommand = context.getBean("depositCommand", DepositCommand.class);
		DBSelectBankCommander dbsBank = (DBSelectBankCommander) context.getBean("DBSelectBankCommander");
		DBSelectClientCommander dbsClient = (DBSelectClientCommander) context.getBean("DBSelectClientCommander");
		dbsBank.getBankByName("My Bank");
		bankCommander = (BankCommander) context.getBean("bankCommander");
		dbsClient.setActiveClient("Jan Wolski");
	}

	@Test
	public void testDeposit() {
		bankCommander.currentBank.printReport();
		int amountBefore = (int) BankCommander.currentClient.activeAccount.getBalance();
		depositCommand.execute();
		int amountAfter = (int) BankCommander.currentClient.activeAccount.getBalance();
		assertEquals(amountBefore + 2000, amountAfter);
	}
}
