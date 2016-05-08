package com.luxoft.bankapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.Gender;

public class SaveClientServlet extends HttpServlet {
	Client client;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String ids = req.getParameter("id");
		final int id = Integer.parseInt(ids);
		final String clientName = req.getParameter("name");
		final String city = req.getParameter("city");
		final String gender = req.getParameter("gender");
		final String email = req.getParameter("email");
		final String balance = req.getParameter("balance");
		client=new Client(clientName,Gender.MALE,email,null,city);
		client.setId(id);
		try {
			new ClientDAOImpl().save(new Bank("My Bank"),client);
		} catch (DAOException e) {
			e.printStackTrace();
		}


	}
}
