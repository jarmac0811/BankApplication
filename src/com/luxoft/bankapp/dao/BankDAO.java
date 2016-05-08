package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.service.BankInfo;

public interface BankDAO {

	Bank getBankByName(String name) throws DAOException, BankNotFoundException;

	void save(Bank bank) throws DAOException;

	void add(Bank bank) throws DAOException;

	void remove(Bank bank) throws DAOException;

	BankInfo getBankInfo(Bank bank);
}
