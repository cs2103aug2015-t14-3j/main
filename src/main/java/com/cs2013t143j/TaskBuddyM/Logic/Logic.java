package com.cs2013t143j.TaskBuddyM.Logic;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import com.cs2013t143j.TaskBuddyM.Parser.ContentParser;


import com.cs2013t143j.TaskBuddyM.Storage.History;
import java.util.Stack;

import com.cs2013t143j.TaskBuddyM.Command.Command;
import com.cs2013t143j.TaskBuddyM.Command.CommandAttributeError;
import com.cs2013t143j.TaskBuddyM.Command.DisplayCommand;
import com.cs2013t143j.TaskBuddyM.Parser.TBParserStub;
import com.cs2013t143j.TaskBuddyM.Storage.Storage;
import com.cs2013t143j.TaskBuddyM.Storage.Task;
import com.cs2013t143j.TaskBuddyM.Logic.ClearCommand;
import com.cs2013t143j.TaskBuddyM.Logic.DoneCommand;

public class Logic {
	private String output;
	private static ArrayList<Task> lastDisplay = new ArrayList<Task>();
	private static Stack<String>  build = new Stack<String>();
	private static Stack<String>  redobuild  = new Stack<String>();
	private Storage storage;
	private TBParserStub parser;
	private StorageAccess storageAccess;
	private History history;
	private Helper helper;
	private Adder adder;
	private Editor editor;
	private Displayer displayer;
	private Deleter deleter;
	private Searcher searcher;
	private DoneCommand done;
	private UndoCommand undo;
	private RedoCommand redo;
	private ClearCommand clear;
	private boolean commPatt  = true;
	
	private final String INVALID_COMMAND = "Invalid Command\n";
	private final String ERROR = "Error: %s\n";
		
	public Logic() {
		output = new String();
		
		lastDisplay = new ArrayList<Task>();
		
		storage = new Storage();
		parser = new TBParserStub();
		storageAccess = new StorageAccess(storage);
		history = new History(storageAccess);
		helper = new Helper();
		adder = new Adder(storageAccess);
		editor = new Editor(storageAccess,history);
		displayer = new Displayer(storageAccess);
		deleter = new Deleter(storageAccess,history);
		searcher = new Searcher(storageAccess);	
		done = new DoneCommand(storageAccess);
		undo = new UndoCommand(history,storageAccess);
		redo = new RedoCommand(history,storageAccess);
		clear = new ClearCommand(storageAccess);
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
		editor = new Editor(storageAccess,history);
		displayer = new Displayer(storageAccess);
		deleter = new Deleter(storageAccess,history);
		searcher = new Searcher(storageAccess);	
		done = 	new DoneCommand(storageAccess);
		undo = new UndoCommand(history,storageAccess);
		redo = new RedoCommand(history,storageAccess);
		clear  = new ClearCommand(storageAccess);
}
	
public String executeCommand(String command) {
		
		if (commPatt) {
			String commandType;

			Map<String,String> parsedCommand = parser.getDictionary(command);

			commandType = parsedCommand.get("command");

			//Edit this out; Used to check if contents of dictionary are correct
			System.out.println(parsedCommand.toString());

			if (commandType == null) {
				return INVALID_COMMAND;
			}

			switch (commandType) {
			case "add":
				redobuild.push("add");
				try {
					build.push("add");
					output = adder.add(parsedCommand);
					return output;
				} catch (ParserContentError e) {
					output = parseError(e);
					return output;
				}
			case "display":
				output = displayer.display(parsedCommand);
				lastDisplay = displayer.getLastDisplay();
				return output;
			case "delete":
				try {
					build.push("delete");
					output = deleter.delete(parsedCommand, lastDisplay);
				} catch (ParserContentError e) {
					output = parseError(e);
					return output;
				}
				return output;
			case "done":
				output = done.execute(parsedCommand);
				 //catch(ParserContentError e) {
					//output = parseError(e);
				//	return output;
				return output;
			case "edit":
				build.push("edit");
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
			case "clear":
				output = clear.execute();
				return output;
			case "undo":
				String cmd = build.pop();
				if(cmd == "add")
				{
				build.push(cmd);	
				history.pushUndoAdd();	
				output= undo.executeUndoAdd(lastDisplay, storageAccess);
				}
				else if(cmd == "delete")
				{
					output= undo.executeUndoDelete(lastDisplay, storageAccess);
				}
				else if(cmd == "edit")
				{
					output = undo.executeUndoEdit(lastDisplay,storageAccess);
				}
					return output; 
			case "redo":
				String new_cmd = redobuild.pop();
				if(new_cmd == "add")
				{
					redobuild.push(new_cmd);
					history.pushRedoAdd();
					output = redo.executeRedoAdd(lastDisplay,storageAccess);
				}
			
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

		return "";
		
//			Command commandToExecute = parser.getCommand(command);
			
//		try {
//			output = commandToExecute.execute(lastDisplay, storageAccess);
//	} catch (CommandAttributeError e) {
//		return e.toString();
//	}
//			lastDisplay = DisplayCommand.getLastDisplay();

//			return output;
	}

public String test(Map<String,String> parsedCommand) {
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
	
public String test2(Command command) {
		
		try{
		output = command.execute(lastDisplay, storageAccess);
		} catch (CommandAttributeError e) {
			return e.toString();
		}
		lastDisplay = DisplayCommand.getLastDisplay();
		
		return output;
	}
	
private String parseError(Exception e) {
		return String.format(ERROR, e.toString());
	}

}
