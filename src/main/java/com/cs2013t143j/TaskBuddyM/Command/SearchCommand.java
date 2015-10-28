package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class SearchCommand implements Command {
	
	private String searchKey;
	
	private final String SEARCH_HEADER = "Search returned %d result(s):\n";
	private final String DISPLAY_FORMAT = "%d.%s\t%s\t%s\t%s\n";
	private final String NO_RESUTS = "Search returned no results\n\n";
	
	private ArrayList<Task> result = new ArrayList<Task>();
	
	public SearchCommand(String _searchKey) {
		searchKey = _searchKey;
	}

	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		ArrayList<Task> allTasks = sAccess.display();
		result.clear();
		
		int i;
		
		for (i=0; i<allTasks.size(); ++i) {
			Task task = allTasks.get(i);
			
			if (task.getDescription().contains(searchKey)) {
				result.add(task);
			}
		}
		
		if (result.size() == 0) {
			return NO_RESUTS;
		} 
		
		String output;
		
		output = String.format(SEARCH_HEADER, result.size());
		int index = 1;
		
		for (i = 0; i < result.size(); ++i) {
			Task task = result.get(i);
			
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
		return output;
	}
}
