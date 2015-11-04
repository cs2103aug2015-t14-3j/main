package com.cs2013t143j.TaskBuddyM.Logic;
import java.time.LocalDateTime;


import com.cs2013t143j.TaskBuddyM.Storage.History;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import com.cs2013t143j.TaskBuddyM.Storage.Task;
public class Editor {
	
	private StorageAccess storage;
	private History history;
	
	private final String EDIT_INDEX = "index";
	private final String EDIT_FIELD = "field";
	private final String EDIT_VALUE = "newValue";
	
	private final String DATE_FORMAT1 = "HHmm dd/MM/yyyy";
	private final String DATE_FORMAT2 = "HH dd/M/yyyy";
	
	private final String EDIT_OUTPUT = "Edited task no.%d %s to %s\n\n";
	private final String INVALID_INDEX = "Invalid Index specified\n\n";
	private final String INVALID_FIELD = "Invalid field specified\n\n";
	
	public Editor(StorageAccess storage,History history) {
		this.storage = storage;
		this.history = history;
	}
	
	public String edit(Map<String, String> parsedCommand, ArrayList<Task> lastDisplay) throws ParserContentError {
		int index = Integer.parseInt(parsedCommand.get(EDIT_INDEX));
	
		String field = parsedCommand.get(EDIT_FIELD);
		String value = parsedCommand.get(EDIT_VALUE);
		
		if (field == null) {
			throw new ParserContentError("Edit field is null");
		} else if (value == null) {
			throw new ParserContentError("Edit new value is null");
		}
		
		if (index > lastDisplay.size()){
			return INVALID_INDEX;
		}
		
		ArrayList<Task> allTasks = storage.display();
		Task taskToEdit = new Task();
		taskToEdit = lastDisplay.get(index -1);
		Task oldTask = new Task(taskToEdit.getDescription(),taskToEdit.getStartDateTime(),taskToEdit.getEndDateTime());
		int storageIndex = allTasks.indexOf(taskToEdit);
		history.pushUndoEdit(oldTask,storageIndex);
		
		try {
			switch (field) {
			case "description":
				storage.updateDescription(storageIndex, value);
				break;
			case "start date":
				LocalDateTime newValue = convertDateTime(value);
				storage.updateStartDate(storageIndex, newValue);
				break;
			case "end date":
				LocalDateTime newValue2 = convertDateTime(value);
				storage.updateEndDate(storageIndex, newValue2);
				break;
			default:
				return INVALID_FIELD;
			}
		} catch (Exception e) {
			return INVALID_FIELD;
		}
		
		String output = String.format(EDIT_OUTPUT, index, field, value);
		return output;
		
//		return "command: edit " + "index: " + index + " field: " + field + " value: " + value + "\n";
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
				formatter = DateTimeFormatter.ofPattern(DATE_FORMAT1);
				dt = LocalDateTime.parse("2359 " + dateTime, formatter);
			} catch (Exception e) {
				formatter = DateTimeFormatter.ofPattern(DATE_FORMAT2);
				dt = LocalDateTime.parse("2359 " + dateTime, formatter);
			}
			
			
			return dt;
		} else {
			//Time specified
			try {
				formatter = DateTimeFormatter.ofPattern(DATE_FORMAT1);
				dt = LocalDateTime.parse(dateTime, formatter);
			} catch (Exception e) {
				formatter = DateTimeFormatter.ofPattern(DATE_FORMAT2);
				dt = LocalDateTime.parse(dateTime, formatter);
			}
			
			
			return dt;
		}
	
	}
	
}
