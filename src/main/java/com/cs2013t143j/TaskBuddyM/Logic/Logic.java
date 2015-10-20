package com.cs2013t143j.TaskBuddyM.Logic;
import java.util.ArrayList;
import java.util.Map;

import com.cs2013t143j.TaskBuddyM.Parser.TBParserStub;
import com.cs2013t143j.TaskBuddyM.Storage.Storage;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class Logic {
	private String output;
	private static ArrayList<Task> lastDisplay;

	private Storage storage;
	private TBParserStub parser;
	private StorageAccess storageAccess;
	
	private Helper helper;
	private Adder adder;
	private Editor editor;
	private Displayer displayer;
	private Deleter deleter;
	private Searcher searcher;
	
	private final String INVALID_COMMAND = "Invalid Command\n";
	private final String ERROR = "Error: %s\n";
		
	public Logic() {
		output = new String();
		
		lastDisplay = new ArrayList<Task>();
		
		storage = new Storage();
		parser = new TBParserStub();
		storageAccess = new StorageAccess(storage);
		
		helper = new Helper();
		adder = new Adder(storageAccess);
		editor = new Editor(storageAccess);
		displayer = new Displayer(storageAccess);
		deleter = new Deleter(storageAccess);
		searcher = new Searcher(storageAccess);		
	}
	
	//Constructor to use storageStub for jUnit testing
	public Logic(boolean a) {
		storage = new StorageStub();
		storageAccess = new StorageAccess(storage);
		output = new String();
		
		lastDisplay = new ArrayList<Task>();
		
		parser = new TBParserStub();
		
		helper = new Helper();
		adder = new Adder(storageAccess);
		editor = new Editor(storageAccess);
		displayer = new Displayer(storageAccess);
		deleter = new Deleter(storageAccess);
		searcher = new Searcher(storageAccess);	
	}
	
	public String executeCommand(String command) {
		Map<String, String> parsedCommand = parser.getDictionary(command);
		String commandType;
		
		commandType = parsedCommand.get("command");

		//Edit this out; Used to check if contents of dictionary are correct
		System.out.println(parsedCommand.toString());
		
		if (commandType == null) {
			return INVALID_COMMAND;
		}
		
		switch (commandType) {
		case "add":
			try {
				output = adder.add(parsedCommand);
			} catch (ParserContentError e) {
				output = parseError(e);
				return output;
			}
			return output;
		case "display":
			output = displayer.display(parsedCommand);
			lastDisplay = displayer.getLastDisplay();
			return output;
		case "delete":
			try {
				output = deleter.delete(parsedCommand, lastDisplay);
			} catch (ParserContentError e) {
				output = parseError(e);
				return output;
			}
			return output;			
		case "edit":
			try {
				output = editor.edit(parsedCommand,  lastDisplay);
			} catch (ParserContentError e) {
				output = parseError(e);
				return output;
			}
			return output;
		case "search":
			output = searcher.search(parsedCommand);
			return output;
		case "undo":
			output = undo(parsedCommand);
			return output;
		case "help":
			try {
				output = helper.help(parsedCommand);
			} catch (ParserContentError e) {
				output = parseError(e);
				return output;
			}
			return output;
		default:
			return INVALID_COMMAND;
		}
	}
	
	private String undo(Map<String, String> parsedCommand) {
		return "command: undo\n";
	}
	
	private String parseError(Exception e) {
		return String.format(ERROR, e.toString());
	}

}
