package com.cs2013t143j.TaskBuddyM.Logic;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.cs2013t143j.TaskBuddyM.Storage.Task;


public class Adder {
	
	private StorageAccess storage;
	
	private final String ADD_DESCRIPTION = "description";
	private final String ADD_START = "startDate";
	private final String ADD_END = "endDate";
	
	private final String DATE_FORMAT1 = "HH dd/MM/yyyy";
	
	private final String ADD_OUTPUT = "Added new Task to TaskBuddy\n\n";
	
	public Adder(StorageAccess storage) {
			this.storage = storage;
	}
	
	public String add(Map<String, String> parsedCommand) throws ParserContentError {
		String description = parsedCommand.get(ADD_DESCRIPTION);
		String startDate = parsedCommand.get(ADD_START);
		String endDate = parsedCommand.get(ADD_END);
		
		if (description == null) {
			throw new ParserContentError("No Description in add");
		}
		
		LocalDateTime start = convertDateTime(startDate);
		LocalDateTime end = convertDateTime(endDate);
		
		Task task;
		
		if (startDate != null) {
			task = new Task(description,start,end);
			//Event
		}	else if (endDate == null) {
			task = new Task(description);
			//Floating Task
		} else {
			task = new Task(description, end);
			//DeadLine
		}
		
		storage.add(task);
		
		return ADD_OUTPUT;
	}
	
	private LocalDateTime convertDateTime(String dateTime) {
		
		if (dateTime == null) {
			return null;
		}
		String[] splitDateTime = dateTime.split(" ");
		DateTimeFormatter formatter;
		LocalDateTime dt;
		
		if (splitDateTime.length == 1) {
			//No time specified
			
			formatter = DateTimeFormatter.ofPattern(DATE_FORMAT1);
			dt = LocalDateTime.parse("23 " + dateTime, formatter);
			
			return dt;
		} else {
			//Time specified
			formatter = DateTimeFormatter.ofPattern(DATE_FORMAT1);
			dt = LocalDateTime.parse(dateTime, formatter);
			
			return dt;
		}
	
	}
}
