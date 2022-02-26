package org.kalbinvv.tsserver;

import java.util.ArrayList;
import java.util.List;

import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.test.Test;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tscore.user.UserEntry;
import org.kalbinvv.tscore.user.UserType;

public class VirtualServerStorage implements ServerStorage{
	
	private List<UserEntry> usersEntries;
	private List<User> onlineUsers;
	private List<Test> tests;
	private boolean anonymousUsersAllowed;
	
	public VirtualServerStorage() {
		usersEntries = new ArrayList<UserEntry>();
		onlineUsers = new ArrayList<User>();
		tests = new ArrayList<Test>();
		anonymousUsersAllowed = false;
	}
	
	@Override
	public void addUser(String login, String pass) {
		usersEntries.add(new UserEntry(login, pass));
	}

	@Override
	public Response authUser(User user) {
		if(user.getUserType() == UserType.Quest && !anonymousUsersAllowed) {
			return new Response(ResponseType.UnsuccessfulConnect, 
					new String("Сервер запретил подключение анонимных пользователей!"));
		}else {
			for(UserEntry userEntry : usersEntries) {
				if(userEntry.name.equals(user.getName()) 
						&& userEntry.pass.equals(user.getPass())) {
					onlineUsers.add(user);
					return new Response(ResponseType.SuccessfulConnect);
				}
			}
			return new Response(ResponseType.UnsuccessfulConnect, 
					new String("Неправильно введён логин или пароль!"));
		}
	}

	@Override
	public List<User> getUsers() {
		return onlineUsers;
	}

	@Override
	public List<Test> getTests() {
		return tests;
	}

}
