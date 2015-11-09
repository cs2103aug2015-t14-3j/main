package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

//@@author A0121327U
public class DisplayLast extends DisplayCommand {
	
	private final String INFO = "Display Last";
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		tasks = lastDisplay;
		
		String output = new String();
		
		output = parseTasks(output);
		
		if (output == FREE_DAY) {
			return "";
		}

		return output;
	}
	
	public String info() {
		String output = INFO;
		
		return output;
	}
}
