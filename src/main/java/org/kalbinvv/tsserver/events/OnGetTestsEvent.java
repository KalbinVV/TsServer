package org.kalbinvv.tsserver.events;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnGetTestsEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		User user = request.from();
		System.out.println(user.getName() + " " + user.getAddress().toString()
				+ " view tests list");
		return new Response(ResponseType.Successful, 
				TestingSystemServer.getServerHandler().getServerStorage().getTests());
	}

}
