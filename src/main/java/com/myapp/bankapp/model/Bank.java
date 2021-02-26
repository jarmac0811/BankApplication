package com.myapp.bankapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.myapp.bankapp.exceptions.ClientExistsException;
import com.myapp.bankapp.service.ClientAddedEvent;
import com.myapp.bankapp.service.ClientRegistrationListener;
import com.myapp.bankapp.service.NoDB;
import com.myapp.bankapp.service.Report;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

public class Bank implements Report, ApplicationContextAware  {

	private String name;
	
	private int id;
	@NoDB
	private Set<Client> clients = new HashSet<>();
	@NoDB
	private List<ClientRegistrationListener> listeners = new ArrayList<>();
	@NoDB
	private Map<String, Client> clientsByName;
	@NoDB
	private ApplicationContext context;

	public Bank() {
		super();
	}
	public Bank(String name) {
		this.name = name;
		listeners.add(new PrintClientListener());
		listeners.add(new EmailNotificationListener());
		clientsByName = new HashMap<>();

	}
	@Override
	public void setApplicationContext(ApplicationContext context ){
		
	this.context=context;
		
	}
	public String getName() {
		return name;
	}

	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void parseFeed(Map<String, String> feed) {
		String name = feed.get("name");

		Client client = clientsByName.get(name);
		if (client == null) {
			client = new Client(name, null, "", "", "");
			clientsByName.put(name, client);
			clients.add(client);
		}

		client.parseFeed(feed);
	}

	public Map<String, Client> getClientsByName() {
		return Collections.unmodifiableMap(clientsByName);
	}

	public Set<Client> getClients() {
		return Collections.unmodifiableSet(clients);
	}

	@Override
	public void printReport() {
		for (Client c : clients)
			c.printReport();
	}

	public void addClient(Client client) throws ClientExistsException {
		//this.context.publishEvent(new ClientAddedEvent (client));
		
		if (clients.contains(client)) {
			throw new ClientExistsException(client);
		}

		clients.add(client);
		clientsByName.put(client.getName(), client);
		for (ClientRegistrationListener crl : listeners) {
			crl.onClientAdded(client);
		}		

	}

	public void removeClient(Client client) {
		clients.remove(client);
	}
	@Component
	static class PrintClientListener implements ClientRegistrationListener,ApplicationListener <ClientAddedEvent> {
		@Override
		public void onApplicationEvent (ClientAddedEvent event){
			event.getClient().printReport();
			System.out.println("on app event");
		}
		@Override
		public void onClientAdded(Client c) {
			//clientsByName.put(c.getName(), c);
			System.out.println("on client added");
		}

	}

	class EmailNotificationListener implements ClientRegistrationListener {

		@Override
		public void onClientAdded(Client c) {
			System.out.println("Notification email for client " + c.getName() + " to be sent");

		}

	}

}