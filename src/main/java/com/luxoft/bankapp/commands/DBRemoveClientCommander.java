package main.java.com.luxoft.bankapp.commands;

import main.java.com.luxoft.bankapp.dao.ClientDAOImpl;
import main.java.com.luxoft.bankapp.exceptions.DAOException;
import main.java.com.luxoft.bankapp.model.Client;

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
