package com.cs2013t143j.TaskBuddyM.Logic;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Storage.Storage;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class StorageAccess {
	
	private Storage storage;
	
	public StorageAccess(Storage _storage) {
		this.storage = _storage;
	}

	public void add(Task newTask) {
		storage.add(newTask);
	}
	
	public void delete(int index) {
		storage.delete(index);
	}
	
	public ArrayList<Task> display() {
		return storage.display();
	}
	
	public void updateDescription(int index, String newdescription){
		storage.updateDescription(index, newdescription);
	}

	public void updateStartDate(int index,LocalDateTime newStartDate) {
		storage.updateStartDate(index, newStartDate);
	}

	public void updateEndDate(int index,LocalDateTime newEndDate) {
		storage.updateEndDate(index, newEndDate);
	}
}