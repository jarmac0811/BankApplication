package com.myapp.bankapp;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import junit.framework.TestCase;
import com.myapp.bankapp.servlets.LoginServlet;

public class LoginServletTest extends TestCase {

	 @Mock
	 HttpServletRequest request;
	 @Mock
	 HttpServletResponse response;
	 @Mock
	 HttpSession session;

	 @Mock
	 RequestDispatcher rd;


	    @Before
	 protected void setUp() throws Exception {
	  MockitoAnnotations.initMocks(this);
	 }
	    @Test
	    public void test() throws Exception {

	     HttpServletRequest request = mock(HttpServletRequest.class);
	     HttpServletResponse response = mock(HttpServletResponse.class);
	     HttpSession session = mock(HttpSession.class);
	     RequestDispatcher rd=mock(RequestDispatcher.class);
	      

	     when(request.getParameter("clientName")).thenReturn("Jan Nowak");
	     when(request.getRequestDispatcher("/menu.html")).thenReturn(rd);


	     StringWriter sw = new StringWriter();
	     PrintWriter pw = new PrintWriter(sw);
	     
	     when(response.getWriter()).thenReturn(pw);
	     System.out.println("Result1: ");
	     new LoginServlet().doPost(request, response);
	     
	     //Verify the session attribute value
	     //verify(session).setAttribute("clientName", "Jan Nowak");
	     
	     //verify(rd).forward(request, response);
	     
	     String result = sw.getBuffer().toString().trim();

	    // System.out.println("Result: "+result);
	     
	     assertEquals("Login successfull...", result);
	    }
	    
	    @Test(expected=ServletException.class)
	    public void testIfNameIsNotNull() throws ServletException, IOException{
	        HttpServletRequest request = mock(HttpServletRequest.class);
		     HttpServletResponse response = mock(HttpServletResponse.class);
		     HttpSession session = mock(HttpSession.class);
		     //System.out.println("Result2: ");

		     when(request.getParameter("clientName")).thenReturn(null);
		     when(request.getSession()).thenReturn(session);
		     
		   
				new LoginServlet().doPost(request, response);
				verify(session).setAttribute("clientName", "Jan Nowak");
		     
	   
	    }
	   }
