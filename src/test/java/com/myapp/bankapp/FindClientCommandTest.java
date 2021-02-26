package com.myapp.bankapp;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.myapp.bankapp.commands.DBSelectBankCommander;
import com.myapp.bankapp.commands.FindClientCommand;
import com.myapp.bankapp.service.BankCommander;

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
