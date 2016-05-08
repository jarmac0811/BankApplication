package com.luxoft.bankapp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public class DBSelectBankCommander {
	private static List<Client> list;
	private static Bank bank;
	private static ClientDAOImpl clientdao;

	public static void getBankByName(String name) {
		bank = new Bank(name);
		clientdao = new ClientDAOImpl();

		try {
			list = clientdao.getAllClients(bank);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		Set<Client> set = new HashSet<>(list);
		bank.setClients(set);
		BankCommander.currentBank = bank;

	}
}
