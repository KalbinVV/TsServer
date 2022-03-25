package org.kalbinvv.storage.interfaces;

import java.util.Set;

import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tscore.user.UserEntry;

public interface UsersStorage {
	public void addUser(User user);
	public void removeUser(User user);
	public Response authUser(User user);
	public boolean isAnonymousUsersAllowed();
	public void setAnonymousUsersAllowed(boolean anonymousUsersAllowed);
	public void removeUserFromOnline(User user);
	public boolean isUserExist(UserEntry user);
	public Set<User> getUsers();
	public Set<User> getOnlineUsers();
	public boolean isAdminUser(User user);
}
