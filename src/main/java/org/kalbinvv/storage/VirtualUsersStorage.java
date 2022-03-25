package org.kalbinvv.storage;

import java.util.HashSet;
import java.util.Set;

import org.kalbinvv.storage.interfaces.UsersStorage;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tscore.user.UserEntry;
import org.kalbinvv.tscore.user.UserType;

public class VirtualUsersStorage implements UsersStorage{
	
	private final Set<User> users;
	private final Set<User> onlineUsers;
	private boolean anonymousUsersAllowed;
	
	public VirtualUsersStorage() {
		users = new HashSet<User>();
		onlineUsers = new HashSet<User>();
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
	public Set<User> getUsers() {
		return users;
	}

	@Override
	public Set<User> getOnlineUsers() {
		return onlineUsers;
	}

	@Override
	public boolean isAdminUser(User user) {
		UserEntry userEntry = user.toEntry();
		for(User u : users) {
			UserEntry entry = u.toEntry();
			if(entry.name.equals(userEntry.name) && entry.pass.equals(userEntry.pass)) {
				return user.getType() == UserType.Admin ? true : false;
			}
		}
		return false;
	}

}
