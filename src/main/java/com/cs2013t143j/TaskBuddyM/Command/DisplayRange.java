package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	
	public DisplayRange(String _startDate, String _endDate) {
		startDate = _startDate;
		endDate = _endDate;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		ArrayList<Task> allTasks = sAccess.display();
		
		tasks = extractAfter(allTasks, startDate);
		
		Collections.sort(tasks, new TaskSorter());
		tasks = removeAfter(tasks, endDate);
		
		output = parseTasks(output);

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
			
			if (endDate != null && endDate.isAfter(date) && task.isDone() == false) {
				result.add(task);
			}
		}
		
		return result;
	}
	
	private ArrayList<Task> removeAfter(ArrayList<Task> allTasks, String _date) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		LocalDateTime date = LocalDateTime.parse("00 " + _date, formatter);
		output = String.format(DISPLAY_HEADER_AFTER, _date);
		
		int i;
		LocalDateTime endDate;
		
		for (i=0; i<allTasks.size(); ++i) {
			
			Task task = allTasks.get(i);
			
			endDate = task.getEndDateTime();
			
			if (endDate == null || endDate.isAfter(date)) {
				break;
			}
		}
		
		return (ArrayList<Task>) allTasks.subList(0, i);
	}
	
}
