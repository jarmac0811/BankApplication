package com.myapp.bankapp.dao;

import java.util.List;

import com.myapp.bankapp.exceptions.DAOException;
import com.myapp.bankapp.model.Client;
import com.myapp.bankapp.model.Bank;

public interface ClientDAO {
	Client findClientByName(Bank bank, String name) throws DAOException;
	Client findClientById(int id) throws DAOException;

	List<Client> getAllClients(Bank bank) throws DAOException;

	void save(Bank bank, Client client) throws DAOException;

	void add(Bank bank, Client client) throws DAOException;

	void remove(Client client) throws DAOException;

}
