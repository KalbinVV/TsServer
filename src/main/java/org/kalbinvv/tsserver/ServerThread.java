package org.kalbinvv.tsserver;

import java.io.IOException;


import java.net.ServerSocket;

import org.kalbinvv.tscore.net.Connection;

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
				serverHandler.handleRequest(connection);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
