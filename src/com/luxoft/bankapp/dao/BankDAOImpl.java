package com.luxoft.bankapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankCommander;
import com.luxoft.bankapp.service.BankInfo;
import com.luxoft.bankapp.service.CheckingAccount;
import com.luxoft.bankapp.service.Gender;
import com.luxoft.bankapp.service.SavingAccount;

public class BankDAOImpl extends BaseDAOImpl implements BankDAO {
	private Connection conn;
	private Client client;
	private Account account;
	private Set<Client> clients1;
	private List<Client> clients;
	private Bank bank;
	private BankInfo bankInfo = new BankInfo();
	private ClientDAOImpl clientDao = new ClientDAOImpl();
	private Logger logger;

	public BankDAOImpl() {
		logger = Logger.getLogger(BankDAOImpl.class.getName());
	}

	@Override
	public Bank getBankByName(String name) throws DAOException, BankNotFoundException {
		clients = new ArrayList<>();
		bank = new Bank(name);
		logger.log(Level.FINE, "loading clients of " + name);
		String sql = "SELECT C.ID,C.NAME,C.GENDER,C.EMAIL,C.CITY,C.PHONE FROM BANK B JOIN CLIENTS C ON B.CLIENT_ID=C.ID WHERE B.NAME=?";
		PreparedStatement stmt;
		PreparedStatement stmt2;
		try {
			conn = openConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				int clientid = rs.getInt("id");
				String clientname = rs.getString("name");
				String clientemail = rs.getString("email");
				String clientcity = rs.getString("city");
				String clientphone = rs.getString("phone");
				String gender = rs.getString("gender");
				Gender sex;
				if (gender.equals("MALE"))
					sex = Gender.MALE;
				else
					sex = Gender.FEMALE;
				client = new Client(clientname, sex, clientemail, clientphone, clientcity);
				client.setId(clientid);
				logger.log(Level.FINE, "loading account of client");
				String sql2 = "SELECT A.ID,A.TYPE,A.BALANCE,A.OVERDRAFT,A.STATUS FROM CLIENTS C JOIN ACCOUNTS A ON C.ID=A.CLIENT_ID WHERE C.ID=?";
				conn = openConnection();
				stmt2 = conn.prepareStatement(sql2);
				stmt2.setInt(1, clientid);
				ResultSet rs2 = stmt2.executeQuery();
				while (rs2.next()) {

					int accId = rs2.getInt("id");
					String accType = rs2.getString("type");
					int accBalance = rs2.getInt("balance");
					int accOverdraft = rs2.getInt("overdraft");
					String accStatus = rs2.getString("status");
					if (accType.equals("s"))
						account = new SavingAccount(accBalance);
					else
						account = new CheckingAccount(accBalance, accOverdraft);
					account.setId(accId);
					if (accStatus.equals("ACTIVE"))
						client.setActiveAccount(account);
					else
						client.addAccount(account);
				}
				clients.add(client);
			}
			clients1 = new HashSet<>(clients);
			bank.setClients(clients1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		}

		finally {
			closeConnection();
		}
		return bank;
	}

	@Override
	public void save(Bank bank) throws DAOException {
		String bankName = bank.getName();
		logger.log(Level.FINE, "updating information of " + bankName);
		String sql = "DELETE FROM ACCOUNTS WHERE CLIENT_ID IN (SELECT C.ID FROM CLIENTS C JOIN BANK B ON C.ID=B.CLIENT_ID WHERE B.NAME=?)";
		String sql2 = "DELETE FROM CLIENTS WHERE ID IN(SELECT CLIENT_ID FROM BANK WHERE NAME=?)";
		String sql3 = "DELETE FROM BANK WHERE NAME=?";

		PreparedStatement stmt;
		try {
			conn = openConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, bankName);
			stmt.executeUpdate();

			stmt = conn.prepareStatement(sql2);
			stmt.setString(1, bankName);
			stmt.executeUpdate();

			stmt = conn.prepareStatement(sql3);
			stmt.setString(1, bankName);
			stmt.executeUpdate();

			clients1 = bank.getClients();
			for (Client client : clients1)
				clientDao.add(bank, client);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		}

