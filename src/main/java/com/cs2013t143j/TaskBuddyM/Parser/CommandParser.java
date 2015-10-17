package com.cs2013t143j.TaskBuddyM.Parser;
import java.util.Map;

public class CommandParser {
	
	private static final String ERROR_COMMAND = "Invalid User Command.";
	
	String userInput;
	
	public CommandParser (String str) {
		userInput = str;
	}
	
	public String extractCommand(Map<String,String> dictionary) {
		String command = "";
		if (userInput.startsWith("add") || userInput.startsWith("create") || userInput.startsWith("insert")) {
			command = "add";
			dictionary.put("command", "add");
		} else if (userInput.startsWith("delete") || userInput.startsWith("remove")) {
			command = "delete";
			dictionary.put("command", "delete");
		} else if (userInput.startsWith("display") || userInput.startsWith("show")) {
			command = "display";
			dictionary.put("command", "display");
		} else if (userInput.startsWith("edit")) {
			command = "edit";
			dictionary.put("command", "edit");
		} else if (userInput.startsWith("search") || userInput.startsWith("look for")) {
			command = "search";
			dictionary.put("command", "search");
		} else if (userInput.startsWith("undo")) {
			command = "undo";
			dictionary.put("command", "undo");
		} else if (userInput.startsWith("help")) {
			command = "help";
			dictionary.put("command", "help");
		} else {
			System.out.println(ERROR_COMMAND);
		}
		return command;
	}
	
	public String removeCommand(String command) {
		return userInput.replace(command, "").trim();
	}

}
