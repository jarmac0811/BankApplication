package main.java.com.luxoft.bankapp.service;
import main.java.com.luxoft.bankapp.model.Account;
import main.java.com.luxoft.bankapp.model.Bank;
import main.java.com.luxoft.bankapp.model.Client;

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

