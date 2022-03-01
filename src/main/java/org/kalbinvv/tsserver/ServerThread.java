package org.kalbinvv.tsserver;

import java.io.IOException;


import java.net.ServerSocket;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Response;

public class ServerThread implements Runnable{
	
	private ServerSocket serverSocket;
	private static int serverPort = 2090;
	
	@Override
	public void run() {
		ServerHandler serverHandler = TestingSystemServer.getServerHandler();
		try {
			serverSocket = new ServerSocket(serverPort);
			while(TestingSystemServer.getServerIsRunning()) {
				Connection connection = new Connection(serverSocket.accept());
				Response response = serverHandler.handleRequest(connection);
				connection.sendResponse(response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
