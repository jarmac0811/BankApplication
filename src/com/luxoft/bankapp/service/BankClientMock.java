package com.luxoft.bankapp.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;

import com.luxoft.bankapp.model.Client;

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

	BankClientMock(Client client) {
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
				e.printStackTrace();
			}

			withdraw();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sendMessage("31");
		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				requestSocket.close();
				stop = System.currentTimeMillis();
				time = stop - start;
			} catch (IOException ioException) {
				ioException.printStackTrace();
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

		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	// public void start() {
	// Runnable clientMock = new BankClientMock(client);
	// Thread t = new Thread(clientMock);
	// t.start();
	//
	// }

}
