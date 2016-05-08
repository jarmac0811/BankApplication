package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.BankException;

public class NotEnoughFundsException extends BankException {

	private static final long serialVersionUID = 1L;
	protected float amount;
	public NotEnoughFundsException(float amount) {
		this.amount = amount;
	}

	

	@Override
	public String toString(){
		return "Balance exceeded by: "+amount;
	}
}
