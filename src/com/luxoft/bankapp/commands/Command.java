package com.luxoft.bankapp.commands;

public interface Command {
	public void execute();

	public void printCommandInfo();
}
