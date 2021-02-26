package com.myapp.bankapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myapp.bankapp.exceptions.DAOException;
import com.myapp.bankapp.dao.ClientDAOImpl;
import com.myapp.bankapp.model.Bank;
import com.myapp.bankapp.model.Client;
import com.myapp.bankapp.service.Gender;

public class SaveClientServlet extends HttpServlet {

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
		Gender sex;
		System.out.println(city);
		System.out.println(gender);
		if (gender.equals("Male"))
			sex = Gender.MALE;
		else
			sex = Gender.FEMALE;
		Client client = new Client(clientName, sex, email, null, city);
		client.setId(id);
		client.setBalance(balance);
		try {
			new ClientDAOImpl().save(new Bank("My Bank"), client);
		} catch (DAOException e) {
			e.printStackTrace();
		}

	}
}
