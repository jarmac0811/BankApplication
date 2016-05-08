package com.luxoft.bankapp.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.luxoft.bankapp.dao.BaseDAOImpl;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankCommander;

public class TransferCommand extends BaseDAOImpl implements Command {
	private Connection conn;
	private Client client1, client2;
	private int id1, id2;
	private Scanner scanner;
	private ClientDAOImpl clientDao;
	private float balance;

	@Override
	public void execute() {
		String sql = "UPDATE ACCOUNTS SET BALANCE=? WHERE CLIENT_ID=?";
		String sql2 = "SELECT BALANCE FROM ACCOUNTS WHERE CLIENT_ID=?";
		clientDao = new ClientDAOImpl();
		
		client1 = BankCommander.currentClient;
		id1 = client1.getId();
		scanner = new Scanner(System.in);
		System.out.println("Give the amount to transfer");
		float amount = scanner.nextFloat();
		try {
			BankCommander.currentClient.activeAccount.withdraw(amount);
		} catch (BankException e1) {
			e1.printStackTrace();
		}
		System.out.println("Choose the name of client to transfer to:");
		scanner.nextLine();
		String name = scanner.nextLine();
		// transferTo(name,amount);
		try { 
			client2 = clientDao.findClientByName(BankCommander.currentBank, name);
			id2 = client2.getId();
			client2.printReport();

			// s.close();
			float amount1 = BankCommander.currentClient.activeAccount.getBalance();
			float amount2 = client2.activeAccount.getBalance();

			PreparedStatement stmt;

			conn = openConnection();
			stmt = conn.prepareStatement(sql2);
			stmt.setInt(1, id2);
			ResultSet results =stmt.executeQuery();
			if (results.next()) {
				balance=results.getFloat("balance");
			}
			stmt = conn.prepareStatement(sql);
			stmt.setFloat(1, amount1);
			stmt.setInt(2, id1);
			stmt.executeUpdate();
			stmt = conn.prepareStatement(sql);
			stmt.setFloat(1, balance+amount);
			stmt.setInt(2, id2);
			stmt.executeUpdate();
			
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Transfers money from current Client to choosen:");
	}

	private void transferTo(String to, float amount) {
		try {
			BankCommander.currentClient.activeAccount.withdraw(amount);
		} catch (BankException e) {
			e.printStackTrace();
		}

		for (Client client : BankCommander.currentBank.getClients()) {
			if (client.getName().equals(to)) {
				client.activeAccount.deposit(amount);
				client2 = client;
			}
		}

	}
}
