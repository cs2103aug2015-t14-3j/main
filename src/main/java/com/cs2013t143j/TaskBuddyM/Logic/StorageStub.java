package com.cs2013t143j.TaskBuddyM.Logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Storage.Storage;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

//@@author A0121327U
public class StorageStub extends Storage {
	
	private ArrayList<Task> tasks;
	
	public final DateTimeFormatter FORMAT_STORAGE_DATETIME = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	
	public StorageStub() {
		tasks = new ArrayList<Task>();
	}

	public void add(Task newTask) {
		tasks.add(newTask);
	}
	
	public void delete(int index) {
		tasks.remove(index);
	}
	
	public ArrayList<Task> display() {
		return tasks;
	}
	
	public void updateDescription(int index, String newdescription){
		Task task = tasks.get(index);
		task.setDescription(newdescription);
	}

	public void updateStartDate(int index,LocalDateTime newStartDate) {
		Task task = tasks.get(index);
		task.setStartDateTime(newStartDate);
	}

	public void updateEndDate(int index,LocalDateTime newEndDate) {
		Task task = tasks.get(index);
		task.setEndDateTime(newEndDate);
	}

	public void writeToFile() {
		return;
	}
}
