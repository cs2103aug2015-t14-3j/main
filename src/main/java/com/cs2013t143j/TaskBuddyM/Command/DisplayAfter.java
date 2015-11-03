package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;


public class DisplayAfter extends DisplayCommand {
	
	private String output = new String();
	
	private final String INFO = "Display After %s";
	
	private final String DATE_FORMAT = "HH dd/MM/yyyy";
	private final String DISPLAY_HEADER_AFTER = "Here are your tasks due after %s:\n";
	
	private final String ERROR_FORMAT = "Invalid date format(Should be dd/mm/yyyy)";
	
	public DisplayAfter(String _date) {
		date = _date;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		if (date == null || date == "" || date == " ") {
			throw new CommandAttributeError(ERROR_DATE);
		}
		
		ArrayList<Task> allTasks = sAccess.display();
		
		tasks = extractAfter(allTasks, date);
		
		output = parseTasks(output);

		return output;
	}
	
	private ArrayList<Task> extractAfter(ArrayList<Task> allTasks, String _date) throws CommandAttributeError {
		ArrayList<Task> result = new ArrayList<Task>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		
		LocalDateTime date;
		
		try {
			date = LocalDateTime.parse("00 " + _date, formatter);
		} catch (DateTimeParseException e) {
			throw new CommandAttributeError(ERROR_FORMAT);
		}
		output = String.format(DISPLAY_HEADER_AFTER, _date);
		
		int i;
		LocalDateTime endDate;
		
		for (i=0; i<allTasks.size(); ++i) {
			
			Task task = allTasks.get(i);
			
			endDate = task.getEndDateTime();
			
			if (endDate != null && endDate.isAfter(date) && task.isDone() == false) {
				result.add(task);
			}
		}
		
		return result;
	}
	
	public String info() {
		String output = String.format(INFO,  date);
		
		return output;
	}
}
