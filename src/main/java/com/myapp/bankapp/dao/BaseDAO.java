package com.myapp.bankapp.dao;

import java.sql.Connection;

import com.myapp.bankapp.exceptions.DAOException;

public interface BaseDAO {
	Connection openConnection() throws DAOException;

	void closeConnection();
}