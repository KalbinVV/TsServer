package org.kalbinvv.tsserver.events;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tscore.user.UserType;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnGetOnlinePlayersEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		User user = (User) request.from();
		Response response = null;
		if(user.getType() == UserType.Admin) {
			response = new Response(ResponseType.Successful, TestingSystemServer.getServerHandler()
					.getServerStorage().getOnlineUsers());
			System.out.println(user.getName() + " " + user.getAddress().toString() 
					+ " get online players list!");
		}else {
			response = new Response(ResponseType.Unsuccessful, "Недостаточно прав!");
		}
		return response;
	}

}
