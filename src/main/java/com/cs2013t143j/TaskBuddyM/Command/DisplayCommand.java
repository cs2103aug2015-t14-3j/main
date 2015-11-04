package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;


public class DisplayCommand implements Command {
	
	protected static ArrayList<Task> tasks = new ArrayList<Task>();
	
	private final String INFO = "Display Command: Should not be instantiated";
	
	protected String date;
	
	private String dateTimeFormat = "dd MMM yyyy HH:mm";
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
	
	protected final String DISPLAY_FORMAT = "%d.%s\t%s\t%s\n";
	protected final String FREE_DAY = "Looks like there is nothing on your schedule. Enjoy your day!!!";
	
	protected final String ERROR_DATE = "Invalid or no date specified";
	
	public static ArrayList<Task> getLastDisplay() {
		return tasks;
	}
	
	protected String parseTasks(String output) {
		
		if (tasks.size() == 0) {
			output = FREE_DAY;
			return output;
		}
		
		Collections.sort(tasks, new TaskSorter());
	
		int index = 1;
		
		int i = 0;
		
		for (i = 0; i < tasks.size(); ++i) {
			Task task = tasks.get(i);
			
			String start;
			String end;
			
			String description = task.getDescription();
			LocalDateTime _start = task.getStartDateTime();
			LocalDateTime _end = task.getEndDateTime();
			
			if (_start == null) {
				start = "-";
			} else {
				start = _start.format(formatter);
			}
			
			if (_end == null) {
				end = "-";
			} else {
				end = _end.format(formatter);
			}
			
			output += String.format(DISPLAY_FORMAT, index, description, start, end);			
			++index;
		}
		
//		System.out.print(output);
		return output;
	}

	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		return null;
	}
	
	public void undo(StorageAccess sAccess) {
		return;
	}
	
	public String info() {
		String output = INFO;
		
		return output;
	}
}
