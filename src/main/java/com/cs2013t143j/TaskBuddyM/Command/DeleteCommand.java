package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class DeleteCommand implements Command {
	
	private String delIndex;
	
	private final String DELETE_OUTPUT = "Deleted task no.%d\n\n";
	private final String INVALID_INDEX = "Invalid Index specified\n\n";

	public DeleteCommand(String _index) {
		delIndex = _index;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		int index = Integer.parseInt(delIndex);
				
		if (index > lastDisplay.size()){
			return INVALID_INDEX;
		}
		
		Task taskToDelete = lastDisplay.get(index-1);
		ArrayList<Task> allTasks = sAccess.display();
		
		int storageIndex = allTasks.indexOf(taskToDelete);
		
		sAccess.delete(storageIndex);
		
		String output = String.format(DELETE_OUTPUT, index);
		return output;
	}
}
