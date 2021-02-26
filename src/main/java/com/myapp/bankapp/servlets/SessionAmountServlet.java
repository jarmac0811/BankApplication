package com.myapp.bankapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionAmountServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final ServletContext context = req.getSession().getServletContext();
		synchronized (SessionAmountServlet.class) {
			Integer clientsConnected = (Integer) context.getAttribute("clientsConnected");
			if(clientsConnected==null)
				clientsConnected=0;
			System.out.println(clientsConnected);
			res.setContentType("text/html");
			PrintWriter out = res.getWriter(); 
			out.println("Sessions opened:<br>");
			out.println(clientsConnected);
		}
	}
}
