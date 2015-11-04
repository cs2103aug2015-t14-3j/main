package com.cs2013t143j.TaskBuddyM.Parser;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class TBParserStub {
	
	
	final String ERROR_NO_COMMAND = "No command entered.";
	CommandParser cmd;
	DateParser date;
	ContentParser content;
	Logger logger;
	String userInput;

	public TBParserStub() {	
		logger = Logger.getLogger(Logic.class.getName());
	}
	
	// parse input to a dict
	public Map<String, String> getDictionary (String input) throws InvalidInputException, TooManyDateFoundException  {
		Map<String, String> dictionary = new HashMap<String,String>();
		
		if (userInput.equals("")) {
			throw new InvalidInputException(ERROR_NO_COMMAND);
		}

		retrieveCommand(dictionary);
		logger.log(Level.INFO, "Parsed Command: " + dictionary.get("command"));
		logger.log(Level.INFO, "Parsed Sub Command: " + dictionary.get("subCommand"));
		
		if (dictionary.get("command").equals("add") && userInput.equals("")) {
			throw new InvalidInputException("No input entered");
		}

		if(!dictionary.get("command").equals("redo") && !dictionary.get("command").equals("undo") &&
				!dictionary.get("command").equals("clear")) {
			retrieveContent(dictionary);
		}
		return dictionary;
	}
	
	private void retrieveCommand(Map<String, String> dictionary) throws InvalidInputException {
		cmd = new CommandParser(userInput);
		cmd.extractShortcutCommand(dictionary);
		cmd.extractSubCommand(dictionary);
		userInput = cmd.removeWord(dictionary.get("command"));
	} 
	
	private void retrieveContent(Map<String,String> dictionary) throws TooManyDateFoundException, InvalidInputException {
		content = new ContentParser(userInput, dictionary);
		switch(dictionary.get("command")) {
		case "add":
			dictionary = content.extractAddContent();
			break;
		case "delete":
			dictionary = content.extractDeleteContent();
			break;
		case "display":
			dictionary = content.extractDisplayContent();
			break;
		case "edit":
			dictionary = content.extractEditContent();
			break;
		case "done":
			dictionary = content.extractDoneContent();
			break;
		default:
			dictionary = content.extractSearchContent();
			break;
		}
	}
}
