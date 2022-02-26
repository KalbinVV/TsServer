package org.kalbinvv.tsserver.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandsHandler {

	private Map<String,Command> commands;
	
	public CommandsHandler() {
		commands = new HashMap<String, Command>();
	}
	
	public void registerCommand(String label, Command command) {
		commands.put(label, command);
	}
	
	public boolean handleCommand(String label) {
		String commandStr = label.split(" ")[0];
		if(commands.containsKey(commandStr)){
			return commands.get(commandStr).run(label);
		}else {
			return false;
		}
	}
	
	
}
