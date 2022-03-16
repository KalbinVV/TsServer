package org.kalbinvv.tsserver;

import java.util.List;
import java.util.Set;

import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.test.Test;
import org.kalbinvv.tscore.test.TestResult;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tscore.user.UserEntry;

public interface ServerStorage {

	public void addUser(User user);
	public void removeUser(User user);
	public Response authUser(User user);
	public boolean isAnonymousUsersAllowed();
	public void setAnonymousUsersAllowed(boolean anonymousUsersAllowed);
	public void removeUserFromOnline(User user);
	public boolean isUserExist(UserEntry user);
	public Set<User> getUsers();
	public Set<User> getOnlineUsers();
	public void addTest(Test test);
	public void removeTest(Test test);
	public List<Test> getTests();
	public void setAnswers(Test test, List<List<String>> answers);
	public List<List<String>> getAnswers(Test test);
	public void addLog(User user, String log);
	public List<String> getLogs();
	public void addTestResult(TestResult testResult);
	public List<TestResult> getTestsResults();
	public boolean isAdminUser(User user);
	
}
