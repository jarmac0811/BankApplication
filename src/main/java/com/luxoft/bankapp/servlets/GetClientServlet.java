package main.java.com.luxoft.bankapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.luxoft.bankapp.dao.ClientDAOImpl;
import main.java.com.luxoft.bankapp.exceptions.DAOException;
import main.java.com.luxoft.bankapp.model.Client;

public class GetClientServlet extends HttpServlet {
	private   Client client;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String id =req.getParameter("id");
		int id2=Integer.parseInt(id);
		try {
			client=new ClientDAOImpl().findClientById(id2);
		} catch (DAOException e) {
			e.printStackTrace();
			
		}
		req.setAttribute("client", client);
		req.getRequestDispatcher("/client.jsp").forward(req, res);
	
}
}