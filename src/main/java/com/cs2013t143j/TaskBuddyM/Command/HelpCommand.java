package com.cs2013t143j.TaskBuddyM.Command;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class HelpCommand implements Command {
	
	private String command;
	
	private String addHelp = "addHelper.txt";
	private String dispHelp = "dispHelper.txt";
	private String delHelp = "delHelper.txt";
	private String searchHelp = "searchHelper.txt";
	private String editHelp = "editHelper.txt";
	private String doneHelp = "doneHelper.txt";
	private String undoHelp = "undoHelper.txt";
	
	private final String INFO = "Help";
	
	private File fileToRead;
	
	private final String HELP_DEFAULT = "You can get help for the following commands: add, display, delete, search, undo, done, edit";
	private final String ERROR_OPEN = "Cannot open help file";
	private final String HELP_OUT = "Here's the help file for Command: %s\n";
	
	public HelpCommand(String _command) {
		command = _command;
	}

	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		String output = new String();
		
		switch(command) {
		case "add":
			output = String.format(HELP_OUT, "ADD");
			fileToRead = new File(addHelp);
			break;
		case "display":
			output = String.format(HELP_OUT, "DISPLAY");
			fileToRead = new File(dispHelp);
			break;
		case "delete":
			output = String.format(HELP_OUT, "DELETE");
			fileToRead = new File(delHelp);
			break;
		case "search":
			output = String.format(HELP_OUT, "SEARCH");
			fileToRead = new File(searchHelp);
			break;
		case "undo":
			output = String.format(HELP_OUT, "UNDO");
			fileToRead = new File(undoHelp);
			break;
		case "done":
			output = String.format(HELP_OUT, "DONE");
			fileToRead = new File(doneHelp);
			break;
		case "edit":
			output = String.format(HELP_OUT, "EDIT");
			fileToRead = new File(editHelp);
			break;
		default:
			return HELP_DEFAULT;
		}
		
		try {
			Desktop.getDesktop().open(fileToRead);
		} catch (IOException e) {
			throw new CommandAttributeError(ERROR_OPEN);
		}
		
		Command command = new DisplayLast();
		output += command.execute(lastDisplay, sAccess);
		
		return output;		
	}
	
	public void undo(StorageAccess sAccess) {
		return;
	}

	public String info() {
		String output = INFO;
		
		return output;
	}
}