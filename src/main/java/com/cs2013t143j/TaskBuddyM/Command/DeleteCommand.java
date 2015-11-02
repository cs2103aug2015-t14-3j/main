package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class DeleteCommand implements Command {
	
	private String delIndex;
	private Task deletedTask;
	
	private final String DELETE_OUTPUT = "Deleted task(s) %s\n";
	
	private final String ERROR_INT = "Index provided is not an Integer";
	private final String ERROR_NEGATIVE = "Index provided must be larger than 0";
	private final String ERROR_RANGE = "Index providded is larger than last display";

	public DeleteCommand(String _index) {
		delIndex = _index;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		
		String[] indexList = delIndex.split(" ");
		
		int i = 0;
		int converted;
		
		for (i=0; i<indexList.length; ++i) {
			try{
				converted = Integer.parseInt(indexList[i]);
				indexes.add(converted);
			} catch (NumberFormatException e) {
				throw new CommandAttributeError(ERROR_INT);
			}
		}
		
		for (i=0; i<indexes.size(); ++i) {
			int index = indexes.get(i);
			if (index > lastDisplay.size()) {
				throw new CommandAttributeError(ERROR_RANGE);
			} else if (index <= 0) {
				throw new CommandAttributeError(ERROR_NEGATIVE);
			}

			Task taskToDelete = lastDisplay.remove(index-1);
			deletedTask = taskToDelete;
			ArrayList<Task> allTasks = sAccess.display();

			int storageIndex = allTasks.indexOf(taskToDelete);

			sAccess.delete(storageIndex);
		}
		
		String deleted = new String();
		
		for (i=0; i<indexes.size(); ++i) {
			deleted += String.valueOf(indexes.get(i));
			
			if (i != indexes.size()-1) {
				deleted += ",";
			}
		}

		String output = String.format(DELETE_OUTPUT, deleted);
		
		Command command = new DisplayLast();
		output += command.execute(lastDisplay, sAccess);
		
		return output;
	}
	
	public void undo(StorageAccess sAccess) {
		sAccess.add(deletedTask);
	}
}
