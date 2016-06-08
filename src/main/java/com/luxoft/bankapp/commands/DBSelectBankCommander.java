package main.java.com.luxoft.bankapp.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.com.luxoft.bankapp.dao.ClientDAOImpl;
import main.java.com.luxoft.bankapp.exceptions.DAOException;
import main.java.com.luxoft.bankapp.model.Bank;
import main.java.com.luxoft.bankapp.model.Client;
import main.java.com.luxoft.bankapp.service.BankCommander;
@Component
public class DBSelectBankCommander {
	private  List<Client> list;
	private  Bank bank;
	@Autowired
	private  ClientDAOImpl clientdao;
	@Autowired
	private BankCommander bankCommander;
	public  void getBankByName(String name) {
		bank = new Bank(name);

		try {
			list = clientdao.getAllClients(bank);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		Set<Client> set = new HashSet<>(list);
		bank.setClients(set);
		//bank.printReport();
		BankCommander.setCurrentBank(bank);
		BankCommander.currentBank.printReport();

	}
}
