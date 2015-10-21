package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class AddCommand implements Command {
	
	protected String description;
	
	protected final String DATE_FORMAT1 = "HH dd/MM/yyyy";
	
	protected final String ADD_OUTPUT = "Added new Task to TaskBuddy\n\n";
	
	protected LocalDateTime convertDateTime(String dateTime) {
		
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
