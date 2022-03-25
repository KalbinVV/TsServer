package org.kalbinvv.tsserver.events;

import org.kalbinvv.tsserver.storage.ServerStorage;
import org.kalbinvv.tsserver.storage.interfaces.LogsStorage;
import org.kalbinvv.tsserver.storage.interfaces.TestsStorage;
import org.kalbinvv.tsserver.storage.interfaces.UsersStorage;
import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.test.Test;
import org.kalbinvv.tscore.test.TestData;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnAddTestEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		User user = request.from();
		ServerStorage serverStorage = TestingSystemServer.getServerHandler()
				.getServerStorage();
		UsersStorage usersStorage = serverStorage.getUsersStorage();
		TestsStorage testsStorage = serverStorage.getTestsStorage();
		LogsStorage logsStorage = serverStorage.getLogsStorage();
		if(!usersStorage.isAdminUser(user)) {
			return new Response(ResponseType.Unsuccessful, "Недостаточно прав");
		}
		TestData testData = (TestData) request.getObject();
		Test test = testData.getTest();
		for(Test tst : testsStorage.getTests()) {
			if(tst.getName().equals(test.getName())) {
				return new Response(ResponseType.Unsuccessful, 
						"Тест с таким названием уже есть на сервере!");
			}
		}
		testsStorage.addTest(test);
		testsStorage.setAnswers(test, testData.getAnswers());
		logsStorage.addLog(user, "Добавил новый тест '" + test.getName() + "'");
		return new Response(ResponseType.Successful);
	}

}
