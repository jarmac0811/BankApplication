package com.myapp.bankapp.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.myapp.bankapp.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;

import com.myapp.bankapp.dao.BaseDAOImpl;
import com.myapp.bankapp.model.Client;
import com.myapp.bankapp.service.BankCommander;

public class DepositCommand extends BaseDAOImpl implements Command {
	private Client client;
	private int clientId;
	private Scanner scanner;
	private PreparedStatement stmt;
	@Autowired
	private BankCommander bankCommander;
	public BankCommander getBankCommander() {
		return bankCommander;
	}

	public void setBankCommander(BankCommander bankCommander) {
		this.bankCommander = bankCommander;
	}

	private static final  Logger logger = Logger.getLogger(DepositCommand.class.getName());

	@Override
	public void execute() {
		client = bankCommander.currentClient;
		clientId = client.getId();
		//scanner = new Scanner(System.in);
		File f=new File("depositClientTest.txt");
		try {
			scanner = new Scanner(f);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.info("Give the amount to deposit");
		float amount =(float) scanner.nextInt();
		bankCommander.currentClient.activeAccount.deposit(amount);
		float amountToupdate = bankCommander.currentClient.activeAccount.getBalance();
		String sql = "UPDATE ACCOUNTS SET BALANCE=? WHERE CLIENT_ID=?";
		

		try {
			conn = openConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setFloat(1, amountToupdate);
			stmt.setInt(2, clientId);
			stmt.executeUpdate();
		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Deposit specified amount to the current client account ");
	}
}
