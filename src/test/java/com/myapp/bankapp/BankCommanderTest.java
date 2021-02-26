package com.myapp.bankapp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.myapp.bankapp.commands.DBSelectBankCommander;
import com.myapp.bankapp.commands.DBSelectClientCommander;
import com.myapp.bankapp.model.Bank;
import com.myapp.bankapp.service.BankCommander;

public class BankCommanderTest {
	ApplicationContext context = new ClassPathXmlApplicationContext("file:resources/application-context.xml");
	DBSelectClientCommander dbsClient = (DBSelectClientCommander) context.getBean("DBSelectClientCommander");
	DBSelectBankCommander dbsBank = (DBSelectBankCommander) context.getBean("DBSelectBankCommander");
	BankCommander bankCommander = (BankCommander) context.getBean("bankCommander");
	Bank bank = (Bank) context.getBean("bank");
	
	@Test
	public void testgetBankByName(){
		
		dbsBank.getBankByName("My Bank");
		//bankCommander.currentBank.printReport();
		//dbsClient.setActiveClient("Jarek Zegarek");
		assertEquals("My Bank",BankCommander.currentBank.getName());
	}
	@Test
	public void testsetActiveClient(){
		dbsBank.getBankByName("My Bank");
		dbsClient.setActiveClient("Jarek Zegarek");
		System.out.println(bankCommander.currentClient.getName());
		assertEquals("Jarek Zegarek",bankCommander.currentClient.getName());
	}
}
