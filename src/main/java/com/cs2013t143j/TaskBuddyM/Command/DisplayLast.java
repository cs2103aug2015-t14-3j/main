package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class DisplayLast extends DisplayCommand {
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		tasks = lastDisplay;
		
		String output = new String();
		
		output = parseTasks(output);

		return output;
	}
	
}
