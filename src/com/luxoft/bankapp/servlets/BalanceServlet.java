package com.luxoft.bankapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.service.BankService;

public class BalanceServlet extends HttpServlet {
	//private Logger logger = Logger.getLogger(LoginServlet.class.getName());
	private float balance;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String clientName = (String) req.getSession().getAttribute("clientName");
//		try {
//			balance=new ClientDAOImpl().findClientByName(new Bank("My Bank"), clientName).getActiveAccount().getBalance();
//		} catch (DAOException e) {
//			e.printStackTrace();
//		}
		BankService bankService=(BankService)req.getSession().getServletContext().getAttribute("bankService");
		balance=bankService.getClient(new Bank("My Bank"), clientName).getActiveAccount().getBalance();
		req.setAttribute ("balance", balance);
		req.getRequestDispatcher("/balance.jsp").forward(req, res);

	}
}
