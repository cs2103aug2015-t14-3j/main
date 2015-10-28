package com.cs2013t143j.TaskBuddyM.Logic;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import com.cs2013t143j.TaskBuddyM.Command.TaskSorter;
import com.cs2013t143j.TaskBuddyM.Storage.Task;
public class Displayer {
	
	private StorageAccess storage;
	private ArrayList<Task> tasks;
	String output;
	
	private final String DATE_FORMAT = "HH dd/MM/yyyy";
	
	private final String DISPLAY_SUB = "subCommand";
	private final String DISPLAY_DATE = "date";
	
	private final String DISPLAY_HEADER_DATE = "Here is your schedule for %s:\n";
	private final String DISPLAY_HEADER_ALL = "Here is your entire schedule:\n";
	private final String DISPLAY_HEADER_FROM = "Here are your events on %s:\n";
	private final String DISPLAY_HEADER_AFTER = "Here are your tasks due after %s:\n";
	private final String DISPLAY_HEADER_DUE = "Here are your tasks due on %s:\n";
	private final String DISPLAY_HEADER_FLOAT = "Here are all your floating tasks:\n";
	private final String DISPLAY_FORMAT = "%d.%s\t%s\t%s\t%s\n";
	private final String FREE_DAY = "Looks like there is nothing on your schedule. Enjoy your day!!!\n\n";
	
	private final String INVALID_DISPLAY_SUB = "Invalid display subcommand specified\n";

	public Displayer(StorageAccess storage) {
		this.storage = storage;
		tasks = new ArrayList<Task>();
	}
	
	public String display(Map<String, String> parsedCommand) {
		output = new String();
		String subCommand = parsedCommand.get(DISPLAY_SUB);
		String date = parsedCommand.get(DISPLAY_DATE);
		
		String[] splitDate = new String[10];
		
		if (date != null && date != ""){
			splitDate = date.split(" ");
		}
		
		if (splitDate.length != 1) {
			//If time is given, only take the date component
			date = splitDate[1];
		}

		ArrayList<Task> allTasks = storage.display();
		
		if (subCommand == null) {
			//Display All
			tasks = extractAll(allTasks);
		} else {
			switch (subCommand) {
			case "on":
				tasks = extractOn(allTasks, date);
				break;
			case "from":
				tasks = extractFrom(allTasks, date);
				break;
			case "after":
				tasks = extractAfter(allTasks, date);
				break;
			case "due":
				tasks = extractDue(allTasks, date);
				break;
			case "incomplete":
				tasks = extractIncomplete(allTasks);
				break;
			case "floating":
				tasks = extractFloating(allTasks);
				break;
			default:
				return INVALID_DISPLAY_SUB;
			}
		}		
		
		
		if (tasks.size() == 0) {
			output = FREE_DAY;
			return output;
		}
		
		Collections.sort(tasks, new TaskSorter());
	
		int index = 1;
		
		int i = 0;
		
		for (i = 0; i < tasks.size(); ++i) {
			Task task = tasks.get(i);
			
			String description = task.getDescription();
			String start = task.getStartDateTimeInString();
			String end = task.getEndDateTimeInString();
			
			if (start == "") {
				start = "-";
			}
			
			if (end == "") {
				end = "-";
			}
			
			boolean done = task.isDone();
			String isDone = "No";
			
			if (done == true) {
				isDone = "Yes";
			}else {
				isDone = "No";
			}
			
			output += String.format(DISPLAY_FORMAT, index, description, start, end, isDone);			
			++index;
		}
		
//		System.out.print(output);
		return output;

		
//		return "command: display " + "sub: " + subCommand + " date: " + date + "\n";
	}
	
	public ArrayList<Task> getLastDisplay() {
		return tasks;
	}
	
	private ArrayList<Task> extractOn(ArrayList<Task> allTasks, String _date) {
		ArrayList<Task> result = new ArrayList<Task>();
		
		String date = convertDate(_date);
		output = String.format(DISPLAY_HEADER_DATE, _date);
		
		int i;
		String startDate;
		String endDate;
		
		for (i=0; i<allTasks.size(); ++i) {
			
			Task task = allTasks.get(i);
			
			startDate = task.getStartDateTimeInString();
			endDate = task.getEndDateTimeInString();
			
			if (startDate.contains(date)) {
				result.add(task);
			} else if (endDate.contains(date)) {
				result.add(task);
			} 
		}
		
		return result;
	}
	
	private ArrayList<Task> extractDue(ArrayList<Task> allTasks, String _date) {
		ArrayList<Task> result = new ArrayList<Task>();
		
		String date = convertDate(_date);
		output = String.format(DISPLAY_HEADER_DUE, _date);
		
		int i;
		String endDate;
		
		for (i=0; i<allTasks.size(); ++i) {
			
			Task task = allTasks.get(i);
			
			endDate = task.getEndDateTimeInString();
			
			if (endDate.contains(date)) {
				result.add(task);
			} 
		}
		
		return result;
	}

	
	private ArrayList<Task> extractFrom(ArrayList<Task> allTasks, String _date) {
		ArrayList<Task> result = new ArrayList<Task>();
		
		String date = convertDate(_date);
		output = String.format(DISPLAY_HEADER_FROM, _date);
		
		int i;
		String startDate;
		
		for (i=0; i<allTasks.size(); ++i) {
			
			Task task = allTasks.get(i);
			
			startDate = task.getStartDateTimeInString();
			
			if (startDate.contains(date)) {
				result.add(task);
			} 
		}
		
		return result;
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
	
	private ArrayList<Task> extractIncomplete(ArrayList<Task> allTasks) {
		ArrayList<Task> result = new ArrayList<Task>();
		
		int i = 0;
		
		for (i=0; i<allTasks.size(); ++i) {
			
			Task task = allTasks.get(i);
			
			if (task.isDone() == false) {
				result.add(task);
			}
		}
		
		return result;
	}
	
	private ArrayList<Task> extractFloating(ArrayList<Task> allTasks) {
		ArrayList<Task> result = new ArrayList<Task>();
		output = DISPLAY_HEADER_FLOAT;
		
		int i = 0;
		String startDate;
		String endDate;
		
		for (i=0; i<allTasks.size(); ++i) {
			Task task = allTasks.get(i);
			
			startDate = task.getStartDateTimeInString();
			endDate = task.getEndDateTimeInString();
			
			if (startDate == "" && endDate == "") {
				result.add(task);
			}
		}
		return result;
	}
	
	private String convertDate(String _date) {
		
		String date = _date.split(" ")[-1];
		
		String[] splitDate = date.split("/");
		
		return splitDate[0] + "-" + splitDate[1] + "-" + splitDate[2];
	}
	
	private ArrayList<Task> extractAll(ArrayList<Task> allTasks) {
		output = DISPLAY_HEADER_ALL;
		return allTasks;
	}
}
