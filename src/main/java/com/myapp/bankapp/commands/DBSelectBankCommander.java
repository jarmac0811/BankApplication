package com.myapp.bankapp.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.myapp.bankapp.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myapp.bankapp.dao.ClientDAOImpl;
import com.myapp.bankapp.model.Bank;
import com.myapp.bankapp.model.Client;
import com.myapp.bankapp.service.BankCommander;
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
