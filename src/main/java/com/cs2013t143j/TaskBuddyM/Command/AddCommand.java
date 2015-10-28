package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class AddCommand implements Command {
	
	protected String description;
	
	protected final String DATE_FORMAT1 = "HH dd/MM/yyyy";
	
	protected final String ADD_OUTPUT = "Added new Task to TaskBuddy\n\n";
	
	protected final String ERROR_DESCRIPTION = "Invalid Description(Cannot be empty)";
	protected final String ERROR_START = "Invalid Start Date";
	protected final String ERROR_END = "Invalid End Date";
	
	private final String ERROR_FORMAT = "Invalid date format(Should be dd/mm/yyyy)";
	
	protected LocalDateTime convertDateTime(String dateTime) throws CommandAttributeError {
		
		if (dateTime == null) {
			return null;
		}
		String[] splitDateTime = dateTime.split(" ");
		DateTimeFormatter formatter;
		LocalDateTime dt;
		
		if (splitDateTime.length == 1) {
			//No time specified	
			formatter = DateTimeFormatter.ofPattern(DATE_FORMAT1);
			
			try{
				dt = LocalDateTime.parse("23 " + dateTime, formatter);
			} catch (DateTimeParseException e) {
				throw new CommandAttributeError(ERROR_FORMAT);
			}
			
			return dt;
		} else {
			//Time specified
			formatter = DateTimeFormatter.ofPattern(DATE_FORMAT1);
			
			try {
				dt = LocalDateTime.parse(dateTime, formatter);
			} catch (DateTimeParseException e) {
				throw new CommandAttributeError(ERROR_FORMAT);
			}
			
			return dt;
		}
	}
}
