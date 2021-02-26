package com.myapp.bankapp;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import junit.framework.TestCase;
import com.myapp.bankapp.model.Client;
import com.myapp.bankapp.service.BankInfo;
import com.myapp.bankapp.servlets.FindClientsServlet;
import com.myapp.bankapp.servlets.LoginServlet;

public class FindClientServletTest extends TestCase {

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	HttpSession session;

	@Mock
	RequestDispatcher rd;

	//@Before
	protected void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	// @Test
	// public void test() throws Exception {
	public static void main(String[] args) throws ServletException, IOException {
		
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		RequestDispatcher rd = mock(RequestDispatcher.class);
		BankInfo bankInfo = mock(BankInfo.class);
		when(request.getParameter("city")).thenReturn("Warsaw");
		// when(bankDAO.getBankInfo(new Bank("My Bank"))).thenReturn(bankInfo);

		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/clients.jsp")).thenReturn(rd);
		new FindClientsServlet().doGet(request, response);
		// Verify the session attribute value
		// verify(session).setAttribute("clientName", "Jan Nowak");

		// verify(rd).forward(request, response);
		List<Client> clients = (List<Client>) request.getAttribute("clients");
		System.out.println(clients.size());
		assertEquals(2, clients.size());
	}

	// @Test(expected = ServletException.class)
	public void testIfNameIsNotNull() throws ServletException, IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		RequestDispatcher rd = mock(RequestDispatcher.class);

		when(request.getParameter("city")).thenReturn(null);

		new LoginServlet().doPost(request, response);

	}
}
