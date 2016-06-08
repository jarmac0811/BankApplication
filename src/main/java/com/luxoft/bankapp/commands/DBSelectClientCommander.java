package main.java.com.luxoft.bankapp.commands;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import main.java.com.luxoft.bankapp.dao.ClientDAOImpl;
import main.java.com.luxoft.bankapp.exceptions.DAOException;
import main.java.com.luxoft.bankapp.model.Client;
import main.java.com.luxoft.bankapp.service.BankCommander;
@Component
public class DBSelectClientCommander {
	private  Scanner scanner;
	private  String bankName;
	@Autowired
	private  ClientDAOImpl clientdao;
	
	@Autowired
	private BankCommander bankCommander;
	private ApplicationContext context;
	public BankCommander getBankCommander() {
		return bankCommander;
	}
	public void setBankCommander(BankCommander bankCommander) {
		this.bankCommander = bankCommander;
	}
	public  ClientDAOImpl getClientdao() {
		return clientdao;
	}
	public  void setClientdao(ClientDAOImpl clientdao) {
		this.clientdao = clientdao;
	}
	public  void setActiveClient(String name) {
		// scanner=new Scanner(System.in);
//		ApplicationContext context =
//                new ClassPathXmlApplicationContext("file:resources/application-context.xml");
//          bankCommander =(BankCommander) context.getBean("bankCommander");
		String clientName = name;
		if (BankCommander.currentBank == null) {
			System.out.println("Enter name of the bank to set active");
			bankName = scanner.nextLine();
			context = new ClassPathXmlApplicationContext("file:resources/application-context.xml");
			DBSelectBankCommander dbsBank = (DBSelectBankCommander) context.getBean("DBSelectBankCommander");
		
			dbsBank.getBankByName(bankName);
		}
		 
		try {
			//System.out.println(bankCommander.toString());
			Client client = clientdao.findClientByName(BankCommander.currentBank, clientName);
			//client.printReport();
			bankCommander.setCurrentClient(client);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
}
