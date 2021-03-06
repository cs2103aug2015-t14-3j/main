package com.cs2013t143j.TaskBuddyM.Logic;

import java.util.Map;

import java.util.Stack;

import com.cs2013t143j.TaskBuddyM.Command.AddDeadline;
import com.cs2013t143j.TaskBuddyM.Command.AddEvent;
import com.cs2013t143j.TaskBuddyM.Command.AddFloating;
import com.cs2013t143j.TaskBuddyM.Command.Command;
import com.cs2013t143j.TaskBuddyM.Command.CommandAttributeError;
import com.cs2013t143j.TaskBuddyM.Command.DeleteCommand;
import com.cs2013t143j.TaskBuddyM.Command.DisplayAfter;
import com.cs2013t143j.TaskBuddyM.Command.DisplayAll;
import com.cs2013t143j.TaskBuddyM.Command.DisplayDone;
import com.cs2013t143j.TaskBuddyM.Command.DisplayWeek;
import com.cs2013t143j.TaskBuddyM.Command.DisplayMonth;
import com.cs2013t143j.TaskBuddyM.Command.DisplayOverDue;
import com.cs2013t143j.TaskBuddyM.Command.DisplayDue;
import com.cs2013t143j.TaskBuddyM.Command.DisplayFloating;
import com.cs2013t143j.TaskBuddyM.Command.DisplayFrom;
import com.cs2013t143j.TaskBuddyM.Command.DisplayOn;
import com.cs2013t143j.TaskBuddyM.Command.DisplayRange;
import com.cs2013t143j.TaskBuddyM.Command.DoneCommand;
import com.cs2013t143j.TaskBuddyM.Command.EditDescription;
import com.cs2013t143j.TaskBuddyM.Command.EditEnd;
import com.cs2013t143j.TaskBuddyM.Command.EditStart;
import com.cs2013t143j.TaskBuddyM.Command.HelpCommand;
import com.cs2013t143j.TaskBuddyM.Command.SearchCommand;
import com.cs2013t143j.TaskBuddyM.Command.UndoCommand;
import com.cs2013t143j.TaskBuddyM.Command.RedoCommand;
import com.cs2013t143j.TaskBuddyM.Parser.TBParserStub;
import com.cs2013t143j.TaskBuddyM.Command.ClearCommand;

//@@author A0121327U
public class CommandCreate {
	
	private final String COMMAND_TYPE = "command";
	
	private final String ADD_DESCRIPTION = "description";
	private final String ADD_START = "startDate";
	private final String ADD_END = "endDate";
	
	private final String DELETE_INDEX = "index";
	
	private final String DISPLAY_SUB = "subCommand";
	private final String DISPLAY_DATE = "date";
	private final String DISPLAY_START = "startDate";
	private final String DISPLAY_END = "endDate";
	
	private final String EDIT_INDEX = "index";
	private final String EDIT_FIELD = "field";
	private final String EDIT_VALUE = "newValue";
	
	private final String HELP_COMMAND = "helpKey";
	
	private final String SEARCH_KEY = "searchKey";
	
	private final String DONE_INDEX = "index";
	
	private final String INVALID_DISPLAY = "Invalid display subcommand";
	private final String INVALID_EDIT = "Invalid field to edit";
	private final String INVALID_COMMAND = "Invalid Command specified";
	
	private final String ERROR_PARSER = "Parser Error: ";
	
	private TBParserStub parser;
	
	private Stack<Command> undoStack;
	private Stack<Command> redoStack;
	
	public CommandCreate() {
		parser = new TBParserStub();
		undoStack = new Stack<Command>();
		redoStack = new Stack<Command>();
	}
	
