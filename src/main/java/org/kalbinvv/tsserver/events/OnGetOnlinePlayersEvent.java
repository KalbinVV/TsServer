package org.kalbinvv.tsserver.events;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tsserver.ServerStorage;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnGetOnlinePlayersEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		User user = (User) request.from();
		ServerStorage serverStorage = TestingSystemServer.getServerHandler().getServerStorage();
		if(!serverStorage.isAdminUser(user)) {
			serverStorage.addLog(user, 
					"Неудачная попытка получить список пользователей: Недостаточно прав");
			return new Response(ResponseType.Unsuccessful, "Недостаточно прав!");
		}
		serverStorage.addLog(user, "Получение списка пользователей");
		return new Response(ResponseType.Successful, TestingSystemServer.getServerHandler()
				.getServerStorage().getOnlineUsers());
	}

}
