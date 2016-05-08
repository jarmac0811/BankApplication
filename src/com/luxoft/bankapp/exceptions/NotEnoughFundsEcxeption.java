package com.luxoft.bankapp.exceptions;

public class NotEnoughFundsEcxeption extends BankException {

	private static final long serialVersionUID = 1L;
	protected float amount;
	public NotEnoughFundsEcxeption(float amount) {
		this.amount = amount;
	}

	public float getAmount() {
		return amount;
	}

	@Override
	public String toString(){
		return "amount exceeded by: "+amount;
	}
}

