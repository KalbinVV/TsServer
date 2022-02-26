package org.kalbinvv.tsserver;

import java.util.List;

import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.test.Test;
import org.kalbinvv.tscore.user.User;

public interface ServerStorage {

	public void addUser(String login, String pass);
	public Response authUser(User user);
	public List<User> getUsers();
	public List<Test> getTests();
	
}
