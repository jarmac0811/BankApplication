package com.myapp.bankapp.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.myapp.bankapp.model.Client;

public class BankClientMock implements Callable<Long> {
	private Client client;
	private Socket requestSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message;
	private String amount;
	private static final String SERVER = "localhost";
	private Long time;
	private long start, stop;
	private static final Logger logger = Logger.getLogger(BankClientMock.class.getName());

	public BankClientMock(Client client) {
		this.client = client;
	}

	@Override
	public Long call() {

		try {
			requestSocket = new Socket(SERVER, 2004);
			start = System.currentTimeMillis();
			System.out.println("Connected to localhost in port 2004");
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			try {
				message = (String) in.readObject();
				System.out.println("serveer>" + message);
			} catch (ClassNotFoundException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}

			withdraw();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
			sendMessage("31");
		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				requestSocket.close();
				stop = System.currentTimeMillis();
				time = stop - start;
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return time;
	}

	private void withdraw() {
		sendMessage("21");
		String name = client.getName();
		sendMessage(name);
		amount = "1";
		sendMessage(amount);

	}

	private void sendMessage(final String msg) {
		try {
			out.writeObject(msg);
			out.flush();

		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	// public void start() {
	// Runnable clientMock = new BankClientMock(client);
	// Thread t = new Thread(clientMock);
	// t.start();
	//
	// }

}
