package org.kalbinvv.tsserver.events;

import org.kalbinvv.tsserver.storage.ServerStorage;
import org.kalbinvv.tsserver.storage.interfaces.LogsStorage;
import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tsserver.TestingSystemServer;
import org.kalbinvv.tscore.user.User;

public class OnGetTestsResultsEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		User user = request.from();
		ServerStorage serverStorage = TestingSystemServer.getServerHandler()
				.getServerStorage();
		LogsStorage logsStorage = serverStorage.getLogsStorage();
		if(!serverStorage.getUsersStorage().isAdminUser(user)) {
			logsStorage.addLog(user, "Не удалость получить результаты тестов: "
					+ "Недостаточно прав");
			return new Response(ResponseType.Unsuccessful, "Недостаточно прав");
		}
		logsStorage.addLog(user, "Получение результатов теста");
		return new Response(ResponseType.Successful, serverStorage.getTestsStorage()
				.getTestsResults());
	}

}
