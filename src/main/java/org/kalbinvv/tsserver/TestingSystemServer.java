package org.kalbinvv.tsserver;

import java.util.Scanner;

import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tscore.user.UserType;
import org.kalbinvv.tsserver.commands.*;

public class TestingSystemServer {

	private static Thread serverThread;
	private static Boolean serverIsRunning;
	private static ServerHandler serverHandler;
	private static CommandsHandler commandsHandler;
	private static final int ServerPort = 2090;

	public static void main(String[] args) {
		commandsHandler= new CommandsHandler();
		registerCommands();
		setServerHandler(new ServerHandler());
		serverThread = new Thread(new ServerThread());
		serverThread.start();
		System.out.println("Сервер запущен на порту " + ServerPort);
		User defaultAdminUser = new User("admin", "admin");
		defaultAdminUser.setType(UserType.Admin);
		serverHandler.getServerStorage().getUsersStorage().addUser(defaultAdminUser);
		serverIsRunning = true;
		Scanner scanner = new Scanner(System.in);
		while(serverIsRunning) {
			String command = scanner.nextLine();
			if(!commandsHandler.handleCommand(command)) {
				System.out.println("Command can't run!");
			}
		}
		scanner.close();
	}
	
	private static void registerCommands() {
		commandsHandler.registerCommand("help", new HelpCommand());
		commandsHandler.registerCommand("adduser", new AddUserCommand());
	}

	public static Boolean getServerIsRunning() {
		return serverIsRunning;
	}

	public static void setServerIsRunning(Boolean serverIsRunning) {
		TestingSystemServer.serverIsRunning = serverIsRunning;
	}

	public static ServerHandler getServerHandler() {
		return serverHandler;
	}

	public static void setServerHandler(ServerHandler serverHandler) {
		TestingSystemServer.serverHandler = serverHandler;
	}
}

