package org.kalbinvv.tsserver;

public class TestingSystemServer {

	private static Thread serverThread;
	private static Boolean serverIsRunning;

	public static void main(String[] args) {
		serverThread = new Thread(new ServerThread());
		serverThread.start();
		System.out.println("Server started on port 2090!");
		serverIsRunning = true;
		try {
			serverThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static Boolean getServerIsRunning() {
		return serverIsRunning;
	}

	public static void setServerIsRunning(Boolean serverIsRunning) {
		TestingSystemServer.serverIsRunning = serverIsRunning;
	}
}

