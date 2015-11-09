package com.cs2013t143j.TaskBuddyM.Logic;

import java.time.LocalDateTime;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Storage.Storage;
import com.cs2013t143j.TaskBuddyM.Storage.Task;
import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;

//@@Chow Hong Ern Daniel A0121327U
public class StorageAccess {
	
	private Storage storage;
	
	public StorageAccess(Storage _storage) {
		this.storage = _storage;
	}

	public boolean add(Task newTask) {
		storage.add(newTask);
		return true;
	}
	
	public ArrayList<Task> showOverDue(){
		ArrayList<Task> OverDueTasks = new ArrayList<Task>();
		
		OverDueTasks = storage.showOverDue();
		
		return OverDueTasks;
	}
	
	public ArrayList<Task> showWeek() {
		ArrayList<Task> WeekTaskList = new ArrayList<Task>();
		
		WeekTaskList = storage.showWeek();
		
		return WeekTaskList;
	}
	
	public ArrayList<Task> showMonth() {
		ArrayList<Task> MonthTaskList = new ArrayList<Task>();
		
		MonthTaskList = storage.showMonth();
		
		return MonthTaskList;		
	}
	
	public void showWarning(Task task){
		 storage.EqualsTo(task);
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
	
	public ArrayList<Task> searchPeriod(LocalDateTime startDate,LocalDateTime endDate){
		ArrayList<Task> TaskList = new ArrayList<Task>();
		
		TaskList = storage.searchTaskWithinPeriod(startDate, endDate);
		
		return TaskList;	
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
		return storage.displayDoneTasks();
	}
}
