package main.java.com.luxoft.bankapp.service;

import org.springframework.context.ApplicationEvent;

import main.java.com.luxoft.bankapp.model.Client;

public class ClientAddedEvent extends ApplicationEvent {
	Client client;
	public ClientAddedEvent(Client c){
		super(c);
		this.client=c;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
}
