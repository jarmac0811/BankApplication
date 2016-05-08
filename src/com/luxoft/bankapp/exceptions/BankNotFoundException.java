package com.luxoft.bankapp.exceptions;

public class BankNotFoundException extends Exception {
	String name;
public BankNotFoundException(String name){
	this.name=name;
}
@Override
public String toString(){
	return "bank "+name+" not found";
}
}
