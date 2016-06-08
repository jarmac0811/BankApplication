package test.java.com.luxoft.bankapp.test.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import main.java.com.luxoft.bankapp.commands.DBSelectBankCommander;
import main.java.com.luxoft.bankapp.commands.FindClientCommand;
import main.java.com.luxoft.bankapp.service.BankCommander;

public class FindClientCommandTest {
private FindClientCommand findClientCommand;
@Before
public void init(){
	ApplicationContext context = new ClassPathXmlApplicationContext("file:resources/application-context.xml");
	findClientCommand = context.getBean("findClientCommand",FindClientCommand.class);
	DBSelectBankCommander dbsBank = (DBSelectBankCommander) context.getBean("DBSelectBankCommander");
	dbsBank.getBankByName("My Bank");
}
@Test
public void testFindClient(){
	
	findClientCommand.execute();
	assertEquals("Jan Kowalski",BankCommander.currentClient.getName());
}
}
