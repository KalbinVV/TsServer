package org.kalbinvv.tsserver;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.RequestType;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.user.User;

public class ServerHandler {

	private ServerStorage serverStorage;

	public ServerHandler() {
		serverStorage = new VirtualServerStorage();
	}

	public void addUser(String login, String pass) {
		serverStorage.addUser(login, pass);
	}
	
	public Response handleRequest(Connection connection) {
		Response response = null;
		Request request = connection.getRequest();
		if(request.getRequestType() == RequestType.UserConnect) {
			User user = (User) request.getObject();
			System.out.println("Trying to connect: " 
					+ user.getName() + " " + user.getAddress().toString());
			response = serverStorage.authUser(user);
			if(response.getResponseType() == ResponseType.SuccessfulConnect) {
				System.out.println("Succesful connect: " + 
						user.getName() + " " + user.getAddress().toString());
			}else {
				System.out.println("Unsuccesful connect: " 
						+ user.getName() + " " + user.getAddress().toString());
			}
			connection.sendResponse(response);
		}
		return response;
	}


}
