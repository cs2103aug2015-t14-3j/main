import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class StorageStub extends Storage {
	
	private ArrayList<Task> tasks;
	private final String dataFile = "data.txt"; 
	
	public final DateTimeFormatter FORMAT_STORAGE_DATETIME = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	
	public StorageStub() {
		tasks = readDataFile();
	}
	
	private ArrayList<Task> readDataFile() {
		ArrayList<Task> allTasks = new ArrayList<Task>();
		Task task;
		File fileToRead = new File(dataFile);
		
		try{
		BufferedReader reader = new BufferedReader(new FileReader(fileToRead));
		String line = reader.readLine();
		while (line != null) {
		task = convertStringToTask(line);
		allTasks.add(task);
		line = reader.readLine();
		}
		reader.close();
		} catch (FileNotFoundException ex) {
		createNewFile();
		} catch (IOException ex) {
		System.out.println(ex);
		}
		
		return allTasks;
	}
	
	private Task convertStringToTask(String line) {
		String[] splitString = line.split("&&");
		Task task = null;
		
		switch(splitString.length) {
		case 1:
			task = createFloating(splitString);
			break;
		case 2:
			task = createDeadline(splitString);
			break;
		case 3:
			task = createEvent(splitString);
			break;
		}
		return task;
	}
	
	private Task createFloating(String[] splitString) {
		String description = splitString[0];
		
		return new Task(description);
	}
	
	private Task createDeadline(String[] splitString) {
		String description = splitString[0];
		String endDate = splitString[1];
		
		LocalDateTime end = convertStringToDateTime(endDate);
		
		return new Task(description, end);
	}
	
	private Task createEvent(String[] splitString) {
		String description = splitString[0];
		String startDate = splitString[1];
		String endDate = splitString[2];
		
		LocalDateTime start = convertStringToDateTime(startDate);
		LocalDateTime end = convertStringToDateTime(endDate);
		
		return new Task(description, start, end);
	}
	
	private LocalDateTime convertStringToDateTime(String dateTime) {
		return LocalDateTime.parse(dateTime, FORMAT_STORAGE_DATETIME);
	}
	
	private void createNewFile() {
		//Creates an empty file at path specified
		File fileToClear = new File(dataFile);
		try{
		PrintWriter writer = new PrintWriter(new FileWriter(fileToClear));
		writer.close();
		} catch (IOException ex) {
		System.out.println(ex);
		}
	}

	public void add(Task newTask) {
		tasks.add(newTask);
		writeTasksToFile();
	}
	
	public void delete(int index) {
		tasks.remove(index);
		writeTasksToFile();
	}
	
	public ArrayList<Task> display() {
		return tasks;
	}
	
	public void updateDescription(int index, String newdescription){
		Task task = tasks.get(index);
		task.setDescription(newdescription);
		writeTasksToFile();
	}

	public void updateStartDate(int index,LocalDateTime newStartDate) {
		Task task = tasks.get(index);
		task.setStartDateTime(newStartDate);
		writeTasksToFile();
	}

	public void updateEndDate(int index,LocalDateTime newEndDate) {
		Task task = tasks.get(index);
		task.setEndDateTime(newEndDate);
		writeTasksToFile();
	}
	
	private void writeTasksToFile() {
		
		ArrayList<String> lines = new ArrayList<String>();
		
		int i;
		
		File fileToRead = new File(dataFile);
		
		for(i=0; i<tasks.size(); ++i) {
			Task task = tasks.get(i);
			String line = convertTaskToString(task);
			lines.add(line);
		}
		
		createNewFile();
		
		try{
			PrintWriter writer = new PrintWriter(new FileWriter(fileToRead, true));
			for(i=0; i<lines.size(); ++i) {
				writer.println(lines.get(i));
			}
			writer.close();
			} catch (IOException ex) {
			System.out.println(ex);
			}

		
	}
	
	private String convertTaskToString(Task task) {
		
		String description = task.getDescription();
		String startDate = task.getStartDateTimeInString();
		String endDate = task.getEndDateTimeInString();
		
		String line = description;
		
		if (startDate != "") {
			line += "&&";
			line += startDate;
		}
		
		if (endDate != "") {
			line += "&&";
			line += endDate;
		}
		
		return line;
	}

}
