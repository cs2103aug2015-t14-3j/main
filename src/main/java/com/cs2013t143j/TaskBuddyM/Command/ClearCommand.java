package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class ClearCommand implements Command {
	
	private final String INFO = "Clear";
	private final String CLEAR_OUT = "Cleared all tasks from memory";

	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		sAccess.clear();
		
		return CLEAR_OUT;
	}

	public void undo(StorageAccess sAccess) {
		return;
	}

	public String info() {
		String output = INFO;
		
		return output;
	}

	public void redo(StorageAccess sAccess) {
		return;
	}

}
