package com.cs2013t143j.TaskBuddyM.Parser;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

//@@author A0126303W

public class TBParserStub {
	
	private static final String DIC_COMMAND = "command";
	private static final String DIC_SUBCOMMAND = "subCommand";
	
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_DISPLAY = "display";
	private static final String COMMAND_DONE = "done";
	private static final String COMMAND_EDIT = "edit";
	private static final String COMMAND_REDO = "redo";
	private static final String COMMAND_UNDO = "undo";
	private static final String COMMAND_CLEAR = "clear";
	private static final String COMMAND_HELP = "help";
	private static final String COMMAND_SEARCH = "search";
	
	private static final String ERROR_NO_COMMAND = "No command entered.";
	private static final String ERROR_NO_CONTENT = "No content entered.";
	
	CommandParser cmd;
	DateParser date;
	ContentParser content;
	Logger logger = Logger.getLogger(Logic.class.getName());;
	String userInput;

	public TBParserStub() {	
	}
	
	// parse input to a dict
	public Map<String, String> getDictionary (String input) throws InvalidInputException, TooManyDateFoundException  {
		Map<String, String> dictionary = new HashMap<String,String>();
		userInput = input;
		logger.log(Level.INFO, "Input to be parsed: " + userInput);
		
		checkCommandExists();
		
		retrieveCommand(dictionary);
		
		checkContentExists(dictionary);
		
		retrieveContent(dictionary);
		
		return dictionary;
	}

	private void checkContentExists(Map<String, String> dictionary) throws InvalidInputException {
		switch (dictionary.get(DIC_COMMAND)) {
		case COMMAND_ADD:
		case COMMAND_DELETE:
		case COMMAND_DONE:
		case COMMAND_EDIT:
		case COMMAND_SEARCH:
		case COMMAND_HELP:
			if (userInput.equals("") || userInput.equals(" ") || userInput.equals(null)) {
				InvalidInputException e = new InvalidInputException(ERROR_NO_CONTENT);
				logger.log(Level.SEVERE, "Exception in TBParser (checkContentExists)");
				throw e;
			}
			break;
		default:
			break;
		}
	}

	private void checkCommandExists() throws InvalidInputException {
		if (userInput.equals("")) {
			InvalidInputException e = new InvalidInputException(ERROR_NO_COMMAND);
			logger.log(Level.SEVERE, "Exception in TBParser (checkCommandExists)"); 
			throw e;
		}
	}
	
	private void retrieveCommand(Map<String, String> dictionary) throws InvalidInputException {
		cmd = new CommandParser(userInput);
		cmd.extractShortcutCommand(dictionary);
		cmd.extractSubCommand(dictionary);
		assert dictionary.get(DIC_COMMAND) != null;
		assert dictionary.get(DIC_COMMAND) != "";
		logger.log(Level.INFO, "Parsed Command: " + dictionary.get(DIC_COMMAND));
		logger.log(Level.INFO, "Parsed Sub Command: " + dictionary.get(DIC_SUBCOMMAND));
		userInput = cmd.removeWord(dictionary.get(DIC_COMMAND));
	} 
	
	private void retrieveContent(Map<String,String> dictionary) throws TooManyDateFoundException, InvalidInputException {
		assert userInput != null;
		assert userInput != "";
		
		content = new ContentParser(userInput, dictionary);
		
		if (!dictionary.get(DIC_COMMAND).equals(COMMAND_REDO) && !dictionary.get(DIC_COMMAND).equals(COMMAND_UNDO)
				&& !dictionary.get(DIC_COMMAND).equals(COMMAND_CLEAR)) {
			switch (dictionary.get(DIC_COMMAND)) {
			case COMMAND_ADD:
				dictionary = content.extractAddContent();
				break;
			case COMMAND_DELETE:
				dictionary = content.extractDeleteContent();
				break;
			case COMMAND_DISPLAY:
				dictionary = content.extractDisplayContent();
				break;
			case COMMAND_EDIT:
				dictionary = content.extractEditContent();
				break;
			case COMMAND_DONE:
				dictionary = content.extractDoneContent();
				break;
			case COMMAND_HELP:
				dictionary = content.extractHelpContent();
				break;
			default:
				dictionary = content.extractSearchContent();
				break;
			}
		}
	}
}
