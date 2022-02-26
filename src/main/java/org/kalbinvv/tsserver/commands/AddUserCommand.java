package org.kalbinvv.tsserver.commands;

import org.kalbinvv.tsserver.ServerHandler;
import org.kalbinvv.tsserver.TestingSystemServer;

public class AddUserCommand implements Command{

	@Override
	public boolean run(String label) {
		ServerHandler serverHandler = TestingSystemServer.getServerHandler();
		String[] args = label.split(" ");
		if(args.length < 2) {
			System.out.println("Invalid format!");
			return false;
		}
		String[] usersParams = args[1].split(":");
		if(usersParams.length < 2) {
			System.out.println("Invalid format!");
			return false;
		}
		serverHandler.addUser(usersParams[0], usersParams[1]);
		System.out.println("Users successful added!");
		return true;
	}

}
