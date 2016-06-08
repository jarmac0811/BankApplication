package main.java.com.luxoft.bankapp.dao;

import main.java.com.luxoft.bankapp.exceptions.DAOException;
import main.java.com.luxoft.bankapp.model.Bank;
import main.java.com.luxoft.bankapp.service.BankInfo;

public interface BankDAO {

	Bank getBankByName(String name) throws DAOException;

	void save(Bank bank) throws DAOException;

	void add(Bank bank) throws DAOException;

	void remove(Bank bank) throws DAOException;

	BankInfo getBankInfo(Bank bank);
}
