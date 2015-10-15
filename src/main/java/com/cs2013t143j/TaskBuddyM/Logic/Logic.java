package com.cs2013t143j.TaskBuddyM.Logic;
import java.util.ArrayList;
import java.util.Map;

import com.cs2013t143j.TaskBuddyM.Parser.TBParserStub;
import com.cs2013t143j.TaskBuddyM.Storage.History;
import com.cs2013t143j.TaskBuddyM.Storage.Storage;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class Logic {
	private String output;
	private static ArrayList<Task> lastDisplay;

	
	private Storage storage;
	private History history;
	private TBParserStub parser;
	
	private Helper helper;
	private Adder adder;
	private Editor editor;
	private Displayer displayer;
	private Deleter deleter;
	private Searcher searcher;
	
	private final String INVALID_COMMAND = "Invalid Command\n";
	
	private String dataFile = "data.txt";
	
	public Logic() {
		output = new String();
		parser = new TBParserStub();
		
		lastDisplay = new ArrayList<Task>();
		
		storage = new Storage();
		//storage.loadTasks();
		
		history = new History(dataFile);
		helper = new Helper();
		adder = new Adder(storage);
		editor = new Editor(storage);
		displayer = new Displayer(storage);
		deleter = new Deleter(storage);
		searcher = new Searcher(storage);		
	}
	
	public String executeCommand(String command) {
		Map<String, String> parsedCommand = parser.getDictionary(command);
		String commandType;
		
		commandType = parsedCommand.get("command");

		//Edit this out; Used to check if contents of dictionary are correct
		//System.out.println(parsedCommand.toString());
		
		if (commandType == null) {
			return "";
		}
		
		switch (commandType) {
		case "add":
			output = adder.add(parsedCommand);
			return output;
		case "display":
			output = displayer.display(parsedCommand);
			lastDisplay = displayer.getLastDisplay();
			return output;
		case "delete":
			output = deleter.delete(parsedCommand, lastDisplay);
			return output;			
		case "edit":
			output = editor.edit(parsedCommand,  lastDisplay);
			return output;
		case "search":
			output = searcher.search(parsedCommand);
			return output;
		case "undo":
			output = undo(parsedCommand);
			return output;
		case "help":
			output = helper.help(parsedCommand);
			return output;
		default:
			return INVALID_COMMAND;
		}
	}
	
	private String undo(Map<String, String> parsedCommand) {
		return "command: undo\n";
	}

}
