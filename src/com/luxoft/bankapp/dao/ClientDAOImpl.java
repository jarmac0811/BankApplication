package com.luxoft.bankapp.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.Gender;

public class ClientDAOImpl extends BaseDAOImpl implements ClientDAO {
	private Client client;
	private int id;
	private Logger logger;
	private Logger logA,  logC;

	public ClientDAOImpl() {
		logger = Logger.getLogger(ClientDAOImpl.class.getName());
		logA = Logger.getLogger("LogA");
		logC = Logger.getLogger("LogC");
		try {
			
			LogManager.getLogManager().readConfiguration(new FileInputStream("logger.properties"));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Client findClientByName(Bank bank, String name) throws DAOException {
		Client client2 = new Client(name);
		String bankName = bank.getName();
		logger.log(Level.INFO, "finding client " + name);
		logA.log(Level.INFO, "test loga db");
		String sql = "SELECT C.ID,C.GENDER,C.EMAIL,C.CITY,C.PHONE FROM BANK B JOIN CLIENTS C ON B.CLIENT_ID=C.ID WHERE C.NAME=? AND B.NAME=?";
		PreparedStatement stmt;

		conn = openConnection();

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, bankName);
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				id = results.getInt("id");
				String email = results.getString("email");
				String city = results.getString("city");
				String phone = results.getString("phone");
				String sex = results.getString("gender");
				Gender gender;
				if (sex.equals("MALE"))
					gender = Gender.MALE;
				else
					gender = Gender.FEMALE;
				client2 = new Client(name, gender, email, phone, city);
				client2.setId(id);
				AccountDAOImpl adi = new AccountDAOImpl();
				List<Account> list = adi.getClientAccounts(id);
				Set<Account> set = new HashSet<>(list);
				client2.setAccounts(set);
				for (Account a : set) {
					if (a.getStatus().equals("ACTIVE"))
						client2.setActiveAccount(a);
				}
			}
			//throw new SQLException();
		} catch (SQLException e) {
			logC.log(Level.INFO, "test logC exception");
			e.printStackTrace();
		}

		client2.printReport();
		return client2;
	}
	@Override
	public Client findClientById(int id) throws DAOException {
		logger.log(Level.INFO, "finding client by id: " + id);
		logA.log(Level.INFO, "test loga db");
		String sql = "SELECT *FROM  CLIENTS WHERE ID=?";
		PreparedStatement stmt;

		conn = openConnection();

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				String name = results.getString("name");
				String email = results.getString("email");
				String city = results.getString("city");
				String phone = results.getString("phone");
				String sex = results.getString("gender");
				Gender gender;
				if (sex.equals("MALE"))
					gender = Gender.MALE;
				else
					gender = Gender.FEMALE;
				client = new Client(name, gender, email, phone, city);
				client.setId(id);
				AccountDAOImpl adi = new AccountDAOImpl();
				List<Account> list = adi.getClientAccounts(id);
				Set<Account> set = new HashSet<>(list);
				client.setAccounts(set);
				for (Account a : set) {
					if (a.getStatus().equals("ACTIVE"))
						client.setActiveAccount(a);
				}
			}
			throw new SQLException();
		} catch (SQLException e) {
			logC.log(Level.INFO, "test logC exception");
			e.printStackTrace();
		}

		client.printReport();
		return client;
	}
	@Override
	public List<Client> getAllClients(Bank bank) throws DAOException {
		List<Client> list = new ArrayList<>();
		String bankName = bank.getName();
		logger.log(Level.INFO, "getting all clients of bank:  " + bankName);
		String sql = "SELECT C.ID,C.GENDER,C.NAME,C.EMAIL,C.CITY,C.PHONE FROM BANK B JOIN CLIENTS C ON B.CLIENT_ID=C.ID WHERE B.NAME=?";

		PreparedStatement stmt;

		conn = openConnection();

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, bankName);
			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				// Retrieve by column name
				id = results.getInt("id");
				String name = results.getString("name");
				String email = results.getString("email");
				String city = results.getString("city");
				String phone = results.getString("phone");
				String sex = results.getString("gender");
				Gender gender;
				if (sex.equals("MALE"))
					gender = Gender.MALE;
				else
					gender = Gender.FEMALE;
				client = new Client(name, gender, email, phone, city);
				client.setId(id);
				AccountDAOImpl adi = new AccountDAOImpl();
				List<Account> listacc = adi.getClientAccounts(id);
				Set<Account> set = new HashSet<>(listacc);
				client.setAccounts(set);
				for (Account a : set) {
					if (a.getStatus().equals("ACTIVE"))
						client.setActiveAccount(a);
				}
				list.add(client);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void save(Bank bank, Client client) throws DAOException {
		Set<Account> set = new HashSet<>();
		set = client.getAccounts();
		String name = client.getName();
		String email = client.getEmail();
		String phone = client.getPhone();
		String city = client.getCity();
		int id = client.getId();
		//String balance=client.getBalance();
		Gender gender = client.getGender();
		String sex;
		if (gender == Gender.MALE)
			sex = "male";
		else
			sex = "female";
		logger.log(Level.INFO, "updating information about client :  " + name);
		String sql = "UPDATE CLIENTS SET NAME=?,GENDER=?,EMAIL=?,PHONE=?,CITY=? WHERE ID=?";
		PreparedStatement stmt;

		conn = openConnection();

		try {
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, name);
			stmt.setString(2, sex);
			stmt.setString(3, email);
			stmt.setString(4, phone);
			stmt.setString(5, city);
			stmt.setInt(6, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		AccountDAOImpl adi = new AccountDAOImpl();
		for (Account a : set) {
			adi.save(a);
		}

	}

	@Override
	public void add(Bank bank, Client client) throws DAOException {
		Set<Account> set = new HashSet<>();
		set = client.getAccounts();
		String bankName = bank.getName();
		String name = client.getName();
		String email = client.getEmail();
		String phone = client.getPhone();
		String city = client.getCity();
		int id = client.getId();
		String sex;
		if (client.getGender() == Gender.MALE)
			sex = "MALE";
		else
			sex = "FEMALE";
		client.getGender();
		logger.log(Level.INFO, "adding to database client: " + name);
		String sql = "INSERT INTO CLIENTS(ID,NAME,GENDER,EMAIL,PHONE,CITY) VALUES(?,?,?,?,?,?)";
		String sql2 = "INSERT INTO BANK(NAME,CLIENT_ID) VALUES(?,?)";
		PreparedStatement stmt;

		conn = openConnection();

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.setString(2, name);
			stmt.setString(3, sex);
			stmt.setString(4, email);
			stmt.setString(5, phone);
			stmt.setString(6, city);
			stmt.executeUpdate();
			stmt = conn.prepareStatement(sql2);
			stmt.setString(1, bankName);
			stmt.setInt(2, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		AccountDAOImpl adi = new AccountDAOImpl();
		for (Account a : set) {
			adi.add(a, id);
		}

	}

	@Override
	public void remove(Client client) throws DAOException {
		id = client.getId();
		logger.log(Level.INFO, "removing from database "+client.getName());
		String sql = "DELETE FROM CLIENTS WHERE ID=?";
		String sql2 = "DELETE FROM BANK WHERE CLIENT_ID=?";
		PreparedStatement stmt;

		conn = openConnection();
		AccountDAOImpl adi = new AccountDAOImpl();

		adi.removeByClientId(id);
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();
			stmt = conn.prepareStatement(sql2);
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
