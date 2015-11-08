package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class RedoCommand implements Command {
	
	private Command commandToRedo;
	
	private final String EMPTY_STACK = "No more commands to redo. ";
	private final String REDO = "Redid the last redoable command. ";
	
	private final String INFO = "Redo: ";
	
	public RedoCommand(Command command) {
		commandToRedo = command;
	}

	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		String output;
		Command command = new DisplayAll();
		
		if (commandToRedo == null) {
			output = EMPTY_STACK;
			output += command.execute(lastDisplay, sAccess);
			
			return output;
		}
		
		commandToRedo.redo(sAccess);
		output = REDO;
		output += command.execute(lastDisplay, sAccess);
		
		return output;
	}
	
	public boolean isValid() {
		return true;
	}

	public void undo(StorageAccess sAccess) {
		return;
	}	
	
	public String info() {
		if (commandToRedo == null) {
			return INFO + "NULL";
		}
		String output = INFO + commandToRedo.info();
		
		return output;
	}
	
	public void redo(StorageAccess sAccess) {
		return;
	}
	
}
