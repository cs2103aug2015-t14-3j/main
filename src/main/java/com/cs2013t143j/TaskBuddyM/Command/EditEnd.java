package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class EditEnd extends EditCommand {
	
	private String newValue;
	
	public EditEnd(String _index, String _newValue) {
		newValue = _newValue;
		index = Integer.parseInt(_index);
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		if (index > lastDisplay.size()){
			return INVALID_INDEX;
		}		
		
		LocalDateTime newDate = convertDateTime(newValue);

		Task taskToEdit = lastDisplay.get(index - 1);
		
		ArrayList<Task> allTasks = sAccess.display();
		
		int storageIndex = allTasks.indexOf(taskToEdit);
		
		sAccess.updateEndDate(storageIndex, newDate);
		
		String output = String.format(EDIT_OUTPUT, index, "end date", newValue);
		return output;
	}
}
