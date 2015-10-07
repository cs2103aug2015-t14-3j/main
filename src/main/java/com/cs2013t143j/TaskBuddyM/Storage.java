package com.cs2013t143j.TaskBuddyM;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Storage {

    private ArrayList<Task> taskList;
    private static final String FILE_NAME = "data.txt";
    private StorageIO operations;
    
public Storage(){
    operations = new StorageIO(FILE_NAME);
} 	



public void loadTasks(){
	taskList = new ArrayList<Task>();
    operations.load(taskList);		
}

public void add(Task newTask) {
	taskList.add(newTask);
	writeToFile(); 
}

public void delete(int index) {
	taskList.remove(index);
	writeToFile();
}

public Task getTask(int index) {
	
	Task task = taskList.get(index);
		return task;
}

public void updateDescription(int index, String newdescription){
	Task task = taskList.get(index);
	task.setDescription(newdescription);
	taskList.add(index,task);
	writeToFile();
}

public void updateStartDate(int index,LocalDateTime newStartDate) {
	Task task = taskList.get(index);
	task.setStartDateTime(newStartDate);
	taskList.add(index,task);
	writeToFile();
}

public void updateEndDate(int index,LocalDateTime newEndDate) {
	Task task = taskList.get(index);
	task.setEndDateTime(newEndDate);
	taskList.add(index,task);
	
	writeToFile();
}

public ArrayList<Task> display() {
	return taskList;
}

public void writeToFile() {
    StorageIO.writeToFile(taskList);
}

}
