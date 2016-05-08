package com.luxoft.bankapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.luxoft.bankapp.exceptions.DAOException;

public class BaseDAOImpl implements BaseDAO {
	protected Connection conn;

	@Override
	public Connection openConnection() throws DAOException {
		try {
			Class.forName("org.h2.Driver"); // this is driver for H2
			conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/bankDB2;mv_store=false", "sa", // login
					"" // password
			);
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		}
	}

	@Override
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
