package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//@@author A0121327U
public abstract class EditCommand implements Command {
	
	protected String index;
	protected String newValue;
	protected final String EDIT_OUTPUT = "Edited task no.%d %s to %s\n";
	protected final String INVALID_INDEX = "Invalid Index specified\n\n";
	
	protected final String ERROR_INT = "Index provided is not an Integer";
	protected final String ERROR_NEGATIVE = "Index provided must be larger than 0";
	protected final String ERROR_RANGE = "Index provided is larger than last display";
	
	private final int DAY_POS = 0;
	private final int MONTH_POS = 1;
	private final int YEAR_POS = 2;
	
	private final String ERROR_FORMAT = "Invalid date format(Should be dd/mm/yyyy)";

	protected LocalDateTime convertDateTime(String dateTime) throws CommandAttributeError {
		
		if (dateTime == null) {
			return null;
		}
		DateTimeFormatter formatter;
		LocalDateTime dt;
		
		String format = createFormatter(dateTime);
		formatter = DateTimeFormatter.ofPattern(format);
		
		int dateTimeSize = dateTime.split(" ").length;
			
		try{
			if (dateTimeSize == 2) {
				dt = LocalDateTime.parse(dateTime, formatter);
			} else {
				dt = LocalDateTime.parse("23 " + dateTime, formatter);
			}
		} catch (DateTimeParseException e) {
			throw new CommandAttributeError(ERROR_FORMAT);
		}
		return dt;
	}
	
	private String createFormatter(String dateTime) throws CommandAttributeError {
		//Generates a format string for DateTimeFormatter based on the input datetime string
		
		String format = "HH ";
		String date;
		
		String[] splitDT = dateTime.split(" ");
		
		if (splitDT.length == 1) {
			//No Time specified
			date = splitDT[0];
		} else {
			// Time specified
			date = splitDT[1];
		}
		
		String[] splitDate = date.split("/");
		
		int daySize = splitDate[DAY_POS].length();
		int monthSize = splitDate[MONTH_POS].length();
		int yearSize = splitDate[YEAR_POS].length();
		
		int i =0 ;
		
		for (i=0; i<daySize; ++i) {
			format += "d";
		}
		format += "/";
		
		for (i=0; i<monthSize; ++i) {
			format += "M";
		}
		format += "/";
		
		for (i=0; i<yearSize; ++i) {
			format += "y";
		}
		
		return format;
	}
}