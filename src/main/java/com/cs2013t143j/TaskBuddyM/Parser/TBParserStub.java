package com.cs2013t143j.TaskBuddyM.Parser;
import java.util.*;

public class TBParserStub {
	
	
	final String ERROR_NO_COMMAND = "No command entered.";
	CommandParser cmd;
	DateParser date;
	ContentParser content;
	String userInput;

	public TBParserStub() {	
	}
	
	// parse input to a dict
	public Map<String, String> getDictionary (String input)  {
		Map<String, String> dictionary = new HashMap<String,String>();
		userInput = input;
		
		/*if (userInput.trim().equals("")) {
			throw new InvalidInputException(ERROR_NO_COMMAND);
		}*/

		retrieveCommand(dictionary);
		
		if(dictionary.get("command") != "redo" && dictionary.get("command") != "undo" &&
				dictionary.get("command") != "clear") {
			retrieveContent(dictionary);
		}
		return dictionary;
	}
	
	private void retrieveCommand(Map<String, String> dictionary) {
		cmd = new CommandParser(userInput);
		cmd.extractShortcutCommand(dictionary);
		cmd.extractSubCommand(dictionary);
		userInput = cmd.removeWord(dictionary.get("command"));
	} 
	
	private void retrieveContent(Map<String,String> dictionary) {
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
