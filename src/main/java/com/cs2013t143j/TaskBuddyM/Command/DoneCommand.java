package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class DoneCommand implements Command {

	private String doneIndex;
	
	private final String INFO = "Done no.%s";
	
	private final String DONE_OUTPUT = "Changed the done status of task %d\n\n";
	private final String INVALID_INDEX = "Invalid Index specified\n\n";

	public DoneCommand(String _index) {
		doneIndex = _index;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		int index = Integer.parseInt(doneIndex);
				
		if (index > lastDisplay.size() || index <= 0){
			return INVALID_INDEX;
		}
		
		Task taskToChange = lastDisplay.get(index-1);
		ArrayList<Task> allTasks = sAccess.display();
		
		int storageIndex = allTasks.indexOf(taskToChange);
		
		sAccess.done(storageIndex);
		
		String output = String.format(DONE_OUTPUT, index);
		
		Command command = new DisplayLast();
		
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
		String output = String.format(INFO, doneIndex);
		
		return output;
	}
	
	public void redo(StorageAccess sAccess) {
		return;
	}
}
