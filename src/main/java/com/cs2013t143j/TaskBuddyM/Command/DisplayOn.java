package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class DisplayOn extends DisplayCommand {
	
	private String date;
	private String output = new String();
	
	private final String DISPLAY_HEADER_DATE = "Here is your schedule for %s:\n\nDescription                 Start Date             End Date            Done\n";
	
	public DisplayOn(String _date) {
		date = _date;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		ArrayList<Task> allTasks = sAccess.display();
		
		tasks = extractOn(allTasks, date);
		
		parseTasks(output);

		return output;
	}
	
	private ArrayList<Task> extractOn(ArrayList<Task> allTasks, String _date) {
		ArrayList<Task> result = new ArrayList<Task>();
		
		String date = convertDate(_date);
		output = String.format(DISPLAY_HEADER_DATE, _date);
		
		int i;
		String startDate;
		String endDate;
		
		for (i=0; i<allTasks.size(); ++i) {
			
			Task task = allTasks.get(i);
			
			startDate = task.getStartDateTimeInString();
			endDate = task.getEndDateTimeInString();
			
			if (startDate.contains(date)) {
				result.add(task);
			} else if (endDate.contains(date)) {
				result.add(task);
			} 
		}
		
		return result;
	}

}
