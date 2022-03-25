package org.kalbinvv.tsserver.events;

import org.kalbinvv.tsserver.storage.ServerStorage;
import org.kalbinvv.tsserver.storage.interfaces.LogsStorage;
import org.kalbinvv.tsserver.storage.interfaces.UsersStorage;
import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tscore.user.UserEntry;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnUserAddEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		UserEntry userEntry = (UserEntry) request.getObject();
		return addUser(request, new User(userEntry.name, userEntry.pass));
	}

	private Response addUser(Request request, User user) {
		UserEntry userEntry = (UserEntry) request.getObject();
		ServerStorage serverStorage = TestingSystemServer.getServerHandler()
				.getServerStorage();
		UsersStorage usersStorage = serverStorage.getUsersStorage();
		LogsStorage logsStorage = serverStorage.getLogsStorage();
		if(usersStorage.isUserExist(userEntry)) {
			logsStorage.addLog(request.from(), "Неудачная попытка создания пользователя " 
					+ userEntry.name
					+ ": Пользователь уже существует!");
			return new Response(ResponseType.Unsuccessful, "Пользователь уже существует!");
		}
		if(!usersStorage.isAdminUser(request.from())) {
			logsStorage.addLog(request.from(), "Неудачная попытка создания пользователя " 
					+ userEntry.name
					+ ": Недостаточно прав!");
			return new Response(ResponseType.Unsuccessful, "Недостаточно прав!");
		}
		usersStorage.addUser(user);
		logsStorage.addLog(request.from(), "Создание пользователя " + userEntry.name);
		return new Response(ResponseType.Successful);
	}

}
