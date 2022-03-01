package org.kalbinvv.tsserver.events;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.RequestType;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnAuthEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		Response response = null;
		if(request.getType() == RequestType.UserConnect) {
			User user = (User) request.getObject();
			System.out.println("Trying to connect: " 
					+ user.getName() + " " + user.getAddress().toString());
			response = TestingSystemServer.getServerHandler().getServerStorage().authUser(user);
			if(response.getType() == ResponseType.Successful) {
				System.out.println("Succesful connect: " + 
						user.getName() + " " + user.getAddress().toString());
			}else {
				System.out.println("Unsuccesful connect: " 
						+ user.getName() + " " + user.getAddress().toString());
			}
		}
		return response;
	}
	
}