package com.myapp.bankapp.commands;

import com.myapp.bankapp.exceptions.DAOException;
import com.myapp.bankapp.dao.ClientDAOImpl;
import com.myapp.bankapp.model.Client;

public class DBRemoveClientCommander {
	private static Client client;
	private static ClientDAOImpl clientDao;

	public static void removeActiveClient() {
		//client = BankCommander.getCurrentClient();
		clientDao = new ClientDAOImpl();
		try {
			clientDao.remove(client);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
}
