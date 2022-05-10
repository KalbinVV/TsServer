package org.kalbinvv.tsserver.events;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tsserver.TestingSystemServer;
import org.kalbinvv.tsserver.storage.ServerStorage;
import org.kalbinvv.tsserver.storage.interfaces.LogsStorage;
import org.kalbinvv.tsserver.storage.interfaces.UsersStorage;

public class OnGetUsersEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		User user = request.from();
		ServerStorage storage = TestingSystemServer.getServerHandler().getServerStorage();
		UsersStorage usersStorage = storage.getUsersStorage();
		LogsStorage logsStorage = storage.getLogsStorage();
		if(!usersStorage.isAdminUser(user)) {
			logsStorage.addLog(user, "Попытка просмотра пользователей: Недостаточно прав!");
			return new Response(ResponseType.Unsuccessful, "Недостаточно прав!");
		}
		logsStorage.addLog(user, "Просмотр списка пользователей");
		return new Response(ResponseType.Successful, usersStorage.getUsers());
	}

}
