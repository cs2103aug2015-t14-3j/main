package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;


import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class DisplayDue extends DisplayCommand {
	
	private String output = new String();
	
	private final String INFO = "Display Due %s";
	
	private final String DISPLAY_HEADER_DUE = "Here are your tasks due on %s:\n";
	
	public DisplayDue(String _date) {
		date = _date;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		if (date == null || date == "" || date == " ") {
			throw new CommandAttributeError(ERROR_DATE);
		}
		
		ArrayList<Task> allTasks = sAccess.display();
		
		tasks = extractDue(allTasks, date);
		
		output = parseTasks(output);

		return output;
	}
	
	private String convertDate(String _date) {
		
		String[] splitDate = _date.split(" ");
		this.date = splitDate[splitDate.length-1];
		
		splitDate = date.split("/");
		
		return splitDate[0] + "-" + splitDate[1] + "-" + splitDate[2];
	}
	
	private ArrayList<Task> extractDue(ArrayList<Task> allTasks, String _date) {
		ArrayList<Task> result = new ArrayList<Task>();
		
		String date = convertDate(_date);
		output = String.format(DISPLAY_HEADER_DUE, this.date);
		
		int i;
		String endDate;
		
		for (i=0; i<allTasks.size(); ++i) {
			
			Task task = allTasks.get(i);
			
			endDate = task.getEndDateTimeInString();
			
			if (endDate.contains(date) && task.isDone() == false) {
				result.add(task);
			} 
		}
		
		return result;
	}
	
	public String info() {
		String output = String.format(INFO, date);
		
		return output;
	}
}
