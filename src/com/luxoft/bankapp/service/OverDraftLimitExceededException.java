package com.luxoft.bankapp.service;

public class OverDraftLimitExceededException extends NotEnoughFundsException {

	private static final long serialVersionUID = 1L;
	private CheckingAccount checkingAccount;
	private float amountToWithdraw;


	public OverDraftLimitExceededException(float amount,CheckingAccount account) {
		super(amount);
		checkingAccount =account;

		amountToWithdraw = account.getBalance() + account.getOverdraft();
		

	}

	@Override
	public String toString() {
		return "Maximum amount of money to withdraw: " + amountToWithdraw + " exeeded by: "+(amount-checkingAccount.getBalance()-checkingAccount.getOverdraft())+"\n";
		
	}
}