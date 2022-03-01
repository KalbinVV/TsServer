package org.kalbinvv.tsserver;

import java.util.HashMap;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.RequestType;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tsserver.events.*;

public class ServerHandler {

	private ServerStorage serverStorage;
	private HashMap<RequestType, ServerEvent> events;

	public ServerHandler() {
		serverStorage = new VirtualServerStorage();
		events = new HashMap<RequestType, ServerEvent>();
		registerEvents();
	}
	
	private void registerEvents() {
		registerEvent(RequestType.UserConnect, new OnAuthEvent());
		registerEvent(RequestType.UserExit, new OnQuitEvent());
		registerEvent(RequestType.AddUser, new OnUserAddEvent());
		registerEvent(RequestType.AddAdminUser, new OnAdminAddEvent());
		registerEvent(RequestType.GetOnlineUsersList, new OnGetOnlinePlayersEvent());
		registerEvent(RequestType.GetAnonymousUsersAllowedSetting, new OnViewUsersAllowedSetting());
		//registerEvent(RequestType.ChangesAnonymousUsersAllowedSetting, new OnChangeUsersAllowedSetting());
	}


	public Response handleRequest(Connection connection) {
		Request request = connection.getRequest();
		Response response = null;
		if(events.containsKey(request.getType())) {
			response = events.get(request.getType()).handle(request, connection);
		}else {
			response = new Response(ResponseType.Unsuccessful, "Неизвестная операция!");
		}
		return response;
	}


	private void registerEvent(RequestType requestType, ServerEvent event) {
		events.put(requestType, event);
	}
	
	public ServerStorage getServerStorage() {
		return serverStorage;
	}

}
