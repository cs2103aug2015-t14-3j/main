package com.cs2013t143j.TaskBuddyM.Logic;

import com.cs2013t143j.TaskBuddyM.Storage.History;
import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Logic.StackCommand;
import com.cs2013t143j.TaskBuddyM.Storage.Task;
import com.cs2013t143j.TaskBuddyM.Storage.StorageIO;
import com.cs2013t143j.TaskBuddyM.Storage.Storage;


import java.util.ArrayList;

//@@ author A0101794H
public class RedoCommand {
		private History history;
		private StorageAccess storage;
		private Task task = new Task();
		StackCommand cmd;
	
		private ArrayList<Task> TaskList = new ArrayList<Task>();

		public RedoCommand(History history,StorageAccess storage) {
						this.history = history;
						this.storage = storage;
		}

		public String executeRedoAdd(ArrayList<Task> TaskList,StorageAccess storage) {
						String feedback = "yes";
						int index;
						Task task;
						StackCommand cmd;

						cmd = history.popRedoAdd();
						task = cmd.getTask();
						index = cmd.getIndex();
	
						TaskList = storage.display();
						TaskList.add(index,task);
	
						storage.writeToFile();
	
						return feedback;
	}
}