package com.myapp.bankapp.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.myapp.bankapp.exceptions.FeedException;
import com.myapp.bankapp.service.CheckingAccount;
import com.myapp.bankapp.service.Gender;
import com.myapp.bankapp.service.Report;
import com.myapp.bankapp.service.SavingAccount;

public class Client implements Report, Comparable<Client>, Serializable {
	private int id;
	private String name;
	private String email;
	private String phone;
	private Gender gender = Gender.MALE;
	private String city;
	private Set<Account> accounts = new HashSet<>();;
	public Account activeAccount;
	private String balance;

	public Client(String name, Gender gender, String email, String phone, String city) {
		this.name = name;
		this.gender = gender;
		this.email = email;
		this.phone = phone;
		this.city = city;
	}

	public Client(String name) {
		this.name = name;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	private Account getAccount(String accountType) {
		for (Account acc : accounts) {
			if (acc.getAccountType().equals(accountType)) {
				return acc;
			}
		}
		return createAccount(accountType);
	}

	/**
	 * This method creates account by its type
	 */
	private Account createAccount(String accountType) {
		Account acc;
		if ("s".equals(accountType)) {
			acc = new SavingAccount(0);
		} else if ("c".equals(accountType)) {
			acc = new CheckingAccount(0, 0);
		} else {
			throw new FeedException("Account type not found " + accountType);
		}
		accounts.add(acc);
		return acc;
	}

	public void parseFeed(Map<String, String> feed) {
		String gender = feed.get("gender");
		if (gender.equals("f"))
			this.gender = Gender.FEMALE;
		if (gender.equals("m"))
			this.gender = Gender.MALE;
		String accountType = feed.get("accounttype");

		Account acc = getAccount(accountType);

		acc.parseFeed(feed);
		setActiveAccount(acc);

	}

	public void setActiveAccount(Account activeAccount) {
		activeAccount.setStatus("ACTIVE");
		this.activeAccount = activeAccount;
		for (Account acc : accounts) {
			acc.setStatus("INACTIVE");
		}
		accounts.add(activeAccount);
		balance = ("" + activeAccount.getBalance());
	}

	@Override
	public void printReport() {
		System.out.println(name + " email: " + email + ", telephone number: " + phone + " City: " + city
				+ ", active account: " + balance);
		System.out.println("List of accounts:");
		for (Account a : accounts)
			a.printReport();
		System.out.print("\n");

	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Account getActiveAccount() {
		return activeAccount;
	}

	public String getClientSalutation() {
		return gender.getSalutation();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Set<Account> getAccounts() {
		return Collections.unmodifiableSet(accounts);
	}

	public void addAccount(Account account) {
		accounts.add(account);

	}

	public void removeAcount(Account account) {
		accounts.remove(account);

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClientSalutation()).append(name);
		sb.append(" ");
		for (Account a : accounts) {
			sb.append(a);
			sb.append(" ");
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	};

	@Override
	public int compareTo(Client client) {

		return this.name.compareTo(client.name);
	}
}
