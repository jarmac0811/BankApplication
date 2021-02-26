package com.myapp.bankapp.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.myapp.bankapp.model.Client;

public class BankInfo implements Serializable {
	private int numberOfClients;
	private int numberOfAccounts;
	private float sumOfCredits;
	private double totalAccountSum;
	private Map<String, List<Client>> clientsByCity;

	public int getNumberOfClients() {
		return numberOfClients;
	}

	public void setNumberOfClients(int numberOfClients) {
		this.numberOfClients = numberOfClients;
	}

	public int getNumberOfAccounts() {
		return numberOfAccounts;
	}

	public void setNumberOfAccounts(int numberOfAccounts) {
		this.numberOfAccounts = numberOfAccounts;
	}

	public float getSumOfCredits() {
		return sumOfCredits;
	}

	public void setSumOfCredits(float sumOfCredits) {
		this.sumOfCredits = sumOfCredits;
	}

	public double getTotalAccountSum() {
		return totalAccountSum;
	}

	public void setTotalAccountSum(double totalAccountSum) {
		this.totalAccountSum = totalAccountSum;
	}

	public Map<String, List<Client>> getClientsByCity() {
		return clientsByCity;
	}

	public void setClientsByCity(Map<String, List<Client>> clientsByCity) {
		this.clientsByCity = clientsByCity;
	}

}
