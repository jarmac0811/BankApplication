package main.java.com.luxoft.bankapp.service;

import main.java.com.luxoft.bankapp.model.Client;

public interface ClientRegistrationListener {
	void onClientAdded(Client c);
}
