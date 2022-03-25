package org.kalbinvv.tsserver.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.kalbinvv.tsserver.storage.interfaces.LogsStorage;
import org.kalbinvv.tscore.user.User;

public class FilesLogsStorage implements LogsStorage{

	private final String logsPath;

	public FilesLogsStorage(String logsPath) {
		this.logsPath = logsPath;
	}

	@Override
	public void addLog(User user, String log) {
		File logFile = getLogFile();
		String logString = "[" + getDateString() + "] (" + user.getStringRepresentation()
		+ ") " + log + "\n";
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(logFile, true));
			bufferedWriter.write(logString);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Collection<String> getLogs() {
		List<String> logs = new ArrayList<String>();
		File logFile = getLogFile();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(logFile));
			logs = bufferedReader.lines().collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return logs;
	}
	
	private File getLogFile() {
		File logFile = new File(logsPath + File.separator + "log-" 
				+ java.time.LocalDate.now() + ".log");
		if(!logFile.exists()) {
			logFile.getParentFile().mkdirs();
		}
		return logFile;
	}
	
	private String getDateString() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
	
	
}
