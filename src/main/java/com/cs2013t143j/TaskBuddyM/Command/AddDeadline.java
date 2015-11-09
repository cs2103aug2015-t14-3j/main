package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

//@@author A0121327U
public class AddDeadline extends AddCommand {
	
	private static final Logger logger = Logger.getLogger(AddDeadline.class.getName());
	
	private String endDate;

	private final String INFO = "Add DeadLine: %s %s";
	
	public AddDeadline(String _description, String _endDate) {
		description = _description;
		endDate = _endDate;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
	
		isValid();
		
		LocalDateTime end = convertDateTime(endDate);
		
		Task task = new Task(description, end);
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
		
		if (endDate == null || endDate == "" || endDate == " ") {
			throw new CommandAttributeError(ERROR_END);
		}
		
		return true;
	}
	
	public String info() {
		String output = String.format(INFO, description, endDate);
		
		return output;
	}
}
