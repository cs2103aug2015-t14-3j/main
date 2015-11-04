package com.cs2013t143j.TaskBuddyM.Logic;

import java.time.LocalDateTime;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Storage.Storage;
import com.cs2013t143j.TaskBuddyM.Storage.Task;
import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;

public class StorageAccess {
	
	private Storage storage;
	
	public StorageAccess(Storage _storage) {
		this.storage = _storage;
	}

	public boolean add(Task newTask) {
		storage.add(newTask);
		return true;
	}
	
	public void delete(int index) {
		storage.delete(index);
	}
	
	public void done(int index) {
		storage.done(index);
	}
	
	public void clear() {
		storage.clearAll();
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
	
	public void writeToFile() {
		storage.writeToFile();
	}

	public ArrayList<Task> displayDone() {
		// TODO Auto-generated method stub
		return storage.displayDoneTasks();
	}
}
