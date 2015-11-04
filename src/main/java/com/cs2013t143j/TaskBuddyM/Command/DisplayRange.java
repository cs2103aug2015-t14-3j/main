package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;


public class DisplayRange extends DisplayCommand {
	
	private String startDate;
	private String endDate;
	private String output = new String();
	
	private final String INFO = "Display Range %s %s";
	
	private final String DATE_FORMAT = "HH dd/MM/yyyy";
	private final String DISPLAY_HEADER_AFTER = "Here are your tasks due after %s:\n";
	
	private final String ERROR_FORMAT = "Invalid date format(Should be dd/mm/yyyy)";
	
	public DisplayRange(String _startDate, String _endDate) {
		startDate = _startDate;
		endDate = _endDate;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		if (startDate == null || startDate == "" || startDate == " ") {
			throw new CommandAttributeError(ERROR_DATE);
		} else if (endDate == null || endDate == "" || endDate == " ") {
			throw new CommandAttributeError(ERROR_DATE);
		}
		
		lastDisplay = extractWithin(startDate,endDate,sAccess);
		
		output = parseTasks(output);

		return output;
	}
private ArrayList<Task> extractWithin(String startDate,String endDate,StorageAccess sAccess){
		ArrayList<Task> TaskList = new ArrayList<Task>();
		
		LocalDateTime startDateTime = convertDateTime(startDate);
		LocalDateTime endDateTime = convertDateTime(endDate);
		
		TaskList = sAccess.searchPeriod(startDateTime, endDateTime);
		
		return TaskList;
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
		try {
			formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
			dt = LocalDateTime.parse("2359 " + dateTime, formatter);
		} catch (Exception e) {
			formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
			dt = LocalDateTime.parse("2359 " + dateTime, formatter);
		}
		
		
		return dt;
	} else {
		//Time specified
		try {
			formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
			dt = LocalDateTime.parse(dateTime, formatter);
		} catch (Exception e) {
			formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
			dt = LocalDateTime.parse(dateTime, formatter);
		}
		
		
		return dt;
	}
}
	
public String info() {
		String output = String.format(INFO, startDate, endDate);
		
		return output;
	}
}
