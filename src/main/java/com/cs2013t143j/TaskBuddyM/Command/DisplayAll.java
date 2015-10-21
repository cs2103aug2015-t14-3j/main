package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;



public class DisplayAll extends DisplayCommand {
	
	private final String DISPLAY_HEADER_ALL = "Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n";
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		ArrayList<Task> allTasks = sAccess.display();
		
		tasks = allTasks;
		
		String output = DISPLAY_HEADER_ALL;
		
		parseTasks(output);

		return output;
	}
}
