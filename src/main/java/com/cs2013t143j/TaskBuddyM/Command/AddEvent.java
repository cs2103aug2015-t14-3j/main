package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class AddEvent extends AddCommand {
	
	private String endDate;
	private String startDate;
	
	public AddEvent(String _description, String _startDate, String _endDate) {
		description = _description;
		startDate = _startDate;
		endDate = _endDate;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		if (description == null || description == "" || description == " ") {
			throw new CommandAttributeError(ERROR_DESCRIPTION);
		}
		
		if (startDate == null || startDate == "" || startDate == " ") {
			throw new CommandAttributeError(ERROR_START);
		}
		
		if (endDate == null || endDate == "" || endDate == " ") {
			throw new CommandAttributeError(ERROR_END);
		}
		
		LocalDateTime start = convertDateTime(startDate);
		LocalDateTime end = convertDateTime(endDate);
		
		Task task = new Task(description, start, end);
		
		sAccess.add(task);
		
		Command command = new DisplayAll();
		String output = command.execute(lastDisplay, sAccess);
		
		return output;
	}
}