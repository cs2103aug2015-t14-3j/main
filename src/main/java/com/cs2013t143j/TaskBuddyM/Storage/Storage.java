package com.cs2013t143j.TaskBuddyM.Storage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.time.LocalDateTime;

import com.cs2013t143j.TaskBuddyM.Command.AddDeadline;
import com.cs2013t143j.TaskBuddyM.Storage.Task;
import com.cs2013t143j.TaskBuddyM.Storage.StorageIO;

import java.lang.NullPointerException;


//@@author A0101794H
public class Storage {
	
	private static final Logger logger = Logger.getLogger(AddDeadline.class.getName());
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
		
		writeToFile();
	}
	
	public void updateStartDate(int index,LocalDateTime newStartDate) {
		assert index != 0;
		Task task = tasks.get(index);
		task.setStartDateTime(newStartDate);
		
		writeToFile();
	}
	
	public void updateEndDate(int index,LocalDateTime newEndDate) {
		assert index != 0;
		
		Task task = tasks.get(index);
		task.setEndDateTime(newEndDate);
		
		writeToFile();
	}
	
	public ArrayList<Task> searchTaskWithinPeriod(LocalDateTime startDateTime,LocalDateTime endDateTime){
	
		ArrayList<Task> newTaskList = new ArrayList<Task>();
		
		for(int i=0;i< tasks.size();i++){
			Task task = tasks.get(i);
			LocalDateTime startDate= task.getStartDateTime();
			LocalDateTime endDate = task.getEndDateTime();
			try{
			//check if task is an event task
			if((startDate!= null) && (endDate != null) && (startDate.compareTo(startDateTime) >= 0) && (endDate.compareTo(endDateTime) <= 0) )   {
				newTaskList.add(task);
			}
			//check if task is a deadline task
			if((startDate == null) && (endDate != null) && (endDate.compareTo(endDateTime) <= 0) ) {
				newTaskList.add(task);
			}
		
		}catch(NullPointerException e){
			System.out.println("Nothing to display!");
		}
		}		
		return newTaskList;
	}

	public void clearAll() {
	tasks = new ArrayList<Task>();
	writeToFile();
	}

	public ArrayList<Task> showOverDue(){
	
	ArrayList<Task> OverDueTaskList = new ArrayList<Task>();
	
	for(int i=0; i< tasks.size();i++){
		 Task task = tasks.get(i);
		 try{
		  //check if task is deadline task
		 if((task.getStartDateTime()== null) &&(task.getEndDateTime() != null) &&(LocalDateTime.now().isAfter(task.getEndDateTime()))) {
			 OverDueTaskList.add(task);
		}
		 //check if task is event task
		 if((task.getStartDateTime()!=null)&&(task.getEndDateTime()!= null)&&(LocalDateTime.now().isAfter(task.getEndDateTime()))) {
			 OverDueTaskList.add(task);
		 }
		
	
	} catch(NullPointerException e){
		System.out.println("Nothing to display!");
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
		try{
		//check if task is deadline task
		if( (task.getStartDateTime() == null)&&  (endTime != null) &&(endTime.isAfter(LocalDateTime.now()))  && (endTime.isBefore(OneWeekLater))  ){ 
			WeekList.add(task);
		}
		//check if task is event task
		if( (task.getStartDateTime() != null)&&  (endTime != null) &&(endTime.isAfter(LocalDateTime.now()))  && (endTime.isBefore(OneWeekLater))  ){ 
			WeekList.add(task);
		}
	
		}catch(NullPointerException e){
 		System.out.println("Nothing to display!");
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
		try{
		//check if task is deadline task
		if( (task.getStartDateTime() == null)&&  (endTime != null) &&(endTime.isAfter(LocalDateTime.now()))  && (endTime.isBefore(OneMonthLater))  ){ 
			MonthList.add(task);
		}
		//check if task is event task
		if( (task.getStartDateTime() != null)&&  (endTime != null) &&(endTime.isAfter(LocalDateTime.now()))  && (endTime.isBefore(OneMonthLater))  ){ 
			MonthList.add(task);
		}
		}catch(NullPointerException e){
	 		System.out.println("Nothing to display!");
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
	
	public void EqualsTo(Task task){
		 	
			for(int i=0;i< tasks.size();i++){
			Task newtask = tasks.get(i);
			LocalDateTime newendTime = newtask.getEndDateTime();
			LocalDateTime newstartTime = newtask.getStartDateTime();
			
			LocalDateTime endTime = task.getEndDateTime();
			LocalDateTime startTime = task.getStartDateTime();
	
			try{
			//check if task is event task
			if((startTime != null) && (endTime != null) && (startTime.compareTo(newstartTime) == 0) && (endTime.compareTo(newendTime) == 0))   {
				logger.setLevel(Level.WARNING);
				logger.log(Level.WARNING,"Scheduling conflict/You already have entered the task!");
				System.exit(0);
			}
			//check if task is deadline task
			if( (startTime == null) && (endTime != null)&&  (endTime.compareTo(newendTime) == 0)){
				logger.setLevel(Level.WARNING);
				logger.log(Level.WARNING,"You have another task ending with the same deadline!");
			
			}
			}catch(NullPointerException e){
		 		System.out.println("Nothing to display!");
		 	}
			}
	}
	

}
	

	


	
	
	
	
	
	

	






