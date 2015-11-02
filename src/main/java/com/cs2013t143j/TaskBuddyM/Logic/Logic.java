package com.cs2013t143j.TaskBuddyM.Logic;
import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Command.Command;
import com.cs2013t143j.TaskBuddyM.Command.CommandAttributeError;
import com.cs2013t143j.TaskBuddyM.Command.DisplayCommand;
import com.cs2013t143j.TaskBuddyM.Storage.Storage;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class Logic {
	private String output;
	private static ArrayList<Task> lastDisplay = new ArrayList<Task>();

	private Storage storage;
	private StorageAccess storageAccess;

	private CommandCreate commandCreator;
		
	public Logic() {
		output = new String();
		
		lastDisplay = new ArrayList<Task>();
		
		storage = new Storage();
		storageAccess = new StorageAccess(storage);
		
		commandCreator = new CommandCreate();
	}
	
	//Constructor to use storageStub for jUnit testing
	public Logic(boolean a) {
		storage = new StorageStub();
		storageAccess = new StorageAccess(storage);
		output = new String();
		
		lastDisplay = new ArrayList<Task>();
	}
	
	public String executeCommand(String command) {
		Command commandToExecute;
		
		try{
			commandToExecute = commandCreator.createCommand(command);
		} catch (CommandAttributeError e) {
			return e.toString();
		}
			
		try {
			output = commandToExecute.execute(lastDisplay, storageAccess);
		} catch (CommandAttributeError e) {
			return e.toString();
		}
		lastDisplay = DisplayCommand.getLastDisplay();

		return output;
	}

	public String test(Command command) {
		return test2(command);
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
}
