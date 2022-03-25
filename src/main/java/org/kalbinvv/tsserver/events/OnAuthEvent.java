package org.kalbinvv.tsserver.events;

import org.kalbinvv.storage.ServerStorage;
import org.kalbinvv.storage.interfaces.LogsStorage;
import org.kalbinvv.storage.interfaces.UsersStorage;
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
		ServerStorage serverStorage = TestingSystemServer.getServerHandler()
				.getServerStorage();
		UsersStorage usersStorage = serverStorage.getUsersStorage();
		LogsStorage logsStorage = serverStorage.getLogsStorage();
		if(request.getType() == RequestType.UserConnect) {
			User user = (User) request.getObject();
			logsStorage.addLog(user, "Попытка входа");
			response = usersStorage.authUser(user);
			if(response.getType() == ResponseType.Successful) {
				logsStorage.addLog(user, "Удачная авторизация");
			}else {
				logsStorage.addLog(user, "Неудачная авторизация: " 
						+ (String) response.getObject());
			}
		}
		return response;
	}

}