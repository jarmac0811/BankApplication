package com.myapp.bankapp.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myapp.bankapp.dao.BankDAOImpl;
import com.myapp.bankapp.model.Bank;
import com.myapp.bankapp.model.Client;
import com.myapp.bankapp.service.BankInfo;

public class FindClientsServlet extends HttpServlet {
	

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String city = req.getParameter("city");
		//System.out.println(city);
		BankInfo bankInfo = new BankDAOImpl().getBankInfo(new Bank("My Bank"));
		Map<String, List<Client>> clientsMap = bankInfo.getClientsByCity();
		List<Client> clients = clientsMap.get(city);
		
		req.setAttribute("clients", clients);
		//clients.get(0).printReport();
		req.getRequestDispatcher("/clients.jsp").forward(req, res);

	}
}
