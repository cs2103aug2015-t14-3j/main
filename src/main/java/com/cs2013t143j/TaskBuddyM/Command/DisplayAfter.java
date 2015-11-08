package com.cs2013t143j.TaskBuddyM.Command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

//@@author A0121327U
public class DisplayAfter extends DisplayCommand {
	
	private String output = new String();
	
	private final String INFO = "Display After %s";
	
	private final String DISPLAY_HEADER_AFTER = "Here are your tasks due after %s:\n";
	
	private final String ERROR_FORMAT = "Invalid date format(Should be dd/mm/yyyy)";

	private final int DAY_POS = 0;
	private final int MONTH_POS = 1;
	private final int YEAR_POS = 2;
	
	public DisplayAfter(String _date) {
		date = _date;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		isValid();
		
		ArrayList<Task> allTasks = sAccess.display();
		
		tasks = extractAfter(allTasks, date);
		
		output = parseTasks(output);

		return output;
	}
	
	public boolean isValid() throws CommandAttributeError {
		if (date == null || date == "" || date == " ") {
			throw new CommandAttributeError(ERROR_DATE);
		}
		
		return true;
	}
	
	private ArrayList<Task> extractAfter(ArrayList<Task> allTasks, String _date) throws CommandAttributeError {
		ArrayList<Task> result = new ArrayList<Task>();
		
		LocalDateTime date;
		date = convertDateTime(_date);

		output = String.format(DISPLAY_HEADER_AFTER, _date);
		
		int i;
		LocalDateTime endDate;
		
		for (i=0; i<allTasks.size(); ++i) {
			
			Task task = allTasks.get(i);
			
			endDate = task.getEndDateTime();
			
			if (endDate != null && endDate.isAfter(date) && task.isDone() == false) {
				result.add(task);
			}
		}
		
		return result;
	}
	
	private LocalDateTime convertDateTime(String dateTime) throws CommandAttributeError {
		
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
	
	public String info() {
		String output = String.format(INFO, date);
		
		return output;
	}
}
