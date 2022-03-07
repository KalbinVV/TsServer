package org.kalbinvv.tsserver;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.test.Test;
import org.kalbinvv.tscore.test.TestResult;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tscore.user.UserEntry;
import org.kalbinvv.tscore.user.UserType;

public class VirtualServerStorage implements ServerStorage{

	private List<User> users;
	private List<User> onlineUsers;
	private List<Test> tests;
	private List<String> logs;
	private List<TestResult> testsResults;
	private HashMap<Test, List<List<String>>> testsAnswers;
	private boolean anonymousUsersAllowed;

	public VirtualServerStorage() {
		User defaultAdminUser = new User("admin", "admin");
		defaultAdminUser.setType(UserType.Admin);
		users = new ArrayList<User>();
		onlineUsers = new ArrayList<User>();
		tests = new ArrayList<Test>();
		logs = new ArrayList<String>();
		testsResults = new ArrayList<TestResult>();
		testsAnswers = new HashMap<Test, List<List<String>>>();
		anonymousUsersAllowed = false;
	}

	@Override
	public void addUser(User user) {
		users.add(user);
	}

	@Override
	public void removeUser(User user) {
		users.removeIf((User userNode) -> {
			return userNode.getName().equals(user.getName());
		});
	}

	@Override
	public void removeUserFromOnline(User user) {
		onlineUsers.removeIf((User userNode) -> {
			return userNode.getName().equals(user.getName());
		});
	}

	@Override
	public Response authUser(User user) {
		if(user.getName().isEmpty()) return new Response(ResponseType.Unsuccessful, 
				"Имя не может быть пустым!");
		if(user.getPass().isEmpty() && !anonymousUsersAllowed) {
			return new Response(ResponseType.Unsuccessful, 
					new String("Сервер запретил подключение анонимных пользователей!"));
		}else if(anonymousUsersAllowed && user.getPass().isEmpty()){
			System.out.println("Anonymous user joined: " + user.getName());
			onlineUsers.add(user);
			return new Response(ResponseType.Successful, user);
		}else {
			for(User userNode : users) {
				UserEntry userEntry = userNode.toEntry();
				if(userEntry.name.equals(user.getName()) 
						&& userEntry.pass.equals(user.getPass())) {
					onlineUsers.add(user);
					return new Response(ResponseType.Successful, userNode);
				}
			}
			return new Response(ResponseType.Unsuccessful, 
					new String("Неправильно введён логин или пароль!"));
		}
	}

	@Override
	public boolean isAnonymousUsersAllowed() {
		return anonymousUsersAllowed;
	}

	public void setAnonymousUsersAllowed(boolean anonymousUsersAllowed) {
		this.anonymousUsersAllowed = anonymousUsersAllowed;
	}

	@Override
	public boolean isUserExist(UserEntry user) {
		for(User userNode : users) {
			UserEntry userEntry = userNode.toEntry();
			if(userEntry.name.equals(user.name) 
					&& userEntry.pass.equals(user.pass)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<User> getUsers() {
		return users;
	}

	@Override
	public List<User> getOnlineUsers() {
		return onlineUsers;
	}

	@Override
	public List<Test> getTests() {
		return tests;
	}

	@Override
	public void addLog(User user, String log) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String logMessage = "(" + dtf.format(LocalDateTime.now()) 
		+ ") [" + user.getStringRepresentation() + "] " + log;
		System.out.println(logMessage);
		logs.add(logMessage);
	}


	@Override
	public List<String> getLogs() {
		return logs;
	}

	@Override
	public void addTest(Test test) {
		tests.add(test);

	}

	@Override
	public void setAnswers(Test test, List<List<String>> answers) {
		testsAnswers.put(test, answers);
	}

	@Override
	public List<List<String>> getAnswers(Test test) {
		for(Test tst : tests) {
			if(tst.getName().equals(test.getName())) {
				return testsAnswers.get(tst);
			}
		}
		return null;
	}

	@Override
	public void addTestResult(TestResult testResult) {
		testsResults.add(testResult);
	}

	@Override
	public List<TestResult> getTestsResults() {
		return testsResults;
	}

}
