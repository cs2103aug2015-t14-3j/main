package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

//@@author A0121327U
public abstract class AddCommand implements Command {
	
	protected String description;
	protected Task addedTask;
	
	protected final String ADD_OUTPUT = "Added new Task to TaskBuddy\n\n";
	
	private final int DAY_POS = 0;
	private final int MONTH_POS = 1;
	private final int YEAR_POS = 2;
	
	protected final String ERROR_DESCRIPTION = "Invalid Description(Cannot be empty)";
	protected final String ERROR_START = "Invalid Start Date";
	protected final String ERROR_END = "Invalid End Date";
	
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
		String date = getDateComponent(dateTime);
		
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
	
	private String getDateComponent(String dateTime) {
		String[] splitDT = dateTime.split(" ");
		String date;
		
		if (splitDT.length == 1) {
			//No Time specified
			date = splitDT[0];
		} else {
			// Time specified
			date = splitDT[1];
		}
		
		return date;
	}
	
	public void undo(StorageAccess sAccess) {
		ArrayList<Task> allTasks = sAccess.display();

		int storageIndex = allTasks.indexOf(addedTask);

		sAccess.delete(storageIndex);
	}
	
	public void redo(StorageAccess sAccess) {
		sAccess.add(addedTask);
	}
}
