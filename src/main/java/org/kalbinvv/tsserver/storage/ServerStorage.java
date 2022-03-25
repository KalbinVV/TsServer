package org.kalbinvv.tsserver.storage;

import org.kalbinvv.tsserver.storage.interfaces.LogsStorage;
import org.kalbinvv.tsserver.storage.interfaces.TestsStorage;
import org.kalbinvv.tsserver.storage.interfaces.UsersStorage;

public class ServerStorage{
	
	private final UsersStorage usersStorage;
	private final TestsStorage testsStorage;
	private final LogsStorage logsStorage;
	
	public ServerStorage(UsersStorage usersStorage, 
			TestsStorage testsStorage, LogsStorage logsStorage) {
		this.usersStorage = usersStorage;
		this.testsStorage = testsStorage;
		this.logsStorage = logsStorage;
	}

	public LogsStorage getLogsStorage() {
		return logsStorage;
	}

	public TestsStorage getTestsStorage() {
		return testsStorage;
	}

	public UsersStorage getUsersStorage() {
		return usersStorage;
	}
	
}
