package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

//@@author A0121327U
public class DisplayFloating extends DisplayCommand {
	
	private String output = new String();
	
	private final String INFO = "Display Floating";
	
	private final String DISPLAY_HEADER_FLOAT = "Here are all your floating tasks:\n";
		
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		ArrayList<Task> allTasks = sAccess.display();
		
		tasks = extractFloating(allTasks);
		
		output = parseTasks(output);

		return output;
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
			
			if (startDate == "" && endDate == "" && task.isDone() == false) {
				result.add(task);
			}
		}
		return result;
	}
	
	public String info() {
		String output = INFO;
		
		return output;
	}
}
