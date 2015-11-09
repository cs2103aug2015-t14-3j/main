package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.util.ArrayList;
import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;

import com.cs2013t143j.TaskBuddyM.Storage.Task;

//@@author A0121327U
public class AddEvent extends AddCommand {
	
	private String endDate;
	private String startDate;
	
	private final String INFO = "Add Event: %s %s %s";
	
	public AddEvent(String _description, String _startDate, String _endDate) {
		description = _description;
		startDate = _startDate;
		endDate = _endDate;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		isValid();
		
		LocalDateTime start = convertDateTime(startDate);
		LocalDateTime end = convertDateTime(endDate);
		
		Task task = new Task(description, start, end);
		addedTask = task;
		
		sAccess.showWarning(task);
		sAccess.add(task);
		
		Command command = new DisplayAll();
		String output = command.execute(lastDisplay, sAccess);
		
		return output;
	}
	
	public boolean isValid() throws CommandAttributeError {
		if (description == null || description == "" || description == " ") {
			throw new CommandAttributeError(ERROR_DESCRIPTION);
		}
		
		if (startDate == null || startDate == "" || startDate == " ") {
			throw new CommandAttributeError(ERROR_START);
		}
		
		return true;
	}
	
	public String info() {
		String output = String.format(INFO, description, startDate, endDate);
		
		return output;
	}
}
