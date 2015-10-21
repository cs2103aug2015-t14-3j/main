package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class DisplayIncomplete extends DisplayCommand {
	
	private String output = new String();
		
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		ArrayList<Task> allTasks = sAccess.display();
		
		tasks = extractIncomplete(allTasks);
		
		parseTasks(output);

		return output;
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
}
