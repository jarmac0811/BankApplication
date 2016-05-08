package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.service.BankCommander;

public class GetAccountsCommand implements Command {


	@Override
	public void execute() {
		
		BankCommander.currentBank.printReport();

	}
	@Override
	public void printCommandInfo(){
		System.out.println("Lists all account of current client");
	}
}
