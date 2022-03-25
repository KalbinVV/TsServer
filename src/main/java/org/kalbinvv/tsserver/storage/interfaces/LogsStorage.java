package org.kalbinvv.tsserver.storage.interfaces;

import java.util.Collection;

import org.kalbinvv.tscore.user.User;

public interface LogsStorage {
	public void addLog(User user, String log);
	public Collection<String> getLogs();
}
