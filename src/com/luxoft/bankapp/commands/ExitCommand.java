package com.luxoft.bankapp.commands;

public class ExitCommand implements Command {

	@Override
	public void execute() {
		System.exit(0);

	}

	@Override
	public void printCommandInfo() {
		System.out.println("Exit");

	}

}
