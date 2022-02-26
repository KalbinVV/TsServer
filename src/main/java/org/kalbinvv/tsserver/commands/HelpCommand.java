package org.kalbinvv.tsserver.commands;

public class HelpCommand implements Command{

	@Override
	public boolean run(String label) {
		System.out.println("Help:\nadduser Login:Pass - add new user\nstop - stop server");
		return true;
	}

}
