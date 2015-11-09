package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

//@@ author A0101794H
public class UndoCommand implements Command {
	
	private Command commandToUndo;
	
	private final String EMPTY_STACK = "No more commands to undo. ";
	private final String UNDO = "Undid the last undoable command. ";
	
	private final String INFO = "Undo: ";
	
	public UndoCommand(Command command) {
		commandToUndo = command;
	}

	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		String output;
		Command command = new DisplayAll();
		
		if (commandToUndo == null) {
			output = EMPTY_STACK;
			output += command.execute(lastDisplay, sAccess);
			
			return output;
		}
		
		commandToUndo.undo(sAccess);
		output = UNDO;
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
		if (commandToUndo == null) {
			return INFO + "NULL";
		}
		String output = INFO + commandToUndo.info();
		
		return output;
	}
	
	public void redo(StorageAccess sAccess) {
		return;
	}
}
