package com.myapp.bankapp.commands;

public interface Command {
	public void execute();

	public void printCommandInfo();
}
