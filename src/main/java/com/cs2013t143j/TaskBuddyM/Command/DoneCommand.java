package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class DoneCommand implements Command {

	private String doneIndex;
	
	private final String DONE_OUTPUT = "Changed the done status of task %d\n\n";
	private final String INVALID_INDEX = "Invalid Index specified\n\n";

	public DoneCommand(String _index) {
		doneIndex = _index;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		int index = Integer.parseInt(doneIndex);
				
		if (index > lastDisplay.size() || index <= 0){
			return INVALID_INDEX;
		}
		
		Task taskToChange = lastDisplay.get(index-1);

		boolean status = taskToChange.isDone();
		
		taskToChange.setIsDone(!status);
		sAccess.writeToFile();
		
		String output = String.format(DONE_OUTPUT, index);
		return output;
	}
}
