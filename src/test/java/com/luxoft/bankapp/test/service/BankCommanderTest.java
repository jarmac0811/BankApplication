package test.java.com.luxoft.bankapp.test.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import main.java.com.luxoft.bankapp.commands.DBSelectBankCommander;
import main.java.com.luxoft.bankapp.commands.DBSelectClientCommander;
import main.java.com.luxoft.bankapp.model.Bank;
import main.java.com.luxoft.bankapp.service.BankCommander;

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
