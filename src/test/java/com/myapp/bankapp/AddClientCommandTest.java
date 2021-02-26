package com.myapp.bankapp;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.myapp.bankapp.commands.AddClientCommand;
import com.myapp.bankapp.commands.DBSelectBankCommander;
import com.myapp.bankapp.model.Bank;
import com.myapp.bankapp.model.Client;
import com.myapp.bankapp.service.BankCommander;
import com.myapp.bankapp.service.BankService;

public class AddClientCommandTest {
	
	AddClientCommand addClientCommand;
	BankService bankService;
	public BankService getBankService() {
		return bankService;
	}
	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}
	public AddClientCommand getAddClientCommand() {
		return addClientCommand;
	}
	public void setAddClientCommand(AddClientCommand addClientCommand) {
		this.addClientCommand = addClientCommand;
	}	
	@Before
	public void init(){
		ApplicationContext context = new ClassPathXmlApplicationContext("file:resources/application-context.xml");
		DBSelectBankCommander dbsBank = (DBSelectBankCommander) context.getBean("DBSelectBankCommander");
		BankCommander bankCommander = (BankCommander) context.getBean("bankCommander");
		addClientCommand = context.getBean("addClientCommand",AddClientCommand.class);
		bankService = context.getBean("bankService",BankService.class);
		dbsBank.getBankByName("My Bank");
	}
@Test
public void testAddingClient(){
	
	addClientCommand.execute();
	Client client = bankService.getClient(new Bank("My Bank"), "Jan Kowalski");
	client.printReport();
	System.out.println(client.getName());
	assertEquals("Jan Kowalski",client.getName());
	
}
}
