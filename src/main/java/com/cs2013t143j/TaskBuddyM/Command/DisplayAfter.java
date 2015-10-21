package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;


public class DisplayAfter extends DisplayCommand {
	
	private String date;
	private String output = new String();
	
	private final String DATE_FORMAT = "HH dd/MM/yyyy";
	private final String DISPLAY_HEADER_AFTER = "Here are your tasks due after %s:\n\nDescription                 Start Date             End Date            Done\n";
	
	public DisplayAfter(String _date) {
		date = _date;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		ArrayList<Task> allTasks = sAccess.display();
		
		tasks = extractAfter(allTasks, date);
		
		parseTasks(output);

		return output;
	}
	
	private ArrayList<Task> extractAfter(ArrayList<Task> allTasks, String _date) {
		ArrayList<Task> result = new ArrayList<Task>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		LocalDateTime date = LocalDateTime.parse("00 " + _date, formatter);
		output = String.format(DISPLAY_HEADER_AFTER, _date);
		
		int i;
		LocalDateTime endDate;
		
		for (i=0; i<allTasks.size(); ++i) {
			
			Task task = allTasks.get(i);
			
			endDate = task.getEndDateTime();
			
			if (endDate != null && endDate.isAfter(date)) {
				result.add(task);
			}
		}
		
		return result;
	}
}
