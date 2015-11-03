package com.cs2013t143j.TaskBuddyM.Storage;
import java.util.ArrayList;



import java.time.LocalDateTime;
import com.cs2013t143j.TaskBuddyM.Storage.Task;
import com.cs2013t143j.TaskBuddyM.Storage.StorageIO;
import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;

public class Storage {

	private ArrayList<Task> tasks = new ArrayList<Task>();
	private ArrayList<Task>doneTasks = new ArrayList<Task>();
	
	
	
public Storage() {
		tasks = StorageIO.readFile();
	}
		
public void add(Task newTask) {
		tasks.add(newTask);
		writeToFile();
	}
	
	public void delete(int index) {
		tasks.remove(index);
		writeToFile();
	}
	
	public Task getTask(int index) {
		Task task = tasks.get(index);
			return task;
	}
	
	public void updateDescription(int index, String newdescription){
		Task task = tasks.get(index);
		task.setDescription(newdescription);
		//tasks.add(index,task);
		writeToFile();
	}
	
	public void updateStartDate(int index,LocalDateTime newStartDate) {
		Task task = tasks.get(index);
		task.setStartDateTime(newStartDate);
		//tasks.add(index,task);
		writeToFile();
	}
	
	public void updateEndDate(int index,LocalDateTime newEndDate) {
		Task task = tasks.get(index);
		task.setEndDateTime(newEndDate);
		//tasks.add(index,task);
		
		writeToFile();
	}
	
	public ArrayList<Task> searchTaskWithinPeriod(LocalDateTime startDateTime,LocalDateTime endDateTime)
	{
		ArrayList<Task>newTaskList = new ArrayList<Task>();
		int length = tasks.size()-1;
		
		for(int i=0;i<length;i++){
		
			if( ((tasks.get(i).getStartDateTime().compareTo(startDateTime))>= 0) & ((tasks.get(i).getEndDateTime().compareTo(endDateTime)) <= 0) )   {
				newTaskList.add(tasks.get(i));
			}
		}
			return newTaskList;	
	}
	
	public void clearAll() {
	tasks = new ArrayList<Task>();
	writeToFile();
	}
	
	public void done(int index){
		
		
		boolean done = true;
		
		Task task = tasks.get(index-1);
		task.setIsDone(done);
		doneTasks.add(task);
		
		tasks.remove(task);
		
		writeToFile();
	}
	
	public ArrayList<Task> displayDoneTasks() {
		return doneTasks;
	}
	
	public ArrayList<Task> display() {
		return tasks;
	}
	
	public void writeToFile() {
		StorageIO.writeToFile(tasks);
	}
}
