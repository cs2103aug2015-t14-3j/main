package com.cs2013t143j.TaskBuddyM.Logic;
import java.io.IOException;
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
		
		try {
			Handler fileHandler  = new FileHandler("./Logic.log");
			logger.setUseParentHandlers(false);
			logger.addHandler(fileHandler);
			
			fileHandler.setLevel(Level.ALL);
			logger.setLevel(Level.ALL);
			
		} catch(IOException exception) {
	        logger.log(Level.SEVERE, "Error occur in FileHandler.", exception);
		}
		
		logger.info("Run with all components");
	}
	
	//Constructor to use storageStub for jUnit testing
	public Logic(boolean a) {
		storage = new StorageStub();
		storageAccess = new StorageAccess(storage);
		output = new String();
		
		lastDisplay = new ArrayList<Task>();
		
		try {
			Handler fileHandler  = new FileHandler("./javacodegeeks.log");
			logger.setUseParentHandlers(false);
			logger.addHandler(fileHandler);
			
			fileHandler.setLevel(Level.ALL);
			logger.setLevel(Level.ALL);
			
		} catch(IOException exception) {
	        logger.log(Level.SEVERE, "Error occur in FileHandler.", exception);
		}
		
		logger.info("Run with stubs");
	}
	
	public String executeCommand(String command) {
		logger.log(Level.INFO, "Command entered", command);
		Command commandToExecute;
		
		try{
			commandToExecute = commandCreator.createCommand(command);
		} catch (CommandAttributeError e) {
			logger.log(Level.SEVERE, "Exception Encountered in createCommand", e);
			return e.toString();
		}
			
		try {
			logger.log(Level.INFO, "Command to Execute", commandToExecute.info());
			output = commandToExecute.execute(lastDisplay, storageAccess);
		} catch (CommandAttributeError e) {
			logger.log(Level.SEVERE, "Exception Encountered in execute", e);
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
			logger.log(Level.SEVERE, "Exception Encountered in execute", e);
			return e.toString();
		}
		lastDisplay = DisplayCommand.getLastDisplay();
		
		return output;
	}
}
