package test.java.com.luxoft.bankapp.test.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import main.java.com.luxoft.bankapp.commands.DBSelectBankCommander;
import main.java.com.luxoft.bankapp.commands.DBSelectClientCommander;
import main.java.com.luxoft.bankapp.dao.BankDAOImpl;
import main.java.com.luxoft.bankapp.model.Bank;
import main.java.com.luxoft.bankapp.service.BankCommander;
import main.java.com.luxoft.bankapp.service.BankInfo;

public class DBReportCommanderTest {
	private BankInfo info = new BankInfo();
	private int numberOfClients;
	private int numberOfAccounts;
	private double totalAccountSum;
	private BankDAOImpl bankDao = new BankDAOImpl();
@Before
public void init(){
	ApplicationContext context = new ClassPathXmlApplicationContext("file:resources/application-context.xml");
	DBSelectClientCommander dbsClient = (DBSelectClientCommander) context.getBean("DBSelectClientCommander");
	DBSelectBankCommander dbsBank = (DBSelectBankCommander) context.getBean("DBSelectBankCommander");
	BankCommander bankCommander = (BankCommander) context.getBean("bankCommander");
	Bank bank = (Bank) context.getBean("bank");
	dbsBank.getBankByName("My Bank");
	dbsClient.setActiveClient("Jarek Zegarek");
}
	@Test
	public void testNumberOfClients() {
		
		info = bankDao.getBankInfo(BankCommander.currentBank);
		numberOfClients = info.getNumberOfClients();
		assertEquals(4, numberOfClients);
	}

	@Test
	public void testNumberOfAccounts() {
		info = bankDao.getBankInfo(BankCommander.currentBank);
		numberOfAccounts = info.getNumberOfAccounts();
		assertEquals(5, numberOfAccounts);
	}

	@Test
	public void testTotalAccountSum() {
		info = bankDao.getBankInfo(BankCommander.currentBank);
		totalAccountSum = info.getTotalAccountSum();
		int totalInt = (int) totalAccountSum;
		assertEquals(12000, totalInt);
	}
}