	public Command createCommand(String _command) throws CommandAttributeError {
		
		assert _command != null;
		assert _command.trim().length() != 0;
		
		Map<String,String> dictionary;
		
		try {
			dictionary = parser.getDictionary(_command);
		} catch (Exception e) {
			throw new CommandAttributeError(ERROR_PARSER + e.toString());
		}
		
		System.out.println(dictionary.toString());
		
		Command command;
		
		String commandType = dictionary.get(COMMAND_TYPE);
		
		switch (commandType) {
		case "add":
			command = createAdd(dictionary);
			try {
				command.isValid();
			} catch (CommandAttributeError e) {
				break;
			}
			undoStack.push(command);
			break;
		case "display":
			command = createDisplay(dictionary);
			break;
		case "delete":
			command = createDelete(dictionary);
			try {
				command.isValid();
			} catch (CommandAttributeError e) {
				break;
			}
			undoStack.push(command);
			break;
		case "edit":
			command = createEdit(dictionary);
			try {
				command.isValid();
			} catch (CommandAttributeError e) {
				break;
			}
			undoStack.push(command);
			break;
		case "search":
			command = createSearch(dictionary);
			break;
		case "help":
			command = createHelp(dictionary);
			break;
		case "done":
			command = createDone(dictionary);
			break;
		case "undo":
			command = createUndo();
			break;
		case "redo":
			command = createRedo();
			break;
		case "clear":
			command = new ClearCommand();
			undoStack.push(command);
			break;
		default:
			throw new CommandAttributeError(INVALID_COMMAND);
		}
		
		return command;
	}
	
	private Command createAdd(Map<String,String> dict) {
		String description = dict.get(ADD_DESCRIPTION);
		String startDate = dict.get(ADD_START);
		String endDate = dict.get(ADD_END);
		
		if (startDate != null) {
			return new AddEvent(description, startDate, endDate);
		} else if (endDate != null) {
			return new AddDeadline(description, endDate);
		} else {
			return new AddFloating(description);
		}
	}
	
	private Command createUndo() {
		if (undoStack.isEmpty()) {
			return new UndoCommand(null);
		}
		
		Command commandToUndo = undoStack.pop();
		
		redoStack.push(commandToUndo);
		
		return new UndoCommand(commandToUndo);
	}
	
	private Command createRedo() {
		if (redoStack.isEmpty()) {
			return new RedoCommand(null);
		}
		
		Command commandToRedo = redoStack.pop();
		
		undoStack.push(commandToRedo);
		
		return new RedoCommand(commandToRedo);
	}
	
	private Command createDelete(Map<String,String> dict) {
		String index = dict.get(DELETE_INDEX);
		
		return new DeleteCommand(index);
	}
	
	private Command createDisplay(Map<String,String> dict) throws CommandAttributeError {
		String sub = dict.get(DISPLAY_SUB);
		String date = dict.get(DISPLAY_DATE);
		
		String[] splitDate = new String[10];
		
		if (date != null && date != ""){
			splitDate = date.split(" ");
		}
		
		if (splitDate.length != 1) {
			//If time is given, only take the date component
			date = splitDate[1];
		}
		
		if (sub == null) {
			//Display All
			return new DisplayAll();
		} else {
			switch (sub) {
			case "on":
				return new DisplayOn(date);
			case "from":
				return new DisplayFrom(date);
			case "after":
				return new DisplayAfter(date);
			case "due":
				return new DisplayDue(date);
			case "overdue":
				return new DisplayOverDue();
			case "month":
				return new DisplayMonth();
			case "week":
				return new DisplayWeek();
			case "floating":
				return new DisplayFloating();
			case "range":
				String start = dict.get(DISPLAY_START);
				String end = dict.get(DISPLAY_END);
				return new DisplayRange(start, end);
			case "done":
				return new DisplayDone();
			}
		}
		throw new CommandAttributeError(INVALID_DISPLAY);
	}
	
	private Command createEdit(Map<String,String> dict) throws CommandAttributeError {
		String field = dict.get(EDIT_FIELD);
		String index = dict.get(EDIT_INDEX);
		String value = dict.get(EDIT_VALUE);
		
		if (field == "description") {
			return new EditDescription(index, value);
		} else if (field == "start date") {
			return new EditStart(index, value);
		} else if (field == "end date") {
			return new EditEnd(index, value);
		}
		
		throw new CommandAttributeError(INVALID_EDIT);
	}
	
	private Command createHelp(Map<String,String> dict) {
		String command = dict.get(HELP_COMMAND);
		
		return new HelpCommand(command);
	}
	
	private Command createSearch(Map<String,String> dict) {
		String term = dict.get(SEARCH_KEY);
		
		return new SearchCommand(term);
	}
	
	private Command createDone(Map<String,String> dict) {
		String index = dict.get(DONE_INDEX);
		
		return new DoneCommand(index);
	}
}
