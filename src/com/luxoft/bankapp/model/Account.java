package com.luxoft.bankapp.model;

import java.util.Map;

import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.service.Report;

public interface Account extends Report {
	int getId();

	float getBalance();

	float getOverdraft();

	void deposit(float x);

	void withdraw(float x) throws BankException;

	void decimalValue();

	String getAccountType();

	void parseFeed(Map<String, String> feed);

	void setType(String type);

	void setId(int id);

	String getStatus();

	void setStatus(String status);

	void setClient_id(int i);

	int getClient_id();
}
