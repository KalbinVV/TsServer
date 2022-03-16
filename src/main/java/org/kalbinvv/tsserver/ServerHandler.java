package org.kalbinvv.tsserver;

import java.util.HashMap;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.RequestType;
import org.kalbinvv.tscore.net.Response;
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
		registerEvent(RequestType.GetOnlineUsers, new OnGetOnlinePlayersEvent());
		registerEvent(RequestType.GetAnonymousUsersAllowedSetting, new OnViewUsersAllowedSetting());
		registerEvent(RequestType.ChangesAnonymousUsersAllowedSetting, 
				new OnChangeUsersAllowedSetting());
		registerEvent(RequestType.AddTest, new OnAddTestEvent());
		registerEvent(RequestType.GetTests, new OnGetTestsEvent());
		registerEvent(RequestType.GetLogs, new OnGetLogsEvent());
		registerEvent(RequestType.CompleteTest, new OnCompleteTestEvent());
		registerEvent(RequestType.GetTestsResults, new OnGetTestsResultsEvent());
		registerEvent(RequestType.GetTestData, new OnGetTestDataEvent());
		registerEvent(RequestType.RemoveTest, new OnRemoveTestEvent());
	}


	public Response handleRequest(Connection connection) {
		Request request = connection.getRequest();
		return events.get(request.getType()).handle(request, connection);
	}


	private void registerEvent(RequestType requestType, ServerEvent event) {
		events.put(requestType, event);
	}
	
	public ServerStorage getServerStorage() {
		return serverStorage;
	}

}
