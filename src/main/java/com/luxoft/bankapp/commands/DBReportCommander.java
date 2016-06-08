package main.java.com.luxoft.bankapp.commands;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import main.java.com.luxoft.bankapp.dao.BankDAOImpl;
import main.java.com.luxoft.bankapp.dao.BaseDAOImpl;
import main.java.com.luxoft.bankapp.service.BankCommander;
import main.java.com.luxoft.bankapp.service.BankInfo;

public class DBReportCommander extends BaseDAOImpl implements Command {
	private BankInfo info = new BankInfo();
	private int numberOfClients;
	private int numberOfAccounts;
	private double totalAccountSum;
	private BankDAOImpl bankDao = new BankDAOImpl();
	@Autowired
	private BankCommander bankCommander;
	private static final  Logger logger = Logger.getLogger(AddClientCommand.class.getName());

	@Override
	public void execute() {

		info = bankDao.getBankInfo(BankCommander.currentBank);
		numberOfClients = info.getNumberOfClients();
		numberOfAccounts = info.getNumberOfAccounts();
		totalAccountSum = info.getTotalAccountSum();
		logger.info("Number of clients: " + numberOfClients + ", number of accounts:" + numberOfAccounts
				+ ", total sum on accounts: " + totalAccountSum);
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Prints information about current bank");
	}
}
