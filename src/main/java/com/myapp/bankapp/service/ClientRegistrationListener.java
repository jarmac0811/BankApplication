package com.myapp.bankapp.service;

import com.myapp.bankapp.model.Client;

public interface ClientRegistrationListener {
	void onClientAdded(Client c);
}
