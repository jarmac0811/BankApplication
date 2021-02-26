package com.myapp.bankapp.dao;

import java.util.List;

import com.myapp.bankapp.exceptions.DAOException;
import com.myapp.bankapp.model.Account;

public interface AccountDAO {
	public void save(Account account) throws DAOException;

	public void add(Account account,int clientId) throws DAOException;

	public void removeByClientId(int idClient) throws DAOException;

	public List<Account> getClientAccounts(int idClient) throws DAOException;
}
