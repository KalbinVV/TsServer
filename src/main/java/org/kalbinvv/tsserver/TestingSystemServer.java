package org.kalbinvv.tsserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.kalbinvv.tscore.test.Question;
import org.kalbinvv.tscore.test.QuestionType;
import org.kalbinvv.tscore.test.SimpleQuestion;
import org.kalbinvv.tscore.test.SimpleTest;
import org.kalbinvv.tscore.test.Test;
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
		serverHandler.getServerStorage().addUser(defaultAdminUser);
		ArrayList<Question> questions = new ArrayList<Question>(
				Arrays.asList(
						new SimpleQuestion("1", QuestionType.CheckBoxes, 
								Arrays.asList("1", "2", "3")),
						new SimpleQuestion("2", QuestionType.TextFields, 
								Arrays.asList("1", "2", "3")))
				);
		List<List<String>> answers = new ArrayList<List<String>>(
				Arrays.asList(new ArrayList<String>(
						Arrays.asList("1")
						),
						new ArrayList<String>(
						Arrays.asList("1", "3")		
						))
				);
		Test sampleTest = new SimpleTest("Тест", "Пример теста", questions, 0);
		serverHandler.getServerStorage().addTest(sampleTest);
		serverHandler.getServerStorage().setAnswers(sampleTest.getID(), answers);
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

