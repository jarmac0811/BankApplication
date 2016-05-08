package com.luxoft.bankapp.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luxoft.bankapp.dao.BankDAOImpl;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankInfo;

public class FindClientsServlet extends HttpServlet {
	Map<String, List<Client>> clientsMap;
	Client client;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String clientName = req.getParameter("name");
		final String city = req.getParameter("city");
		System.out.println("inf find servlet city " + city + "name " + clientName);
		BankInfo bankInfo = new BankDAOImpl().getBankInfo(new Bank("My Bank"));
		clientsMap = bankInfo.getClientsByCity();
		List<Client> clients = clientsMap.get(city);		
		for (Client c : clients) {
			if (c.getName().equals(clientName)) {
				client = c;
				break;
			}
		}
		req.setAttribute("client", client);
		req.getRequestDispatcher("/clients.jsp").forward(req, res);

	}
}
