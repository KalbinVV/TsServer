package org.kalbinvv.tsserver.events;

import org.kalbinvv.storage.ServerStorage;
import org.kalbinvv.storage.interfaces.LogsStorage;
import org.kalbinvv.storage.interfaces.UsersStorage;
import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tscore.user.UserEntry;
import org.kalbinvv.tscore.user.UserType;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnAdminAddEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		UserEntry userEntry = (UserEntry) request.getObject();
		User user = new User(userEntry.name, userEntry.pass);
		user.setType(UserType.Admin);
		return addUser(request, user);
	}

	private Response addUser(Request request, User user) {
		UserEntry userEntry = (UserEntry) request.getObject();
		ServerStorage serverStorage = TestingSystemServer.getServerHandler()
				.getServerStorage();
		UsersStorage usersStorage = serverStorage.getUsersStorage();
		LogsStorage logsStorage = serverStorage.getLogsStorage();
		if(usersStorage.isUserExist(userEntry)) {
			logsStorage.addLog(request.from(), "Неудачная попытка создания администратора " + userEntry.name
					+ ": Пользователь уже существует!");
			return new Response(ResponseType.Unsuccessful, "Пользователь уже существует!");
		}
		if(!usersStorage.isAdminUser(request.from())) {
			logsStorage.addLog(request.from(), "Неудачная попытка создания администратора " + userEntry.name
					+ ": Недостаточно прав!");
			return new Response(ResponseType.Unsuccessful, "Недостаточно прав!");
		}
		usersStorage.addUser(user);
		logsStorage.addLog(request.from(), "Создание администратора " + userEntry.name);
		return new Response(ResponseType.Successful);
	}

}
