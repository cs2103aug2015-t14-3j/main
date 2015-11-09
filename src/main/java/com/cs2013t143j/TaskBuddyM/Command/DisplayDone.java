package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

//@@ author A0101794H
public class DisplayDone extends DisplayCommand {
	
	private final String DISPLAY_HEADER_DONE = "Here are all your done tasks:\n";
	
	private final String INFO = "Display Done";
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		ArrayList<Task> allTasks = sAccess.displayDone();
		
		tasks = allTasks;
		
		String output = DISPLAY_HEADER_DONE;
		
		output = parseTasks(output);

		return output;
	}
	
	public String info() {
		String output = INFO;
		
		return output;
	}
}
