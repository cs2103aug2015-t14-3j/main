package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class EditDescription extends EditCommand {
	
	private String newValue;
	
	private final String ERROR_DESCRIPTION = "Invalid Description";
	
	public EditDescription(String _index, String _newValue) {
		newValue = _newValue;
		index = _index;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		int editIndex = 0;
		
		try {
			editIndex = Integer.parseInt(index);
		} catch (NumberFormatException e) {
			throw new CommandAttributeError(ERROR_INT);
		}
		
		if (editIndex > lastDisplay.size()){
			throw new CommandAttributeError(ERROR_RANGE);
		} else if (editIndex <= 0) {
			throw new CommandAttributeError(ERROR_NEGATIVE);
		}
		
		if (newValue == null || newValue == "" || newValue == " ") {
			throw new CommandAttributeError(ERROR_DESCRIPTION);
		}
		
		Task taskToEdit = lastDisplay.get(editIndex - 1);
		
		ArrayList<Task> allTasks = sAccess.display();
		
		int storageIndex = allTasks.indexOf(taskToEdit);
		
		sAccess.updateDescription(storageIndex, newValue);
		
		String output = String.format(EDIT_OUTPUT, editIndex, "description", newValue);

		Command command = new DisplayLast();
		output += command.execute(lastDisplay, sAccess);
		
		return output;
	}
}
