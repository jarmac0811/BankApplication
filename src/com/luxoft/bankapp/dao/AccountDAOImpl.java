package com.luxoft.bankapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.service.CheckingAccount;
import com.luxoft.bankapp.service.SavingAccount;

public class AccountDAOImpl extends BaseDAOImpl implements AccountDAO {
	private Connection conn;
	private float overdraft;

	@Override
	public void add(Account account, int client_id) throws DAOException {
		int id = account.getId();
		String type = account.getAccountType();
		float balance = account.getBalance();
		String status = account.getStatus();
		if (type.equals("C"))
			overdraft = account.getOverdraft();
		String sql = "INSERT INTO ACCOUNTS(id,TYPE,BALANCE,OVERDRAFT,CLIENT_ID,STATUS) " + "VALUES(?,?,?,?,?,?)";
		PreparedStatement stmt;

		conn = openConnection();

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.setString(2, type);
			stmt.setFloat(3, balance);
			stmt.setFloat(4, overdraft);
			stmt.setInt(5, client_id);
			stmt.setString(6, status);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(Account account) throws DAOException {
		int id = account.getId();
		String type = account.getAccountType();
		float balance = account.getBalance();
		if (type.equals("C"))
			overdraft = account.getOverdraft();
		String sql = "UPDATE ACCOUNTS SET TYPE=?,BALANCE=?,OVERDRAFT=? WHERE ID=?";

		PreparedStatement stmt;

		conn = openConnection();

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, type);
			stmt.setFloat(2, balance);
			stmt.setFloat(3, overdraft);
			stmt.setInt(4, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeByClientId(int idClient) throws DAOException {
		String sql = "DELETE FROM ACCOUNTS WHERE CLIENT_ID=?";

		PreparedStatement stmt;

		conn = openConnection();

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, idClient);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Account> getClientAccounts(int idClient) throws DAOException {
		Account account;
		List<Account> list = new ArrayList<>();

		String sql = "SELECT * FROM ACCOUNTS WHERE CLIENT_ID=?";

		PreparedStatement stmt;

		conn = openConnection();

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, idClient);
			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				// Retrieve by column name
				int id = results.getInt("id");
				String type = results.getString("type");
				String status = results.getString("status");
				int balance = results.getInt("balance");
				int overdraft = results.getInt("overdraft");
				if (type.equals("C"))
					account = new CheckingAccount(balance, overdraft);
				else
					account = new SavingAccount(balance);
				account.setId(id);
				account.setType(type);
				account.setStatus(status);
				list.add(account);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}
}
