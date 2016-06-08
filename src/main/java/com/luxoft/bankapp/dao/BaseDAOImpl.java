package main.java.com.luxoft.bankapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.com.luxoft.bankapp.exceptions.DAOException;

public class BaseDAOImpl implements BaseDAO {
	protected Connection conn;
	private static final Logger logger = Logger.getLogger(BaseDAOImpl.class.getName());

	@Override
	public Connection openConnection() throws DAOException {
		try {
			Class.forName("org.h2.Driver"); // this is driver for H2
			conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;mv_store=false", "sa", // login
					"" // password
			);
		
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException();
		}
	}

	@Override
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
