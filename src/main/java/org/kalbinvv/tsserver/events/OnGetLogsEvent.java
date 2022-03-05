package org.kalbinvv.tsserver.events;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tscore.user.UserType;
import org.kalbinvv.tsserver.ServerStorage;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnGetLogsEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		ServerStorage serverStorage = TestingSystemServer.getServerHandler().getServerStorage();
		User user = request.from();
		if(user.getType() != UserType.Admin) {
			serverStorage.addLog(user, "Неудачная попытка получить журнал действий: Недостаточно прав");
			return new Response(ResponseType.Unsuccessful, "Недостаточно прав!");
		}
		serverStorage.addLog(user, "Получение журнала действий");
		return new Response(ResponseType.Successful, TestingSystemServer.
				getServerHandler().getServerStorage().getLogs());
	}

}
