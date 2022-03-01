package org.kalbinvv.tsserver.events;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tscore.user.UserEntry;
import org.kalbinvv.tscore.user.UserType;
import org.kalbinvv.tsserver.ServerStorage;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnUserAddEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		UserEntry userEntry = (UserEntry) request.getObject();
		return addUser(request, new User(userEntry.name, userEntry.pass));
	}
	
	private Response addUser(Request request, User user) {
		UserEntry userEntry = (UserEntry) request.getObject();
		Response response = null;
		ServerStorage serverStorage = TestingSystemServer.getServerHandler().getServerStorage();
		if(serverStorage.isUserExist(userEntry)) {
			response = new Response(ResponseType.Unsuccessful, "Пользователь уже существует!");
		}else {
			if(request.from().getType() == UserType.Admin) {
				serverStorage.addUser(user);
				System.out.println("User added: " + userEntry.name + "\nFrom: "
						+ request.from().getName() + " "
						+ request.from().getAddress().toString());
				response = new Response(ResponseType.Successful);
			}else {
				System.out.println("Failed user add: " + userEntry.name + "\nFrom: "
						+ request.from().getName() + " " 
						+ request.from().getAddress().toString());
				response = new Response(ResponseType.Unsuccessful, "Недостаточно прав!");
			}
		}
		return response;
	}

}
