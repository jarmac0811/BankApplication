package main.java.com.luxoft.bankapp.dao;

import java.util.List;

import main.java.com.luxoft.bankapp.exceptions.DAOException;
import main.java.com.luxoft.bankapp.model.Account;

public interface AccountDAO {
	public void save(Account account) throws DAOException;

	public void add(Account account,int clientId) throws DAOException;

	public void removeByClientId(int idClient) throws DAOException;

	public List<Account> getClientAccounts(int idClient) throws DAOException;
}
