package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class DisplayWeek extends DisplayCommand{

private final String DISPLAY_HEADER_WEEK = "Here are all your tasks for the week:\n";
	
private final String INFO = "Display Week";
	
	
public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		ArrayList<Task> allTasks = sAccess.showWeek();
		
		tasks = allTasks;
		
		String output = DISPLAY_HEADER_WEEK;
		
		output = parseTasks(output);

		return output;
	}
public String info() {
	String output = INFO;
	
	return output;
}	
	
	
}