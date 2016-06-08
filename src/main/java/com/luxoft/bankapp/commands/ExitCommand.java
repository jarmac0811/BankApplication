package main.java.com.luxoft.bankapp.commands;

import java.util.logging.Logger;

public class ExitCommand implements Command {
	private static final  Logger logger = Logger.getLogger(ExitCommand.class.getName());
	@Override
	public void execute() {
		System.exit(0);

	}

	@Override
	public void printCommandInfo() {
		System.out.println("Exit");

	}

}
