package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.util.Comparator;

import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class TaskSorter implements Comparator<Task> {
	
	public int compare(Task t1, Task t2) {
		LocalDateTime t1EndDateTime = t1.getEndDateTime();
		LocalDateTime t2EndDateTime = t2.getEndDateTime();
		
		if (t1EndDateTime == null && t2EndDateTime == null) {
			String description1 = t1.getDescription();
			String description2 = t2.getDescription();
			
			return description1.compareTo(description2);
		}
		
		if (t1EndDateTime == null) {
			return 1;
		} else if (t2EndDateTime == null) {
			return -1;
		}

		return t1EndDateTime.compareTo(t2EndDateTime);
		
	}

}
