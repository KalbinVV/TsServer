package org.kalbinvv.tsserver;

import java.io.IOException;


import java.net.ServerSocket;

import org.kalbinvv.tscore.config.Config;
import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Response;

public class ServerThread implements Runnable{
	
	private ServerSocket serverSocket;
	
	@Override
	public void run() {
		Config config = new Config("config.yml");
		Integer serverPort = config.getInteger("serverPort");
		System.out.println("Server is running on port: " + serverPort);
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
