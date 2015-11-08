package com.cs2013t143j.TaskBuddyM.Storage;

import static org.junit.Assert.*;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.cs2013t143j.TaskBuddyM.Command.CommandAttributeError;
import com.cs2013t143j.TaskBuddyM.Storage.Storage;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.junit.Test;

public class StorageTest extends Storage {
	
	public static final DateTimeFormatter FORMAT_STORAGE_DATETIME = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	Storage storage = new Storage();
	@Test
	public void testAdd() {
		ArrayList<Task> TaskList = new ArrayList<Task>();
		
		TaskList= storage.display();
		Task testTask  = new Task();
		String description = "new";
		testTask.setDescription(description);
		testTask.setStartDateTime(null);
		testTask.setEndDateTime(null);
		testTask.setIsDone(false);
		
		TaskList.add(testTask);
		
		int index = TaskList.size()-1;
		
		assertEquals(TaskList.get(index).getDescription(),description);
		//fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		ArrayList<Task> TaskList = new ArrayList<Task>();
		
		TaskList = storage.display();
		int index = TaskList.size()-1;
		TaskList.remove(index);
		
		int newindex = TaskList.size()-1;
		
		assertEquals(index-1,newindex);
		
		//fail("Not yet implemented");
	}

	@Test
	public void testGetTask() {
		ArrayList<Task> TaskList = new ArrayList<Task>();
		String olddescription = "test task";
		Task testTask = new Task();
		testTask.setDescription("test task");
		testTask.setEndDateTime(null);
		testTask.setStartDateTime(null);
		testTask.setIsDone(false);
		
		storage.add(testTask);
		
		TaskList = storage.display();
		int index = TaskList.size()-1;
		
		String description = storage.getTask(index).getDescription();
		
		
		assertEquals(olddescription,description);
		
		//fail("Not yet implemented");
	}

	@Test
	public void testUpdateDescription() {
		ArrayList<Task> TaskList = new ArrayList<Task>();
		Task testTask = new Task();
		testTask.setDescription("test task");
		testTask.setEndDateTime(null);
		testTask.setStartDateTime(null);
		testTask.setIsDone(false);
		
		storage.add(testTask);
		
		TaskList = storage.display();
		int index = TaskList.size()-1;
		String newdescription = "new description";
		
		storage.updateDescription(index, newdescription);
		String description = storage.getTask(index).getDescription();
		
		assertEquals(newdescription,description);
	}

	@Test
public void testUpdateStartDate() {
		ArrayList<Task> TaskList = new ArrayList<Task>();
		Task testTask = new Task();
		testTask.setDescription("test task");
		testTask.setEndDateTime(null);
		testTask.setStartDateTime(null);
		testTask.setIsDone(false);
		
		storage.add(testTask);
		
		TaskList = storage.display();
		int index = TaskList.size()-1;
		String oldstartdate = "";
		
		LocalDateTime oldstartdatetime = convertToDate(oldstartdate);
		storage.updateStartDate(index, oldstartdatetime);
		
		String newstartdate = storage.getTask(index).getStartDateTimeInString();
		
		assertEquals(oldstartdate,newstartdate);
		//fail("Not yet implemented");
}

	@Test
	public void testUpdateEndDate() {
		ArrayList<Task> TaskList = new ArrayList<Task>();
		TaskList = storage.display();
		int index = TaskList.size()-1;
		
		String newenddate = "";
		LocalDateTime newenddatetime = convertToDate(newenddate);
		
		storage.updateEndDate(index, newenddatetime);
		String oldenddate = storage.getTask(index).getEndDateTimeInString();
		
		assertEquals(newenddate,oldenddate);
		//fail("Not yet implemented");
	}

	
	//public void testSearchTaskWithinPeriod() {
		//fail("Not yet implemented");
	//}

	@Test
	public void testClearAll() {
		ArrayList<Task> TaskList = new ArrayList<Task>();
		ArrayList<Task> newTaskList = new ArrayList<Task>();
		Task testTask = new Task();
		
		testTask.setDescription("test task");
		testTask.setEndDateTime(null);
		testTask.setStartDateTime(null);
		testTask.setIsDone(false);
		
		storage.add(testTask);
		storage.clearAll();
		
		TaskList = storage.display();
		
		assertEquals(TaskList,newTaskList);
		
		//fail("Not yet implemented");
	}

	
	//public void testShowOverDue() {
		//fail("Not yet implemented");
	//}

	
	//public void testShowWeek() {
		//fail("Not yet implemented");
	//}

	
	//public void testShowMonth() {
		//fail("Not yet implemented");
	//}

	@Test
	public void testDone() {
		ArrayList<Task> TaskList = new ArrayList<Task>();
		TaskList = storage.display();
		int index  = TaskList.size()-1;
		
		if(TaskList.get(index).isDone() == false){
		storage.done(index);
		}
		//check the size of the arraylist after done is invoked
		int new_index = TaskList.size()-1;
		assertEquals(index-1,new_index);
		//fail("Not yet implemented");
}

	
	//public void testDisplayDoneTasks() {
		//fail("Not yet implemented");
	//}

	
private static LocalDateTime convertToDate(String strDate){
		
		try {
				return LocalDateTime.parse(strDate, FORMAT_STORAGE_DATETIME);
			} catch (Exception e) {
				return null;
			}
		}
}