package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class DisplayDue extends DisplayCommand {
	
	private String date;
	private String output = new String();
	
	private final String DISPLAY_HEADER_DUE = "Here are your tasks due on %s:\n\nDescription                 Start Date             End Date            Done\n";
	
	public DisplayDue(String _date) {
		date = _date;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		ArrayList<Task> allTasks = sAccess.display();
		
		tasks = extractDue(allTasks, date);
		
		output = parseTasks(output);

		return output;
	}
	
	private ArrayList<Task> extractDue(ArrayList<Task> allTasks, String _date) {
		ArrayList<Task> result = new ArrayList<Task>();
		
		String date = convertDate(_date);
		output = String.format(DISPLAY_HEADER_DUE, _date);
		
		int i;
		String endDate;
		
		for (i=0; i<allTasks.size(); ++i) {
			
			Task task = allTasks.get(i);
			
			endDate = task.getEndDateTimeInString();
			
			if (endDate.contains(date)) {
				result.add(task);
			} 
		}
		
		return result;
	}
}
