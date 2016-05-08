package com.luxoft.bankapp.dao;

import java.util.List;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public interface ClientDAO {
	Client findClientByName(Bank bank, String name) throws DAOException;
	Client findClientById(int id) throws DAOException;

	List<Client> getAllClients(Bank bank) throws DAOException;

	void save(Bank bank, Client client) throws DAOException;

	void add(Bank bank, Client client) throws DAOException;

	void remove(Client client) throws DAOException;

}
