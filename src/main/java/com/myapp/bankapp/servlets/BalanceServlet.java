package com.myapp.bankapp.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myapp.bankapp.exceptions.DAOException;
import com.myapp.bankapp.dao.ClientDAOImpl;
import com.myapp.bankapp.model.Bank;

public class BalanceServlet extends HttpServlet {
	private final static Logger logger = Logger.getLogger(LoginServlet.class.getName());
	private float balance;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	
	}
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String clientName = (String) req.getSession().getAttribute("clientName");
		try {
			balance=new ClientDAOImpl().findClientByName(new Bank("My Bank"), clientName).getActiveAccount().getBalance();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
//		BankService bankService=(BankService)req.getSession().getServletContext().getAttribute("bankService");
//		System.out.println(bankService);
//		Client client=bankService.getClient(new Bank("My Bank"), clientName);
//				.getActiveAccount()
//				.getBalance();
		req.setAttribute ("balance", balance);
		//client.printReport();
		req.getRequestDispatcher("/balance.jsp").forward(req, res);

	}
}
