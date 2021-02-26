package com.myapp.bankapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	private final static Logger logger = Logger.getLogger(LoginServlet.class.getName());
	//HttpServletRequest req;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	
	}
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//System.out.println("Login successful");
		//this.req=req;
		final String clientName = req.getParameter("clientName");
		if (clientName == null||clientName=="") {
			logger.warning("Client not found");
			throw new ServletException("No client specified");
		}
		req.getSession().setAttribute("clientName", clientName);
		logger.info("Client " + clientName + " logged into ATM");
		PrintWriter out= res.getWriter();
		out.write("Login successfull...");
		res.sendRedirect("/menu.html");
	}

}
