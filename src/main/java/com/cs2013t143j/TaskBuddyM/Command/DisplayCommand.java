package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;
import java.util.Collections;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;


public class DisplayCommand implements Command {
	
	protected static ArrayList<Task> tasks = new ArrayList<Task>();
	
	protected final String DISPLAY_FORMAT = "%d.%s\t%s\t%s\t%s\n";
	protected final String FREE_DAY = "Looks like there is nothing on your schedule. Enjoy your day!!!\n\n";

	protected String convertDate(String date) {
		String[] splitDate = date.split("/");
		
		return splitDate[0] + "-" + splitDate[1] + "-" + splitDate[2];
	}
	
	public static ArrayList<Task> getLastDisplay() {
		return tasks;
	}
	
	protected String parseTasks(String output) {
		
		if (tasks.size() == 0) {
			output = FREE_DAY;
			return output;
		}
		
		Collections.sort(tasks, new TaskSorter());
	
		int index = 1;
		
		int i = 0;
		
		for (i = 0; i < tasks.size(); ++i) {
			Task task = tasks.get(i);
			
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
		
//		System.out.print(output);
		return output;
	}

	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		return null;
	}
}
