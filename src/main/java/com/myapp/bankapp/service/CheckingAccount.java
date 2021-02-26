package com.myapp.bankapp.service;

import java.io.Serializable;
import java.util.Map;

import com.myapp.bankapp.exceptions.BankException;
import com.myapp.bankapp.exceptions.OverDraftLimitExceededException;
import com.myapp.bankapp.model.Account;

public class CheckingAccount extends AbstractAccount implements Account, Serializable{

	public CheckingAccount(float balance,float overdraft){
		super(balance);
		this.overdraft=overdraft;
		this.type="C";
		this.status="INACTIVE";
	}
	@Override
	public void parseFeed(Map<String, String> feed){
		this.deposit(Float.parseFloat(feed.get("balance")));
		this.overdraft=Float.parseFloat(feed.get("overdraft"));
	}
	@Override
	public void withdraw(float amount) throws BankException {
		if (amount <= getBalance() + overdraft)
			super.withdraw( amount);
		else
			throw new OverDraftLimitExceededException(amount,this);

	}

	@Override
	public float getOverdraft() {
		return overdraft;
	}

	@Override
	public void printReport() {
		System.out.println("Checking account, balance: " + getBalance());

	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(overdraft);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheckingAccount other = (CheckingAccount) obj;
		if (Float.floatToIntBits(getBalance()) != Float.floatToIntBits(other.getBalance()))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CheckingAccount balance:"+getBalance();
	}
	@Override
	public void decimalValue(){
		float rounded=Math.round(getBalance());
		System.out.println("Rounded balance: "+rounded);
	}
	@Override
	public String getAccountType(){
		return type;
	}
	@Override
	public void setStatus(String s){
		status=s;
	}
}
