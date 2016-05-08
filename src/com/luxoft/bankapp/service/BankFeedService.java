package com.luxoft.bankapp.service;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.luxoft.bankapp.model.Bank;

public class BankFeedService {
	public Map<String, String> map = new LinkedHashMap<>();
	public Bank bank;

	BankFeedService(Bank bank) {
		this.bank = bank;
	}

	public void loadFeed(String folder) {

		File f = new File(folder);
		String[] tab = new String[10];
		String[] temp;
		String name = "";
		String x = "";
		for (final File fileEntry : f.listFiles()) {
			name = fileEntry.getName();

			try {
				FileReader random = new FileReader(folder + "/" + name);

				BufferedReader br = new BufferedReader(random);

				while (true) {
					try {
						x = br.readLine();//
						if (x == null)
							break;

						tab = x.split(";");

						for (String str : tab) {
							temp = str.split("=");
							map.put(temp[0], temp[1]);
							// random.close();
						}
						this.bank.parseFeed(map);
					} catch (EOFException e) {
                         e.printStackTrace();
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println("File not founded");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
