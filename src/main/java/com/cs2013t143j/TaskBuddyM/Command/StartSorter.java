package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.util.Comparator;

import com.cs2013t143j.TaskBuddyM.Storage.Task;

//@@author A0121327U-unused
class StartSorter implements Comparator<Task> {
	
	public int compare(Task t1, Task t2) {
		LocalDateTime t1StartDateTime = t1.getStartDateTime();
		LocalDateTime t2StartDateTime = t2.getStartDateTime();
		
		if (t1StartDateTime == null) {
			return 1;
		} else if (t2StartDateTime == null) {
			return -1;
		}

		return t1StartDateTime.compareTo(t2StartDateTime);
		
	}

}
