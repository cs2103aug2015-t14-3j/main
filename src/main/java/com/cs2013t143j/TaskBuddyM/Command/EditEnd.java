package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class EditEnd extends EditCommand {
	
	private Task editedTask;
	private LocalDateTime oldDateTime;
	
	private final String INFO = "Edit no.%s end date to %s";
	
	public EditEnd(String _index, String _newValue) {
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
		
		LocalDateTime newDate = convertDateTime(newValue);

		Task taskToEdit = lastDisplay.get(editIndex - 1);
		editedTask = taskToEdit;
		oldDateTime = taskToEdit.getEndDateTime();
		
		ArrayList<Task> allTasks = sAccess.display();
		
		int storageIndex = allTasks.indexOf(taskToEdit);
		
		sAccess.updateEndDate(storageIndex, newDate);
		
		String output = String.format(EDIT_OUTPUT, editIndex, "end date", newValue);

		Command command = new DisplayLast();
		output += command.execute(lastDisplay, sAccess);
		
		return output;
	}
	
	public void undo(StorageAccess sAccess) {
		ArrayList<Task> allTasks = sAccess.display();
		
		int storageIndex = allTasks.indexOf(editedTask);
		
		sAccess.updateEndDate(storageIndex, oldDateTime);
	}
	
	public String info() {
		String output = String.format(INFO, index, newValue);
		
		return output;
	}
}
