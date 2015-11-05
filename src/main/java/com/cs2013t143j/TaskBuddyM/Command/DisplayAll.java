package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

//@@Chow Hong Ern Daniel A0121327U
public class DisplayAll extends DisplayCommand {
	
	private final String DISPLAY_HEADER_ALL = "Here is your entire schedule:\n";
	
	private final String INFO = "Display All";
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		ArrayList<Task> allTasks = sAccess.display();
		
		tasks = extractAll(allTasks);
		
		String output = DISPLAY_HEADER_ALL;
		
		output = parseTasks(output);

		return output;
	}
	
	private ArrayList<Task> extractAll(ArrayList<Task> allTasks) {

		ArrayList<Task> results = new ArrayList<Task>();
		
		int i = 0;
		
		for (i=0; i<allTasks.size(); ++i) {
			Task task = allTasks.get(i);
			
			if (task.isDone() == false) {
				results.add(task);
			}
		}
		return results;
	}
	
	public String info() {
		String output = INFO;
		
		return output;
	}
}
