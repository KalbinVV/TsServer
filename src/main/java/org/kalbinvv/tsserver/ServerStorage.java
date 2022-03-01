package org.kalbinvv.tsserver;

import java.util.List;

import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.test.Test;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tscore.user.UserEntry;

public interface ServerStorage {

	public void addUser(User user);
	public Response authUser(User user);
	public boolean isUserExist(UserEntry user);
	public List<User> getUsers();
	public List<Test> getTests();
	
}
