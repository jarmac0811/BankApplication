package main.java.com.luxoft.bankapp.service;

import java.io.Serializable;

import main.java.com.luxoft.bankapp.exceptions.BankException;
import main.java.com.luxoft.bankapp.model.Account;

public abstract class AbstractAccount implements Account, Serializable {
	private float balance;
	private int id;
	protected String type;
	protected float overdraft;
	protected int client_id;
	protected String status;


	
	@Override
	public String getStatus() {
		return status;
	}
	@Override
	public void setStatus(String status) {
		this.status = status;
	}
	AbstractAccount(float initBalance) {
		balance = initBalance;
	}
	@Override
	public int getClient_id() {
		return client_id;
	}

	@Override
	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}


	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public float getBalance() {
		return balance;
	}

	@Override
	public void deposit(float amount) {
		balance += amount;

	}

	@Override
	public synchronized void withdraw(float amount) throws BankException {
		balance -= amount;
	}

}