package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class EditCommand implements Command {
	
	protected String index;
	protected String newValue;
	protected final String EDIT_OUTPUT = "Edited task no.%d %s to %s\n";
	private final String DATE_FORMAT1 = "HH dd/MM/yyyy";
	protected final String INVALID_INDEX = "Invalid Index specified\n\n";
	
	protected final String ERROR_INT = "Index provided is not an Integer";
	protected final String ERROR_NEGATIVE = "Index provided must be larger than 0";
	protected final String ERROR_RANGE = "Index providded is larger than last display";

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
			dt = LocalDateTime.parse("00 " + dateTime, formatter);
			
			return dt;
		} else {
			//Time specified
			formatter = DateTimeFormatter.ofPattern(DATE_FORMAT1);
			dt = LocalDateTime.parse(dateTime, formatter);
			
			return dt;
		}
	
	}
}