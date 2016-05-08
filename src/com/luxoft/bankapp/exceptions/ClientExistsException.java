package com.luxoft.bankapp.exceptions;

import com.luxoft.bankapp.model.Client;

public class ClientExistsException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private Client client;
	
	public Client getClient() {
		return client;
	}

	public ClientExistsException(Client client) {
		this.client = client;
	}
	
	@Override
	public String toString() {
		return "Tried to insert existing client: " + client;
	} 

}
