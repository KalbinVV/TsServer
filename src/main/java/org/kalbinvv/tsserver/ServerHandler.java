package org.kalbinvv.tsserver;

import java.util.HashMap;
import java.util.Map;

import org.kalbinvv.tsserver.storage.FilesLogsStorage;
import org.kalbinvv.tsserver.storage.ServerStorage;
import org.kalbinvv.tsserver.storage.VirtualTestsStorage;
import org.kalbinvv.tsserver.storage.VirtualUsersStorage;
import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.RequestType;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.security.Utils;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tscore.user.UserType;
import org.kalbinvv.tsserver.events.*;

public class ServerHandler {

	private ServerStorage serverStorage;
	private Map<RequestType, ServerEvent> events;

	public ServerHandler() {
		serverStorage = new ServerStorage(new VirtualUsersStorage(),
				new VirtualTestsStorage(), new FilesLogsStorage("logs"));
		User defaultAdminUser = new User("admin", Utils.convertToSHA256("admin"));
		defaultAdminUser.setType(UserType.Admin);
		serverStorage.getUsersStorage().addUser(defaultAdminUser);
		events = new HashMap<RequestType, ServerEvent>();
		registerEvents();
	}
	
	private void registerEvents() {
		registerEvent(RequestType.UserConnect, new OnAuthEvent());
		registerEvent(RequestType.UserExit, new OnQuitEvent());
		registerEvent(RequestType.AddUser, new OnUserAddEvent());
		registerEvent(RequestType.AddAdminUser, new OnAdminAddEvent());
		registerEvent(RequestType.GetOnlineUsers, new OnGetOnlineUsersEvent());
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
		registerEvent(RequestType.GetUsers, new OnGetUsersEvent());
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
