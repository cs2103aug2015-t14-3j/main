package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;
import java.util.Collections;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

//@@Chow Hong Ern Daniel A0121327U
public class SortDisplay implements Command {
	
	protected final String DISPLAY_FORMAT = "%d.%s\t%s\t%s\t%s\n";
	private final String SORTED_HEADER = "Here are your tasks sorted by start date\n";
	protected final String FREE_DAY = "Looks like there is nothing on your schedule. Enjoy your day!!!\n\n";
	
	private final String INFO = "Sort Display by start date";

	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		String output = SORTED_HEADER;
		
		if (lastDisplay.size() == 0) {
			output = FREE_DAY;
			return output;
		}
		
		Collections.sort(lastDisplay, new StartSorter());
	
		int index = 1;
		
		int i = 0;
		
		for (i = 0; i < lastDisplay.size(); ++i) {
			Task task = lastDisplay.get(i);
			
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
	
	public void undo(StorageAccess sAccess) {
		return;
	}
	
	public String info() {
		String output = INFO;
		
		return output;
	}
	
	public void redo(StorageAccess sAccess) {
		return;
	}
}
