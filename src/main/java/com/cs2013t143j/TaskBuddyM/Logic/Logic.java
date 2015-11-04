package com.cs2013t143j.TaskBuddyM.Logic;
import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Command.Command;
import com.cs2013t143j.TaskBuddyM.Command.CommandAttributeError;
import com.cs2013t143j.TaskBuddyM.Command.DisplayCommand;
import com.cs2013t143j.TaskBuddyM.Storage.Storage;
import com.cs2013t143j.TaskBuddyM.Storage.Task;
import java.util.logging.*;

public class Logic {
	private String output;
	private static ArrayList<Task> lastDisplay = new ArrayList<Task>();
	private Storage storage;
	private StorageAccess storageAccess;

	private CommandCreate commandCreator;
	
	private static final Logger logger = Logger.getLogger(Logic.class.getName());
	
	public Logic() {
		output = new String();
		
		lastDisplay = new ArrayList<Task>();
		
		storage = new Storage();
		storageAccess = new StorageAccess(storage);
		
		commandCreator = new CommandCreate();
		
	//	logger.setUseParentHandlers(false);
		logger.setLevel(Level.ALL);
				
		logger.info("Run with all components");
	}

	//Constructor to use storageStub for jUnit testing
	public Logic(boolean a) {
		storage = new StorageStub();
		storageAccess = new StorageAccess(storage);
		output = new String();
		
		lastDisplay = new ArrayList<Task>();
		
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.ALL);
		
		logger.info("Run with stubs");
	}
	
	public String executeCommand(String command) {
		logger.info("Input: " + command);
		Command commandToExecute;
		
		try{
			commandToExecute = commandCreator.createCommand(command);
		} catch (CommandAttributeError e) {
			logger.severe("Exception(createCommand): " + e.toString());
			return e.toString();
		}
			
		try {
			logger.info("Command: " + commandToExecute.info());
			//System.out.print(commandToExecute.info());
			output = commandToExecute.execute(lastDisplay, storageAccess);
		} catch (CommandAttributeError e) {
			logger.severe("Exception(execute): " + e.toString());
			return e.toString();
		}
		
		lastDisplay = DisplayCommand.getLastDisplay();
		
		return output;
	}
			
	public String test2(Command command) {
		
		try{
		output = command.execute(lastDisplay, storageAccess);
		} catch (CommandAttributeError e) {
			logger.log(Level.SEVERE, "Exception Encountered in execute", e);
			return e.toString();
		}
		lastDisplay = DisplayCommand.getLastDisplay();
		
		return output;
	}
}
