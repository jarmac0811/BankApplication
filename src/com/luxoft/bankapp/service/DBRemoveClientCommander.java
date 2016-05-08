package com.luxoft.bankapp.service;

import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Client;

public class DBRemoveClientCommander {
	private static Client client;
	private static ClientDAOImpl clientDao;

	public static void removeActiveClient() {
		client = BankCommander.getCurrentClient();
		clientDao = new ClientDAOImpl();
		try {
			clientDao.remove(client);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
}
