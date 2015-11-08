package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class DisplayOverDue  extends DisplayCommand{

private final String DISPLAY_HEADER_OVERDUE = "Here are all your overdue tasks:\n";
	
private final String INFO = "Display Overdue";
	
	
public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		ArrayList<Task> allTasks = sAccess.showOverDue();
		
		tasks = allTasks;
		
		String output = DISPLAY_HEADER_OVERDUE;
		
		output = parseTasks(output);

		return output;
	}
public String info() {
	String output = INFO;
	
	return output;
}	
	
	
}