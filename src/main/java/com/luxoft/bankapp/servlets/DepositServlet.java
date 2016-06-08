package main.java.com.luxoft.bankapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.luxoft.bankapp.dao.AccountDAOImpl;
import main.java.com.luxoft.bankapp.exceptions.DAOException;
import main.java.com.luxoft.bankapp.model.Account;
import main.java.com.luxoft.bankapp.model.Bank;
import main.java.com.luxoft.bankapp.service.BankService;

public class DepositServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String clientName = (String) req.getSession().getAttribute("clientName");
//		System.out.println("deposit is"+req.getParameter("amount"));
		float deposit=Float.parseFloat(req.getParameter("amount"));
//		try {
//			Account acc=new ClientDAOImpl().findClientByName(new Bank("My Bank"), clientName).getActiveAccount();
//			acc.deposit(deposit);
//			new AccountDAOImpl().save(acc);
//		} catch ( DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		BankService bankService=(BankService)req.getSession().getServletContext().getAttribute("bankService");
		Account acc=bankService.getClient(new Bank("My Bank"), clientName).getActiveAccount();
		acc.deposit(deposit);
		try {
			new AccountDAOImpl().save(acc);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res.sendRedirect("/balance");
}
}
