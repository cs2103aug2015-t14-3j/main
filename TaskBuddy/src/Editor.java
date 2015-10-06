import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class Editor {
	
	private Storage storage;
	
	private final String EDIT_INDEX = "index";
	private final String EDIT_FIELD = "field";
	private final String EDIT_VALUE = "newValue";
	
	private final String DATE_FORMAT1 = "HHmm dd/MM/yyyy";
	private final String DATE_FORMAT2 = "HHmm dd/M/yyyy";
	
	private final String EDIT_OUTPUT = "Edited task no.%d %s to %s\n\n";
	private final String INVALID_INDEX = "Invalid Index specified\n\n";
	private final String INVALID_FIELD = "Invalid field specified\n\n";
	
	public Editor(Storage storage) {
		this.storage = storage;
	}
	
	public String edit(Map<String, String> parsedCommand, ArrayList<Task> lastDisplay) {
		int index = Integer.parseInt(parsedCommand.get(EDIT_INDEX));
		String field = parsedCommand.get(EDIT_FIELD);
		String value = parsedCommand.get(EDIT_VALUE);
		
		if (index > lastDisplay.size()){
			return INVALID_INDEX;
		}
		
		Task taskToEdit = lastDisplay.get(index - 1);
		
		ArrayList<Task> allTasks = storage.display();
		
		int storageIndex = allTasks.indexOf(taskToEdit);
		
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
