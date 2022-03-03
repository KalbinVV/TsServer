package org.kalbinvv.tsserver;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.test.Question;
import org.kalbinvv.tscore.test.SimpleTest;
import org.kalbinvv.tscore.test.Test;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tscore.user.UserEntry;
import org.kalbinvv.tscore.user.UserType;

public class VirtualServerStorage implements ServerStorage{

	private List<User> users;
	private List<User> onlineUsers;
	private List<Test> tests;
	private boolean anonymousUsersAllowed;

	public VirtualServerStorage() {
		User defaultAdminUser = new User("admin", "admin");
		defaultAdminUser.setType(UserType.Admin);
		users = new ArrayList<User>(Arrays.asList(defaultAdminUser));
		onlineUsers = new ArrayList<User>();
		Test sampleTest = new SimpleTest("Test", "Sample test", new ArrayList<Question>());
		tests = new ArrayList<Test>(Arrays.asList(sampleTest));
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
					&& userEntry.pass.equals(user)) {
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

}
