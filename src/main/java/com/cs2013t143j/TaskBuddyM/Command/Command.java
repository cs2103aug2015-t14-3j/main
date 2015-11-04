package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public interface Command {
	String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError;
	void undo(StorageAccess sAccess);
	String info();
}
