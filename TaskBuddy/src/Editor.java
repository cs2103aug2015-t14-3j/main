import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class Editor {
	
	private Storage storage;
	
	private final String EDIT_INDEX = "index";
	private final String EDIT_FIELD = "field";
	private final String EDIT_VALUE = "value";
	
	private final String DATE_FORMAT1 = "HHmm dd/MM/yyyy";
	private final String DATE_FORMAT2 = "HHmm dd/M/yyyy";
	
	public Editor(Storage storage) {
		this.storage = storage;
	}
	
	public String edit(Map<String, String> parsedCommand, ArrayList<Task> tasks) {
		int displayIndex = Integer.parseInt(parsedCommand.get(EDIT_INDEX)) - 1;
		String field = parsedCommand.get(EDIT_FIELD);
		String value = parsedCommand.get(EDIT_VALUE);
		
		Task taskToEdit = tasks.get(displayIndex);
		
//		int storageIndex = taskToEdit.getIndex();
		
		switch (field) {
		case "description":
			storage.updateDescription(Integer.parseInt(storageIndex), value);
			break;
		case "start":
			LocalDateTime newValue = convertDateTime(value);
			storage.updateStartDate(Integer.parseInt(storageIndex), newValue);
		case "end":
			LocalDateTime newValue2 = convertDateTime(value);
			storage.updateEndDate(Integer.parseInt(storageIndex), newValue2);
			break;
		}
		
		return "command: edit " + "index: " + index + " field: " + field + " value: " + value + "\n";
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