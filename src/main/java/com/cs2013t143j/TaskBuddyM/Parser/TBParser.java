package com.cs2013t143j.TaskBuddyM.Parser;
import java.util.*;

public class TBParser {
	
	private static final String INVALID_INPUT = "Invalid input entered.";
	private CommandParser cmd;
//	private Content content;

	private String userInput;

	public TBParser() {
	}
	
	public Map<String, String> getDictionary (String input) throws InvalidInputException {
		Map<String, String> dictionary = new HashMap<String,String>();
		userInput = input;
		
		if (userInput.equals(null)) {
			throw new InvalidInputException(INVALID_INPUT);
		}
		
		retrieveCommand(dictionary);
		//retrieveContent(dictionary, command);
		return dictionary;
	}

//	private void retrieveContent(Map<String, String> dictionary, String command) {
//		if (!userInput.equals(null)) {
//			content = new Content(userInput,command);
//			content.extractContent(dictionary);
//		}
//	}

	private void retrieveCommand(Map<String, String> dictionary) throws InvalidInputException {
		cmd = new CommandParser(userInput);
		cmd.extractShortcutCommand(dictionary);
		cmd.extractSubCommand(dictionary);
		userInput = cmd.removeCommand(dictionary.get("command"));
	} 
}
