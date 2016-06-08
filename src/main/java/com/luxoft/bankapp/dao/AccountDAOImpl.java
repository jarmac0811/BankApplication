package main.java.com.luxoft.bankapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.com.luxoft.bankapp.exceptions.DAOException;
import main.java.com.luxoft.bankapp.model.Account;
import main.java.com.luxoft.bankapp.service.CheckingAccount;
import main.java.com.luxoft.bankapp.service.SavingAccount;

public class AccountDAOImpl extends BaseDAOImpl implements AccountDAO {
	private float overdraft;
	private PreparedStatement stmt;
	private static final Logger logger = Logger.getLogger(AccountDAOImpl.class.getName());

	@Override
	public void add(Account account, int clientId) throws DAOException {
		int id = account.getId();
		String type = account.getAccountType();
		float balance = account.getBalance();
		String status = account.getStatus();
		if (("C").equals(type))
			overdraft = account.getOverdraft();
		String sql = "INSERT INTO ACCOUNTS(id,TYPE,BALANCE,OVERDRAFT,CLIENT_ID,STATUS) " + "VALUES(?,?,?,?,?,?)";
		

		conn = openConnection();

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.setString(2, type);
			stmt.setFloat(3, balance);
			stmt.setFloat(4, overdraft);
			stmt.setInt(5, clientId);
			stmt.setString(6, status);
			stmt.executeUpdate();
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
	public void save(Account account) throws DAOException {
		int id = account.getId();
		String type = account.getAccountType();
		float balance = account.getBalance();
		if (("C").equals(type))
			overdraft = account.getOverdraft();
		String sql = "UPDATE ACCOUNTS SET TYPE=?,BALANCE=?,OVERDRAFT=? WHERE ID=?";

		

		conn = openConnection();

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, type);
			stmt.setFloat(2, balance);
			stmt.setFloat(3, overdraft);
			stmt.setInt(4, id);
			stmt.executeUpdate();
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
	public void removeByClientId(int idClient) throws DAOException {
		String sql = "DELETE FROM ACCOUNTS WHERE CLIENT_ID=?";

	

		conn = openConnection();

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, idClient);
			stmt.executeUpdate();
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
	public List<Account> getClientAccounts(int idClient) throws DAOException {
		Account account;
		List<Account> list = new ArrayList<>();

		String sql = "SELECT * FROM ACCOUNTS WHERE CLIENT_ID=?";

		

		conn = openConnection();

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, idClient);
			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				int id = results.getInt("id");
				String type = results.getString("type");
				String status = results.getString("status");
				float balance = results.getFloat("balance");
				overdraft = results.getFloat("overdraft");
				if (("C").equals(type))
					account = new CheckingAccount(balance, overdraft);
				else
					account = new SavingAccount(balance);
				account.setId(id);
				account.setType(type);
				account.setStatus(status);
				list.add(account);

			}
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
		return list;

	}
}
