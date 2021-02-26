package com.myapp.bankapp.service;

import org.springframework.context.ApplicationEvent;

import com.myapp.bankapp.model.Client;

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
