package com.myapp.bankapp.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.myapp.bankapp.dao.BaseDAOImpl;
import com.myapp.bankapp.model.Client;
import com.myapp.bankapp.service.BankCommander;

public class WithdrawCommand extends BaseDAOImpl implements Command {
	private Client client;
	private int id;
	private Scanner scanner;
	private PreparedStatement stmt;
	@Autowired
	private BankCommander bankCommander;
	private static final  Logger logger = Logger.getLogger(WithdrawCommand.class.getName());
	@Override
	public void execute() {
		client = bankCommander.currentClient;
		id = client.getId();
		//scanner = new Scanner(System.in);
				File f=new File("withdrawClientTest.txt");
				try {
					scanner = new Scanner(f);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		logger.info("Give the amount to withdraw");
		float amount = scanner.nextFloat();

		try {
			bankCommander.currentClient.activeAccount.withdraw(amount);
			float amountToupdate = bankCommander.currentClient.activeAccount.getBalance();
			String sql = "UPDATE ACCOUNTS SET BALANCE=? WHERE CLIENT_ID=?";
			
			conn = openConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setFloat(1, amountToupdate);
			stmt.setInt(2, id);
			stmt.executeUpdate();
		} catch (Exception e) {
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
		System.out.println("Withdraw specified amount");
	}
}
