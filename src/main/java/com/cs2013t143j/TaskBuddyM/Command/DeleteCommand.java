package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class DeleteCommand implements Command {
	
	private String delIndex;
	
	private final String DELETE_OUTPUT = "Deleted task no.%d\n\n";
	
	private final String ERROR_INT = "Index provided is not an Integer";
	private final String ERROR_NEGATIVE = "Index provided must be larger than 0";
	private final String ERROR_RANGE = "Index providded is larger than last display";

	public DeleteCommand(String _index) {
		delIndex = _index;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		int index = 0;
		
		try{
			index = Integer.parseInt(delIndex);
		} catch (NumberFormatException e) {
			throw new CommandAttributeError(ERROR_INT);
		}
				
		if (index > lastDisplay.size()) {
			throw new CommandAttributeError(ERROR_RANGE);
		} else if (index <= 0) {
			throw new CommandAttributeError(ERROR_NEGATIVE);
		}
		
		Task taskToDelete = lastDisplay.get(index-1);
		ArrayList<Task> allTasks = sAccess.display();
		
		int storageIndex = allTasks.indexOf(taskToDelete);
		
		sAccess.delete(storageIndex);
		
		String output = String.format(DELETE_OUTPUT, index);
		return output;
	}
}
