package org.kalbinvv.tsserver;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.RequestType;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tscore.user.UserEntry;
import org.kalbinvv.tscore.user.UserType;

public class ServerHandler {

	private ServerStorage serverStorage;

	public ServerHandler() {
		serverStorage = new VirtualServerStorage();
	}

	public void addUser(String login, String pass) {
		getServerStorage().addUser(login, pass);
	}

	public Response handleRequest(Connection connection) {
		Response response = null;
		Request request = connection.getRequest();
		if(request.getType() == RequestType.UserConnect) {
			User user = (User) request.getObject();
			System.out.println("Trying to connect: " 
					+ user.getName() + " " + user.getAddress().toString());
			response = getServerStorage().authUser(user);
			if(response.getType() == ResponseType.Successful) {
				System.out.println("Succesful connect: " + 
						user.getName() + " " + user.getAddress().toString());
			}else {
				System.out.println("Unsuccesful connect: " 
						+ user.getName() + " " + user.getAddress().toString());
			}
		}else if(request.getType() == RequestType.AddUser){
			UserEntry userEntry = (UserEntry) request.getObject();
			if(serverStorage.isUserExist(userEntry)) {
				response = new Response(ResponseType.Unsuccessful, "Пользователь уже существует!");
			}else {
				if(request.from().getType() == UserType.Admin) {
					serverStorage.addUser(userEntry.name, userEntry.pass);
					System.out.println("User added: " + userEntry.name + "\nFrom: "
							+ request.from().getName());
					response = new Response(ResponseType.Successful);
				}else {
					System.out.println("Failed user add: " + userEntry.name + "\nFrom: "
							+ request.from().getName() + "/" 
							+ request.from().getAddress().toString());
					response = new Response(ResponseType.Unsuccessful, "Недостаточно прав!");
				}
			}
		}
		return response;
	}

	public ServerStorage getServerStorage() {
		return serverStorage;
	}


}
