package com.cs2013t143j.TaskBuddyM.Storage;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.Boolean;
import java.lang.String;
import java.io.IOException;
import java.io.FileNotFoundException;




public class StorageIO {
	public static final DateTimeFormatter FORMAT_STORAGE_DATETIME = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
 
	public static  String _fileName;

	public static String filePath = "data.txt";
	private static List<String> commands;
	
	
	protected StorageIO(String FILE_NAME) {
	
	_fileName = FILE_NAME;
	commands = new ArrayList<String>();
}

	
	
protected  void load(ArrayList<Task> Tasks) {

    
    	this.readFile();
    
	
	for(int i=0; i< commands.size();i++) {
    			Task task = createTask(commands.get(i));
    			Tasks.add(task);
   }
}
	
public void readFile() {
		try{
			String command;
			File file = new File(filePath);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			commands.clear();

			while ((command = br.readLine()) != null) {
				commands.add(command);
			}
			br.close();
			fr.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
}

public  static Task createTask(String command){

		String description = "";
		String startDateString = "";
		String endDateString = "";
		String done = "";
		boolean isDone;


		String[] splitComponents = command.split("");


		description = splitComponents[0];
		startDateString = splitComponents[1];
		endDateString = splitComponents[2];
		done = splitComponents[3];

		LocalDateTime startDate = convertToDate(startDateString);
		LocalDateTime endDate = convertToDate(endDateString);


		Task task = new Task(description,startDate,endDate);

		isDone = Boolean.parseBoolean(done);
		task.setIsDone(isDone);

		return task;
	}

private static LocalDateTime convertToDate(String strDate){
		try {
			return LocalDateTime.parse(strDate, FORMAT_STORAGE_DATETIME);
		} catch (Exception e) {
			return null;
		}
}
		
private static String createCommands(Task task) {

		StringBuilder str = new StringBuilder();

		str.append("");

		String description = task.getDescription();
		str.append(description);
		str.append("");

		String startDateTime = task.getStartDateTimeInString();
		str.append(startDateTime);
		str.append("");

		String endDateTime = task.getEndDateTimeInString();
		str.append(endDateTime);
		str.append("");

		String done = task.isDone() + "";
		str.append(done);
		str.append("");


		return str.toString();
}

public static void writeToFile(ArrayList<Task> taskList) {
	commands.clear();
	for(int i=0;i< taskList.size();i++){
		String command = createCommands(taskList.get(i));
		commands.add(command);
	}
	
		try { 
			FileWriter fw = new FileWriter(filePath);
			BufferedWriter bw = new BufferedWriter(fw);

				for(int i=0; i<commands.size();i++) {
					bw.write(commands.get(i));
					}
				
		    bw.close();
			
		}
		catch (Exception e) {

			e.printStackTrace();
		}
	}
}








