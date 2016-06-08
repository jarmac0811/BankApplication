package main.java.com.luxoft.bankapp.commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import main.java.com.luxoft.bankapp.dao.BaseDAOImpl;
import main.java.com.luxoft.bankapp.dao.ClientDAOImpl;
import main.java.com.luxoft.bankapp.exceptions.BankException;
import main.java.com.luxoft.bankapp.exceptions.DAOException;
import main.java.com.luxoft.bankapp.model.Client;
import main.java.com.luxoft.bankapp.service.BankCommander;

public class TransferCommand extends BaseDAOImpl implements Command {
	private Client client1;
	private Client client2;
	private int id1;
	private int id2;
	private Scanner scanner;
	private ClientDAOImpl clientDao;
	private float balance;
	private PreparedStatement stmt;
	@Autowired
	private BankCommander bankCommander;
	
	private static final Logger logger = Logger.getLogger(TransferCommand.class.getName());

	@Override
	public void execute() {
		String sql = "UPDATE ACCOUNTS SET BALANCE=? WHERE CLIENT_ID=?";
		String sql2 = "SELECT BALANCE FROM ACCOUNTS WHERE CLIENT_ID=?";
		clientDao = new ClientDAOImpl();

		client1 = bankCommander.currentClient;
		id1 = client1.getId();
		scanner = new Scanner(System.in);
		logger.info("Give the amount to transfer");
		float amount = scanner.nextFloat();
		try {
			bankCommander.currentClient.activeAccount.withdraw(amount);
		} catch (BankException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		logger.info("Choose the name of client to transfer to:");
		scanner.nextLine();
		String name = scanner.nextLine();
		// transferTo(name,amount);
		try {
			client2 = clientDao.findClientByName(BankCommander.currentBank, name);
			id2 = client2.getId();
			client2.printReport();
			float amount1 = bankCommander.currentClient.activeAccount.getBalance();
			//float amount2 = client2.activeAccount.getBalance();

			conn = openConnection();
			stmt = conn.prepareStatement(sql2);
			stmt.setInt(1, id2);
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				balance = results.getFloat("balance");
			}
			stmt = conn.prepareStatement(sql);
			stmt.setFloat(1, amount1);
			stmt.setInt(2, id1);
			stmt.executeUpdate();
			stmt = conn.prepareStatement(sql);
			stmt.setFloat(1, balance + amount);
			stmt.setInt(2, id2);
			stmt.executeUpdate();

		} catch (DAOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Transfers money from current Client to choosen:");
	}

//	private void transferTo(String to, float amount) {
//		try {
//			BankCommander.currentClient.activeAccount.withdraw(amount);
//		} catch (BankException e) {
//			e.printStackTrace();
//		}
//
//		for (Client client : BankCommander.currentBank.getClients()) {
//			if (client.getName().equals(to)) {
//				client.activeAccount.deposit(amount);
//				client2 = client;
//			}
//		}
//
//	}
}
