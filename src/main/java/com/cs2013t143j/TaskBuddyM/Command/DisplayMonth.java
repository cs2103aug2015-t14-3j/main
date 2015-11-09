package com.cs2013t143j.TaskBuddyM.Command;

//@@ author A0101794H
import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class DisplayMonth  extends DisplayCommand{

			private final String DISPLAY_HEADER_MONTH = "Here are all your tasks for the month:\n";
			private final String INFO = "Display Month";
	
			public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
				ArrayList<Task> allTasks = sAccess.showMonth();
				tasks = allTasks;
				String output = DISPLAY_HEADER_MONTH;
				output = parseTasks(output);

				return output;
			}	

			public String info() {
				String output = INFO;
	
				return output;
			}	
}
