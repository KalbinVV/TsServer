package org.kalbinvv.tsserver.events;

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
		if(!TestingSystemServer.getServerHandler().getServerStorage().isAdminUser(user)) {
			return new Response(ResponseType.Unsuccessful, "Недостаточно прав");
		}
		return new Response(ResponseType.Successful, TestingSystemServer.getServerHandler()
				.getServerStorage().getTestsResults());
	}

}
