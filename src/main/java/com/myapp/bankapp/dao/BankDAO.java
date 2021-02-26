package com.myapp.bankapp.dao;

import com.myapp.bankapp.exceptions.DAOException;
import com.myapp.bankapp.service.BankInfo;
import com.myapp.bankapp.model.Bank;

public interface BankDAO {

	Bank getBankByName(String name) throws DAOException;

	void save(Bank bank) throws DAOException;

	void add(Bank bank) throws DAOException;

	void remove(Bank bank) throws DAOException;

	BankInfo getBankInfo(Bank bank);
}
