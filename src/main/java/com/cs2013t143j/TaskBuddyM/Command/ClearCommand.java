package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;


//@@ author A0101794H
public class ClearCommand implements Command {
	
	private final String INFO = "Clear";
	private final String CLEAR_OUT = "Cleared all tasks from memory";
	
	private ArrayList<Task> allTasks = new ArrayList<Task>();

	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		isValid();
		
		allTasks = sAccess.display();
		
		sAccess.clear();
		
		return CLEAR_OUT;
	}
	
	public boolean isValid() {
		return true;
	}

	//@@author A0121327U
	public void undo(StorageAccess sAccess) {
		int i = 0;
		
		for (i=0; i<allTasks.size(); ++i) {
			Task task = allTasks.get(i);
			
			sAccess.add(task);
		}
	}

	public String info() {
		String output = INFO;
		
		return output;
	}

	public void redo(StorageAccess sAccess) {
		sAccess.clear();
	}
}
