package com.luxoft.bankapp.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(LoginServlet.class.getName());
	HttpServletRequest req;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.req=req;
		final String clientName = req.getParameter("clientName");
		if (clientName == null||clientName=="") {
			logger.warning("Client not found");
			throw new ServletException("No client specified");
		}
		req.getSession().setAttribute("clientName", clientName);
		logger.info("Client " + clientName + " logged into ATM");
		res.sendRedirect("/menu.html");
	}

}
