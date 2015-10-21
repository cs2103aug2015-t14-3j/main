package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class EditCommand implements Command {
	
	protected int index;
	protected final String EDIT_OUTPUT = "Edited task no.%d %s to %s\n\n";
	private final String DATE_FORMAT1 = "HHmm dd/MM/yyyy";
	protected final String INVALID_INDEX = "Invalid Index specified\n\n";

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
			dt = LocalDateTime.parse("2359 " + dateTime, formatter);
			
			return dt;
		} else {
			//Time specified
			formatter = DateTimeFormatter.ofPattern(DATE_FORMAT1);
			dt = LocalDateTime.parse(dateTime, formatter);
			
			return dt;
		}
	
	}
}