package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class AddDeadline extends AddCommand {
	
	private String endDate;

	
	public AddDeadline(String _description, String _endDate) {
		description = _description;
		endDate = _endDate;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		if (description == null || description == "" || description == " ") {
			throw new CommandAttributeError(ERROR_DESCRIPTION);
		}
		
		if (endDate == null || endDate == "" || endDate == " ") {
			throw new CommandAttributeError(ERROR_END);
		}
		
		LocalDateTime end = convertDateTime(endDate);
		
		Task task = new Task(description, end);
		addedTask = task;
		
		sAccess.add(task);
		
		Command command = new DisplayAll();
		String output = command.execute(lastDisplay, sAccess);
		
		return output;
	}


}