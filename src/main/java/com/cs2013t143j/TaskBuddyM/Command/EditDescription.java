package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

//@@author A0121327U
public class EditDescription extends EditCommand {
	
	private String oldValue;
	private Task editedTask;
	
	private final String INFO = "Edit no.%s desctiption to %s";
	
	private final String ERROR_DESCRIPTION = "Invalid Description";
	
	public EditDescription(String _index, String _newValue) {
		newValue = _newValue;
		index = _index;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		int editIndex = 0;
		
		isValid();
		
		editIndex = Integer.parseInt(index);
		
		if (editIndex > lastDisplay.size()){
			throw new CommandAttributeError(ERROR_RANGE);
		} else if (editIndex <= 0) {
			throw new CommandAttributeError(ERROR_NEGATIVE);
		}
		
		Task taskToEdit = lastDisplay.get(editIndex - 1);
		editedTask = taskToEdit;
		oldValue = taskToEdit.getDescription();
		
		ArrayList<Task> allTasks = sAccess.display();
		
		int storageIndex = allTasks.indexOf(taskToEdit);
		
		sAccess.updateDescription(storageIndex, newValue);
		
		String output = String.format(EDIT_OUTPUT, editIndex, "description", newValue);

		Command command = new DisplayLast();
		output += command.execute(lastDisplay, sAccess);
		
		return output;
	}
	
	public boolean isValid() throws CommandAttributeError {
		try {
			Integer.parseInt(index);
		} catch (NumberFormatException e) {
			throw new CommandAttributeError(ERROR_INT);
		}

		if (newValue == null || newValue == "" || newValue == " ") {
			throw new CommandAttributeError(ERROR_DESCRIPTION);
		}
		
		return true;
	}
	
	public void undo(StorageAccess sAccess) {
		ArrayList<Task> allTasks = sAccess.display();
			
		int storageIndex = allTasks.indexOf(editedTask);
		
		sAccess.updateDescription(storageIndex, oldValue);
	}
	
	public void redo(StorageAccess sAccess) {
		ArrayList<Task> allTasks = sAccess.display();
		
		int storageIndex = allTasks.indexOf(editedTask);
		
		sAccess.updateDescription(storageIndex, newValue);
	}
	
	public String info() {
		String output = String.format(INFO, index, newValue);
		
		return output;
	}
}
