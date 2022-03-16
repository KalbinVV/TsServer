package org.kalbinvv.tsserver.events;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.test.Test;
import org.kalbinvv.tscore.test.TestData;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tsserver.ServerStorage;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnGetTestDataEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		User user = request.from();
		ServerStorage serverStorage = TestingSystemServer.getServerHandler().getServerStorage();
		if(!serverStorage.isAdminUser(user)) {
			return new Response(ResponseType.Unsuccessful, "Недостаточно прав");
		}
		Test test = (Test) request.getObject();
		TestData testData = new TestData(test, serverStorage.getAnswers(test));
		return new Response(ResponseType.Successful, testData);
	}

}