		finally {
			closeConnection();
		}

	}

	@Override
	public void add(Bank bank) throws DAOException {
		logger.log(Level.FINE, "adding to database " + bank.getName());
		clients1 = bank.getClients();
		for (Client client : clients1)
			clientDao.add(bank, client);

	}

	@Override
	public void remove(Bank bank) throws DAOException {
		logger.log(Level.FINE, "removing from database " + bank.getName());
		String bankName = bank.getName();
		String sql1 = "delete from accounts where client_id in (select c.id from clients c join bank b on c.id=b.client_id where b.name=?)";
		String sql2 = "delete from clients where id in (select c.id from clients c join bank b on c.id=b.client_id where b.name=?)";
		String sql3 = "delete from bank where name=?";

		PreparedStatement stmt;
		try {
			conn = openConnection();
			stmt = conn.prepareStatement(sql1);
			stmt.setString(1, bankName);
			stmt.executeUpdate();

			stmt = conn.prepareStatement(sql2);
			stmt.setString(1, bankName);
			stmt.executeUpdate();

			stmt = conn.prepareStatement(sql3);
			stmt.setString(1, bankName);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		}

		finally {
			closeConnection();
		}

	}

	@Override
	public BankInfo getBankInfo(Bank bank) {
		logger.log(Level.FINE, "aquiring information about " + BankCommander.currentBank.getName());
		String bankName=bank.getName();
		int numberOfClients = 0;
		int numberOfAccounts = 0;
		double totalSumOnAccounts = 0;
		Map<String, List<Client>> clientsByCity = new TreeMap<>();
		List<Client> list;
		String sql1 = "SELECT COUNT(CLIENT_ID)  FROM BANK WHERE NAME=?";
		String sql2 = "SELECT COUNT(A.ID) FROM BANK B JOIN CLIENTS C ON B.CLIENT_ID=C.ID JOIN ACCOUNTS A ON A.CLIENT_ID=C.ID WHERE B.NAME=?";
		String sql3 = "SELECT SUM(A.BALANCE) FROM BANK B JOIN CLIENTS C ON B.CLIENT_ID=C.ID JOIN ACCOUNTS A ON A.CLIENT_ID=C.ID WHERE B.NAME=?";
		String sql4 = "SELECT C.ID, C.CITY, C.NAME FROM CLIENTS C JOIN BANK B ON C.ID=B.CLIENT_ID WHERE B.NAME=? ORDER BY CITY";
		PreparedStatement stmt;
		try {
			conn = openConnection();
			stmt = conn.prepareStatement(sql1);
			stmt.setString(1, bankName);
			ResultSet result = stmt.executeQuery();
			if (result.next())
				numberOfClients = result.getInt(1);

			stmt = conn.prepareStatement(sql2);
			stmt.setString(1, bankName);
			result = stmt.executeQuery();
			if (result.next())
				numberOfAccounts = result.getInt(1);
			stmt = conn.prepareStatement(sql3);
			stmt.setString(1, bankName);
			result = stmt.executeQuery();
			if (result.next())
				totalSumOnAccounts = result.getDouble(1);
			stmt = conn.prepareStatement(sql4);
			stmt.setString(1, bankName);
			result = stmt.executeQuery();
			while (result.next()) {
				String city = result.getString("city");
				String name = result.getString("name");
				int id=result.getInt(1);
				client = new Client(name);
				client.setCity(city);
				client.setId(id);
				
				list = clientsByCity.get(city);
				if (list == null) {
					list = new ArrayList<Client>();
					Client client2 = new Client(name);
					client2.setCity(city);
					client2.setId(id);
					list.add(client2);
					clientsByCity.put(city, list);
				} else if (!list.contains(client))
					list.add(client);
			}
//			 for (String city : clientsByCity.keySet()) {
//			 System.out.println("City: " + city);
//			 for (Client client : clientsByCity.get(city))
//			 System.out.println(client.getName());
//			 }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		bankInfo.setNumberOfClients(numberOfClients);
		bankInfo.setNumberOfAccounts(numberOfAccounts);
		bankInfo.setTotalAccountSum(totalSumOnAccounts);
		bankInfo.setClientsByCity(clientsByCity);
		return bankInfo;
	}
}
