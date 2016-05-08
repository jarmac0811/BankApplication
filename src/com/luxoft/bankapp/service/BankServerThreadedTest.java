package com.luxoft.bankapp.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.luxoft.bankapp.model.Client;

public class BankServerThreadedTest {
	private Socket requestSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message;
	private Client client;
	private final String SERVER = "localhost";
	private ExecutorService executor;
	private List<Future<Long>> clientList;
	private int average;
	private long sum;

	public void start() {
		Client client = findClientByName("Jan");
		double amount = client.getActiveAccount().getBalance();
		System.out.println("amount before" + amount);
		executor = Executors.newFixedThreadPool(1000);
		clientList = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			Future<Long> future = executor.submit(new BankClientMock(client));
			clientList.add(future);
		}
		for (int i = 0; i < 1000; i++)
			try {
				sum += clientList.get(i).get();
				// System.out.println("time: " + i + " " +
				// clientList.get(i).get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		average = (int) sum / 1000;

		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		client = findClientByName("Jan");
		double amount2 = client.getActiveAccount().getBalance();
		System.out.println("amount after" + amount2);
		System.out.println("average time: " + average);

	}

	Client findClientByName(String name) {
		try {
			requestSocket = new Socket(SERVER, 2004);
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

			sendMessage("20");
			try {
				sendMessage(name);
				client = (Client) in.readObject();

			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
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
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
		return client;
	}

	private void sendMessage(final String msg) {
		try {
			out.writeObject(msg);
			out.flush();

		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public static void main(final String args[]) {
		BankServerThreadedTest test = new BankServerThreadedTest();

		test.start();

	}
}