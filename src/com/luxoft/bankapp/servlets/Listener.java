package com.luxoft.bankapp.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.luxoft.bankapp.service.BankService;

public class Listener implements ServletContextListener{

	
	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ApplicationContext context =
                new FileSystemXmlApplicationContext("servlets-application-context.xml");
		BankService bankService=(BankService)context.getBean("bankService");
		System.out.println("in listener");
		event.getServletContext().setAttribute("bankService", bankService);

	}

}
