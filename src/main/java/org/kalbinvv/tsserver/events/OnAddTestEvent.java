package org.kalbinvv.tsserver.events;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.test.Test;
import org.kalbinvv.tscore.test.TestData;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tscore.user.UserType;
import org.kalbinvv.tsserver.ServerStorage;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnAddTestEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		User user = request.from();
		if(user.getType() != UserType.Admin) {
			return new Response(ResponseType.Unsuccessful, "Недостаточно прав");
		}
		TestData testData = (TestData) request.getObject();
		ServerStorage serverStorage = TestingSystemServer.getServerHandler().getServerStorage();
		Test test = testData.getTest();
		serverStorage.addTest(test);
		serverStorage.setAnswers(test, testData.getAnswers());
		serverStorage.addLog(user, "Добавил новый тест '" + test.getName() + "'");
		return new Response(ResponseType.Successful);
	}

}
