package main.java.com.luxoft.bankapp.exceptions;

public class NotEnoughFundsException extends BankException {

	private static final long serialVersionUID = 1L;
	protected float amount;
	public NotEnoughFundsException(float amount) {
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

