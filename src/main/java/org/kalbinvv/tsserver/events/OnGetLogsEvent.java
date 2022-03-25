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

public class OnGetLogsEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		ServerStorage serverStorage = TestingSystemServer.getServerHandler()
				.getServerStorage();
		UsersStorage usersStorage = serverStorage.getUsersStorage();
		LogsStorage logsStorage = serverStorage.getLogsStorage();
		User user = request.from();
		if(!usersStorage.isAdminUser(user)) {
			logsStorage.addLog(user, "Неудачная попытка получить журнал действий: "
					+ "Недостаточно прав");
			return new Response(ResponseType.Unsuccessful, "Недостаточно прав!");
		}
		logsStorage.addLog(user, "Получение журнала действий");
		return new Response(ResponseType.Successful, logsStorage.getLogs());
	}

}
