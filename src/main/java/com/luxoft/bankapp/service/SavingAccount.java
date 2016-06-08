package main.java.com.luxoft.bankapp.service;

import java.io.Serializable;
import java.util.Map;

import main.java.com.luxoft.bankapp.exceptions.BankException;
import main.java.com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import main.java.com.luxoft.bankapp.model.Account;

public class SavingAccount extends AbstractAccount implements Account, Serializable {
	public SavingAccount(float initBalance) throws IllegalArgumentException {
		super(initBalance);
		this.type = "S";
		this.status = "INACTIVE";
		if (initBalance < 0)
			throw new IllegalArgumentException();

	}

	@Override
	public void parseFeed(Map<String, String> feed) {
		this.deposit(Float.parseFloat(feed.get("balance")));
	}

	@Override
	public void printReport() {
		System.out.println("Saving account, balance: " + getBalance());

	}

	@Override
	public void withdraw(float amount) throws BankException {
		if (getBalance() >= amount)
			super.withdraw(amount);
		else
			throw new NotEnoughFundsException(amount - getBalance());

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(getBalance());
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
		SavingAccount other = (SavingAccount) obj;
		if (Float.floatToIntBits(getBalance()) != Float.floatToIntBits(other.getBalance()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SavingAccount balance:" + getBalance();
	}

	@Override
	public void decimalValue() {
		float rounded = Math.round(getBalance());
		System.out.println("Rounded balance: " + rounded);
	}

	@Override
	public String getAccountType() {
		return type;
	}

	@Override
	public float getOverdraft() {
		return 0;
	}

	@Override
	public void setStatus(String s) {
		status = s;
	}
}
