package com.myapp.bankapp.service;
import com.myapp.bankapp.model.Account;
import com.myapp.bankapp.model.Bank;
import com.myapp.bankapp.model.Client;

public interface BankService {
	 public void addClient(Bank bank,Client client) throws ClientExistsException;
	    public void removeClient(Bank bank,Client client);
	    public void addAccount(Client client, Account account) ;
	    public void setActiveAccount(Client client, Account account);
	    public Client getClient(Bank bank, String clientName);
	    public void saveClient(Client client);
	    public Client loadClient();
	    public void clearData();
}

