package main.java.com.luxoft.bankapp.servlets;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		final ServletContext context=event.getSession().getServletContext();
synchronized(SessionListener.class){
	Integer clientsConnected=(Integer)context.getAttribute("clientsConnected");
	if(clientsConnected==null)
		clientsConnected=0;
	else
		clientsConnected++;
	context.setAttribute("clientsConnected", clientsConnected);
}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		final ServletContext context=event.getSession().getServletContext();
synchronized(SessionListener.class){
	Integer clientsConnected=(Integer)context.getAttribute("clientsConnected");
	if(clientsConnected==null)
		clientsConnected=0;
	else
		clientsConnected--;
	context.setAttribute("clientsConnected", clientsConnected);
}

	}

}
