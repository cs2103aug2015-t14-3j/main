package com.cs2013t143j.TaskBuddyM.Parser;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class TBParserStub {
	
	final String DIC_COMMAND = "command";
	final String DIC_SUBCOMMAND = "subCommand";
	
	final String ERROR_NO_COMMAND = "No command entered.";
	final String ERROR_NO_TASK = "No task entered.";
	
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
		userInput = input;
		logger.log(Level.INFO, "Input to be parsed: " + userInput);
		
		checkCommandExists();
		
		retrieveCommand(dictionary);
		
		logger.log(Level.INFO, "Parsed Command: " + dictionary.get(DIC_COMMAND));
		logger.log(Level.INFO, "Parsed Sub Command: " + dictionary.get(DIC_SUBCOMMAND));
		
		checkTaskExists(dictionary);
		
		retrieveContent(dictionary);

		return dictionary;
	}

	private void checkTaskExists(Map<String, String> dictionary) throws InvalidInputException {
		if (dictionary.get(DIC_COMMAND).equals("add") && 
				(userInput.equals("") || userInput.equals(" ") || userInput.equals(null))) {
			InvalidInputException e = new InvalidInputException(ERROR_NO_TASK);
			logger.log(Level.SEVERE, "Exception(checkTaskExists)"); 
			throw e;
		}
	}

	private void checkCommandExists() throws InvalidInputException {
		if (userInput.equals("")) {
			InvalidInputException e = new InvalidInputException(ERROR_NO_COMMAND);
			logger.log(Level.SEVERE, "Exception(checkCommandExists)"); 
			throw e;
		}
	}
	
	private void retrieveCommand(Map<String, String> dictionary) throws InvalidInputException {
		cmd = new CommandParser(userInput);
		cmd.extractShortcutCommand(dictionary);
		cmd.extractSubCommand(dictionary);
		userInput = cmd.removeWord(dictionary.get(DIC_COMMAND));
	} 
	
	private void retrieveContent(Map<String,String> dictionary) throws TooManyDateFoundException, InvalidInputException {
		content = new ContentParser(userInput, dictionary);
		
		if (!dictionary.get("command").equals("redo") && !dictionary.get("command").equals("undo")
				&& !dictionary.get("command").equals("clear")) {
			switch (dictionary.get(DIC_COMMAND)) {
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
}
