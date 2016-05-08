package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.dao.BankDAOImpl;
import com.luxoft.bankapp.dao.BaseDAOImpl;
import com.luxoft.bankapp.service.BankCommander;
import com.luxoft.bankapp.service.BankInfo;

public class DBReportCommander extends BaseDAOImpl implements Command {
	private BankInfo info = new BankInfo();
	private int numberOfClients;
	private int numberOfAccounts;
	private double totalAccountSum;
	private BankDAOImpl bankDao = new BankDAOImpl();

	@Override
	public void execute() {

		info = bankDao.getBankInfo(BankCommander.currentBank);
		numberOfClients = info.getNumberOfClients();
		numberOfAccounts = info.getNumberOfAccounts();
		totalAccountSum = info.getTotalAccountSum();
		System.out.println("Number of clients: " + numberOfClients + ", number of accounts:" + numberOfAccounts
				+ ", total sum on accounts: " + totalAccountSum);
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Prints information about current bank");
	}
}
