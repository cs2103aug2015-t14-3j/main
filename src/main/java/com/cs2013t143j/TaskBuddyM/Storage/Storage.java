package com.cs2013t143j.TaskBuddyM.Storage;
import java.util.ArrayList;
import java.time.LocalDateTime;
import com.cs2013t143j.TaskBuddyM.Storage.Task;
import com.cs2013t143j.TaskBuddyM.Storage.StorageIO;
import java.lang.NullPointerException;


public class Storage {

	private ArrayList<Task> tasks = new ArrayList<Task>();
	private ArrayList<Task>doneTasks = new ArrayList<Task>();
	private ArrayList<Task>newTaskList = new ArrayList<Task>();
	
	
public Storage() {
		tasks = StorageIO.readFile();
	}
		
public void add(Task newTask) {
		tasks.add(newTask);
		writeToFile();
	}
	
	public void delete(int index) {
		assert index != 0;
		
		tasks.remove(index);
		writeToFile();
	}
	
	public Task getTask(int index) {
		assert index != 0;
		
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
		assert index != 0;
		
		
		Task task = tasks.get(index);
		task.setEndDateTime(newEndDate);
		//tasks.add(index,task);
		
		writeToFile();
	}
	
public ArrayList<Task> searchTaskWithinPeriod(LocalDateTime startDateTime,LocalDateTime endDateTime)
{
	try{
		
		for(int i=0;i< tasks.size();i++){
			Task task = tasks.get(i);
			LocalDateTime startDate= task.getStartDateTime();
			LocalDateTime endDate = task.getEndDateTime();
			
			if((startDate!= null) && (endDate != null) && (startDate.compareTo(startDateTime) >= 0) && (endDate.compareTo(endDateTime) <= 0) )   {
				newTaskList.add(task);
			}
		
		}
		} catch(NullPointerException e){
			System.out.println("Wrong format");
		}
				
		return newTaskList;
}

public void clearAll() {
	tasks = new ArrayList<Task>();
	writeToFile();
	}

public ArrayList<Task> ShowOverDue(){
	
	ArrayList<Task> OverDueTaskList = new ArrayList<Task>();
	
	for(int i=0; i< tasks.size();i++){
		 Task task = tasks.get(i);
		 if((task.getStartDateTime()== null) &&(task.getEndDateTime() != null) &&(LocalDateTime.now().isAfter(task.getEndDateTime()))) {
			 OverDueTaskList.add(task);
		}
		 if((task.getStartDateTime()!=null)&&(task.getEndDateTime()!= null)&&(LocalDateTime.now().isAfter(task.getEndDateTime()))) {
			 OverDueTaskList.add(task);
		 }
		
	}
	return OverDueTaskList;
}

public ArrayList<Task> showWeek(){
ArrayList<Task> WeekList = new ArrayList<Task>();

for(int i=0;i< tasks.size();i++){
 Task task = tasks.get(i);
 LocalDateTime endTime = task.getEndDateTime();
 LocalDateTime OneWeekLater = (LocalDateTime.now()).plusWeeks(1);
 
 if( (task.getStartDateTime() == null)&&  (endTime != null) &&(endTime.isAfter(LocalDateTime.now()))  && (endTime.isBefore(OneWeekLater))  ){ 
	 WeekList.add(task);
}
 
 if( (task.getStartDateTime() != null)&&  (endTime != null) &&(endTime.isAfter(LocalDateTime.now()))  && (endTime.isBefore(OneWeekLater))  ){ 
	 WeekList.add(task);
}
}
	return WeekList;
}


public ArrayList<Task> showMonth(){
ArrayList<Task> MonthList = new ArrayList<Task>();

for(int i=0;i< tasks.size();i++){
 Task task = tasks.get(i);
 LocalDateTime endTime = task.getEndDateTime();
 LocalDateTime OneMonthLater = (LocalDateTime.now()).plusMonths(1);
 
 if( (task.getStartDateTime() == null)&&  (endTime != null) &&(endTime.isAfter(LocalDateTime.now()))  && (endTime.isBefore(OneMonthLater))  ){ 
	 MonthList.add(task);
}
 
 if( (task.getStartDateTime() != null)&&  (endTime != null) &&(endTime.isAfter(LocalDateTime.now()))  && (endTime.isBefore(OneMonthLater))  ){ 
	 MonthList.add(task);
}
}
	return MonthList;
}

public void done(int index){
		assert index != 0;
		
		boolean done = true;
		
		Task task = tasks.get(index);
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
	
	public boolean EqualsTo(Task task){
		boolean str = false;
		
		String description = task.getDescription();
		LocalDateTime startTime = task.getStartDateTime();
		LocalDateTime endTime = task.getEndDateTime();
		
		for(int i=0; i< tasks.size();i++){
			Task oldTask = tasks.get(i);
			if((oldTask.getDescription() == description)  && (oldTask.getStartDateTime() == startTime) &&
					(oldTask.getEndDateTime() == endTime)){
					 str = true;
			}
		}
			
		return str;
	
	}





}



