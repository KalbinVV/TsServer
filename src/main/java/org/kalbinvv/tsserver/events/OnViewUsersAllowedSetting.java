package org.kalbinvv.tsserver.events;

import org.kalbinvv.storage.ServerStorage;
import org.kalbinvv.storage.interfaces.LogsStorage;
import org.kalbinvv.storage.interfaces.UsersStorage;
import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnViewUsersAllowedSetting implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		User user = (User) request.from();
		ServerStorage serverStorage = TestingSystemServer.getServerHandler()
				.getServerStorage();
		UsersStorage usersStorage = serverStorage.getUsersStorage();
		LogsStorage logsStorage = serverStorage.getLogsStorage();
		if(!usersStorage.isAdminUser(user)) {
			logsStorage.addLog(user, 
					"Неудачная попытка получения настройки авторизации для анонимных "
							+ "пользователей: " 
							+ "Недостаточно прав");
			return new Response(ResponseType.Unsuccessful, "Недостаточно прав!");
		}
		logsStorage.addLog(user, "Получение настройки авторизации для анонимных "
				+ "пользователей");
		return new Response(ResponseType.Successful, usersStorage.isAnonymousUsersAllowed());
	}

}
