package com.luxoft.bankapp.exceptions;

import com.luxoft.bankapp.service.CheckingAccount;

public class OverDraftLimitExceededException extends NotEnoughFundsException {

	private static final long serialVersionUID = 1L;
	private CheckingAccount checkingAccount;
	private float amountToWithdraw;

	public OverDraftLimitExceededException(float amount,CheckingAccount account) {
		super(amount);
		checkingAccount = account;
		amountToWithdraw = checkingAccount.getBalance() + checkingAccount.getOverdraft();

	}

	@Override
	public String toString() {
		return "Maximum amount of money to wihdraw: " + amountToWithdraw;
	}
}
