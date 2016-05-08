package com.luxoft.bankapp.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.luxoft.bankapp.dao.BaseDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankCommander;

public class DepositCommand extends BaseDAOImpl implements Command {
	private Connection conn;
	private Client client;
	private int clientId;
	private Scanner scanner;

	@Override
	public void execute() {
		client = BankCommander.currentClient;
		clientId = client.getId();
		scanner = new Scanner(System.in);
		System.out.println("Give the amount to deposit");
		float amount = scanner.nextFloat();
		BankCommander.currentClient.activeAccount.deposit(amount);

		// s.close();
		float amountToupdate = BankCommander.currentClient.activeAccount.getBalance();
		String sql = "UPDATE ACCOUNTS SET BALANCE=? WHERE CLIENT_ID=?";
		PreparedStatement stmt;

		try {
			conn = openConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setFloat(1, amountToupdate);
			stmt.setInt(2, clientId);
			stmt.executeUpdate();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Deposit specified amount to the current client account ");
	}
}
