package main.java.com.luxoft.bankapp.dao;

import java.sql.Connection;

import main.java.com.luxoft.bankapp.exceptions.DAOException;

public interface BaseDAO {
	Connection openConnection() throws DAOException;

	void closeConnection();
}