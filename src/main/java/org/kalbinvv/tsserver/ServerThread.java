package org.kalbinvv.tsserver;

import java.io.IOException;

import java.net.ServerSocket;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.RequestType;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.user.User;

public class ServerThread implements Runnable{
	
	private ServerSocket serverSocket;
	private static int serverPort = 2090;
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(serverPort);
			while(TestingSystemServer.getServerIsRunning()) {
				Connection connection = new Connection(serverSocket.accept());
				Request request = connection.getRequest();
				if(request.getRequestType() == RequestType.UserConnect) {
					User user = (User) request.getObject();
					System.out.println("Connected user: " + user.getName());
					Response response = new Response(ResponseType.SuccessfulConnect, "Успешное подключение!");
					connection.sendResponse(response);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
