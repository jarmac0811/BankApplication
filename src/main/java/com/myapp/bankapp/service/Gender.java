package com.myapp.bankapp.service;

import java.io.Serializable;

public enum Gender implements Serializable {

	MALE("Mr ") {
		@Override
		public void work() {
			System.out.println("I work like an engineer");
		}
	},
	FEMALE("Mrs ") {
		@Override
		public void work() {
			System.out.println("I work like a doctor");
		}
	};
	private String salutation;

	private Gender(String s) {
		salutation = s;
	}

	public String getSalutation() {
		return salutation;
	}

	public abstract void work();
	
}