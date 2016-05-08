package com.luxoft.bankapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Client;

public class GetClientServlet extends HttpServlet {
	Client client;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String id =req.getParameter("id");
		int id2=Integer.parseInt(id);
		System.out.println("in get client servlet id="+id2);
		try {
			client=new ClientDAOImpl().findClientById(id2);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	
		req.setAttribute("client", client);
		req.getRequestDispatcher("/client.jsp").forward(req, res);
	
}
}