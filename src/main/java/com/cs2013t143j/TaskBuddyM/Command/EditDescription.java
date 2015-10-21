package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class EditDescription extends EditCommand {
	
	private String newValue;
	
	public EditDescription(String _index, String _newValue) {
		newValue = _newValue;
		index = Integer.parseInt(_index);
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		if (index > lastDisplay.size()){
			return INVALID_INDEX;
		}
		
		Task taskToEdit = lastDisplay.get(index - 1);
		
		ArrayList<Task> allTasks = sAccess.display();
		
		int storageIndex = allTasks.indexOf(taskToEdit);
		
		sAccess.updateDescription(storageIndex, newValue);
		
		String output = String.format(EDIT_OUTPUT, index, "description", newValue);
		return output;
	}
}
