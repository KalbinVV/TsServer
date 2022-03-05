package org.kalbinvv.tsserver.events;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tscore.user.UserType;
import org.kalbinvv.tsserver.ServerStorage;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnViewUsersAllowedSetting implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		User user = (User) request.from();
		ServerStorage serverStorage = TestingSystemServer.getServerHandler().getServerStorage();
		if(user.getType() != UserType.Admin) {
			serverStorage.addLog(user, 
					"Неудачная попытка получения настройки авторизации для анонимных пользователей: " 
					+ "Недостаточно прав");
			return new Response(ResponseType.Unsuccessful, "Недостаточно прав!");
		}
		serverStorage.addLog(user, "Получение настройки авторизации для анонимных пользователей");
		return new Response(ResponseType.Successful, serverStorage.isAnonymousUsersAllowed());
	}

}