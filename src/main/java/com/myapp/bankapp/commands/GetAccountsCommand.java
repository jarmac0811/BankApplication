package com.myapp.bankapp.commands;

import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.myapp.bankapp.service.BankCommander;
@Component
public class GetAccountsCommand implements Command {
	//@Autowired
	private BankCommander bankCommander;
	private static final  Logger logger = Logger.getLogger(AddClientCommand.class.getName());

	@Override
	public void execute() {

		BankCommander.
		currentBank.
		printReport();
	}
	@Override
	public void printCommandInfo(){
		System.out.println("Lists all accounts of current client");
	}
}
