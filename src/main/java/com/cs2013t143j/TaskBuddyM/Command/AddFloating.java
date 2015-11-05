package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

//@@Chow Hong Ern Daniel A0121327U
public class AddFloating extends AddCommand {
	
	private final String INFO = "Add Floating: %s";
	
	public AddFloating(String _description) {
		description = _description;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		if (description == null || description == "" || description == " ") {
			throw new CommandAttributeError(ERROR_DESCRIPTION);
		}

		Task task = new Task(description);
		addedTask = task;
		
		sAccess.add(task);
		
		Command command = new DisplayAll();
		String output = command.execute(lastDisplay, sAccess);
		
		return output;
	}
	
	public String info() {
		String output = String.format(INFO, description);
		
		return output;	
	}
}