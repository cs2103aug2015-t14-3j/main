package com.cs2013t143j.TaskBuddyM.Command;

import java.awt.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;


public class DisplayRange extends DisplayCommand {
	
	private String startDate;
	private String endDate;
	private String output = new String();
	
	private final String DATE_FORMAT = "HH dd/MM/yyyy";
	private final String DISPLAY_HEADER_AFTER = "Here are your tasks due after %s:\n";
	
	private final String ERROR_FORMAT = "Invalid date format(Should be dd/mm/yyyy)";
	
	public DisplayRange(String _startDate, String _endDate) {
		startDate = _startDate;
		endDate = _endDate;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		if (startDate == null || startDate == "" || startDate == " ") {
			throw new CommandAttributeError(ERROR_DATE);
		} else if (endDate == null || endDate == "" || endDate == " ") {
			throw new CommandAttributeError(ERROR_DATE);
		}
		
		ArrayList<Task> allTasks = sAccess.display();
		
		tasks = extractAfter(allTasks, startDate);
		
		Collections.sort(tasks, new TaskSorter());
		tasks = removeAfter(tasks, endDate);
		
		output = parseTasks(output);

		return output;
	}
	
	private ArrayList<Task> extractAfter(ArrayList<Task> allTasks, String _date) throws CommandAttributeError {
		ArrayList<Task> result = new ArrayList<Task>();
		
		LocalDateTime date;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		
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
	
	private ArrayList<Task> removeAfter(ArrayList<Task> allTasks, String _date) throws CommandAttributeError {
		
		LocalDateTime date;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		
		ArrayList<Task> result = new ArrayList<Task>();
		
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
			
			if (endDate == null || endDate.isAfter(date)) {
				break;
			} else {
				result.add(task);
			}
		}
		
		return result;
	}
	
}
