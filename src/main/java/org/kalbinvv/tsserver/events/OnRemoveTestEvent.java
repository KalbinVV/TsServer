package org.kalbinvv.tsserver.events;

import org.kalbinvv.storage.ServerStorage;
import org.kalbinvv.storage.interfaces.LogsStorage;
import org.kalbinvv.storage.interfaces.TestsStorage;
import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.test.Test;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnRemoveTestEvent implements ServerEvent {

	@Override
	public Response handle(Request request, Connection connection) {
		User user = request.from();
		ServerStorage serverStorage = TestingSystemServer.getServerHandler()
				.getServerStorage();
		LogsStorage logsStorage = serverStorage.getLogsStorage();
		TestsStorage testsStorage = serverStorage.getTestsStorage();
		if(!serverStorage.getUsersStorage().isAdminUser(user)) {
			logsStorage.addLog(user, "Неудачная попытка удаления теста: Недостаточно прав");
			return new Response(ResponseType.Unsuccessful, "Недостаточно прав!");
		}
		Test test = (Test) request.getObject();
		testsStorage.removeTest(test);
		testsStorage.removeAnswers(test);
		logsStorage.addLog(user, "Удалил тест " + test.getName());
		return new Response(ResponseType.Successful);
	}

}
