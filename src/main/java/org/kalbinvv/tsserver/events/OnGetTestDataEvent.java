package org.kalbinvv.tsserver.events;

import org.kalbinvv.storage.ServerStorage;
import org.kalbinvv.storage.interfaces.LogsStorage;
import org.kalbinvv.storage.interfaces.TestsStorage;
import org.kalbinvv.storage.interfaces.UsersStorage;
import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.test.Test;
import org.kalbinvv.tscore.test.TestData;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnGetTestDataEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		User user = request.from();
		Test test = (Test) request.getObject();
		ServerStorage serverStorage = TestingSystemServer.getServerHandler()
				.getServerStorage();
		UsersStorage usersStorage = serverStorage.getUsersStorage();
		LogsStorage logsStorage = serverStorage.getLogsStorage();
		TestsStorage testsStorage = serverStorage.getTestsStorage();
		if(!usersStorage.isAdminUser(user)) {
			logsStorage.addLog(user, "Не удалось получить данные теста "
					+ "'" + test.getName() + "'"
					+ ": Недостаточно прав");
			return new Response(ResponseType.Unsuccessful, "Недостаточно прав");
		}
		TestData testData = new TestData(test, testsStorage.getAnswers(test));
		logsStorage.addLog(user, "Удачное получение данных теста '" + test.getName() + "'");
		return new Response(ResponseType.Successful, testData);
	}

}
