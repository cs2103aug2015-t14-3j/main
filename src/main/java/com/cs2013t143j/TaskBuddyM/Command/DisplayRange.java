package com.cs2013t143j.TaskBuddyM.Command;

//@@ author A0101794H
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class DisplayRange extends DisplayCommand {
	
	private String startDate;
	private String endDate;
	private String output = new String();
	
	private final String INFO = "Display Range %s %s";
	
	private final String DISPLAY_RANGE = "Here are your tasks from %s to %s\n";
	
	private final String ERROR_FORMAT = "Invalid date format(Should be dd/mm/yyyy)";
	private final String ERROR_START = "Invalid or no start date specified";
	private final String ERROR_END = "Invalid or no end date specified";
	
	private final int DAY_POS = 0;
	private final int MONTH_POS = 1;
	private final int YEAR_POS = 2;
	
	public DisplayRange(String _startDate, String _endDate) {
		startDate = _startDate;
		endDate = _endDate;
	}

	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {

		isValid();

		tasks = extractWithin(startDate, endDate, sAccess);
		
		String[] splitStart = startDate.split(" ");
		String[] splitEnd = endDate.split(" ");
		
		startDate = splitStart[splitStart.length - 1];
		endDate = splitEnd[splitEnd.length - 1];
		
		output = String.format(DISPLAY_RANGE, startDate, endDate);
		output = parseTasks(output);

		return output;
	}
	
	public boolean isValid() throws CommandAttributeError {
		if (startDate == null || startDate == "" || startDate == " ") {
			throw new CommandAttributeError(ERROR_START);
		} else if (endDate == null || endDate == "" || endDate == " ") {
			throw new CommandAttributeError(ERROR_END);
		}
		
		return true;
	}
	
	private ArrayList<Task> extractWithin(String startDate,String endDate,StorageAccess sAccess) throws CommandAttributeError {
		ArrayList<Task> TaskList = new ArrayList<Task>();

		LocalDateTime startDateTime = convertDateTime(startDate);
		LocalDateTime endDateTime = convertDateTime(endDate);

		TaskList = sAccess.searchPeriod(startDateTime, endDateTime);

		return TaskList;
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
		String output = String.format(INFO, startDate, endDate);

		return output;
	}
}
