package org.kalbinvv.tsserver.events;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnQuitEvent implements ServerEvent {

	@Override
	public Response handle(Request request, Connection connection) {
		User user = (User) request.getObject();
		TestingSystemServer.getServerHandler().getServerStorage().removeUserFromOnline(user);
		System.out.println("User " + user.getName() + " " + user.getAddress().toString()
				+ " leaved!");
		return null;
	}
	
}
