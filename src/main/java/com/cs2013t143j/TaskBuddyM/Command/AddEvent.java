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
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		LocalDateTime start = convertDateTime(startDate);
		LocalDateTime end = convertDateTime(endDate);
		
		Task task = new Task(description, start, end);
		
		sAccess.add(task);
		
		return ADD_OUTPUT;
	}
}