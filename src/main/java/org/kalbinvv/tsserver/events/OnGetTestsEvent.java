package org.kalbinvv.tsserver.events;

import org.kalbinvv.storage.ServerStorage;
import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnGetTestsEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		User user = request.from();
		ServerStorage serverStorage = TestingSystemServer.getServerHandler()
				.getServerStorage();
		serverStorage.getLogsStorage().addLog(user, "Получение списка тестов");
		return new Response(ResponseType.Successful, serverStorage.getTestsStorage()
				.getTests());
	}

}
