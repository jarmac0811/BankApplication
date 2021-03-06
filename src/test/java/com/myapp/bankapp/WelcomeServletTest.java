package com.myapp.bankapp;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import junit.framework.TestCase;
import com.myapp.bankapp.servlets.WelcomeServlet;

public class WelcomeServletTest extends TestCase {

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


	     StringWriter sw = new StringWriter();
	     PrintWriter pw = new PrintWriter(sw);
	     
	     when(response.getWriter()).thenReturn(pw);
	     
	     new WelcomeServlet().doGet(request, response);
	    
	     
	     String result = sw.getBuffer().toString().trim();

	     System.out.println("Result: "+result);
	     
	     assertEquals("Hello I'am ATM! <br>\n"
	     		+ "<a href='login.html'>Login</a>", result);
	    }
	    

	   }
