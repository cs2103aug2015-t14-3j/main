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
import java.io.PrintWriter;
import java.lang.NullPointerException;

public class StorageIO {
	public static final DateTimeFormatter FORMAT_STORAGE_DATETIME = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	private static ArrayList<Task> tasks = new ArrayList<Task>();
	public static String fileName = "data.txt";
	
public static ArrayList<Task> readFile() {
		
		File file = new File(fileName);
		try {
			String command;
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

            
			while ((command= br.readLine())!= null) {
				Task task = createTask(command);
				tasks.add(task);
			}
			br.close();
			fr.close();


		} catch(NullPointerException e) {
			createNewFile();
		} catch(IOException e){
			System.out.println(e);
		}

		return tasks;
	}

private static void createNewFile() {
		File file = new File(fileName);
		try {
				PrintWriter writer = new PrintWriter(new FileWriter(file));
				writer.close();
		} catch(IOException e) {
		  System.out.println(e);	
		}
		
	}

public static Task createTask(String command){

		String[] splitComponents = command.split("&&");
		Task task  = null;
		
		
		switch(splitComponents.length) {
		
		case 1: 
			
				task = createFloatingTask(splitComponents);
				break;
		case 2: 
				task = createDeadLineTask(splitComponents);
				break;
		case 3: 
				task = createEventTask(splitComponents);
				break;
		}
			
		return task;
		
	}



private static Task createFloatingTask(String[] splitComponents) {
	String description = splitComponents[0];
	
	return new Task(description);
}

private static Task createDeadLineTask(String[] splitComponents) {
	String description = splitComponents[0];
	String endDateString = splitComponents[1];
	
	LocalDateTime endDate = convertToDate(endDateString);
	
	
	return new Task(description,endDate);
}


private static Task createEventTask(String[] splitComponents){
 String description = splitComponents[0];
 String startDateString = splitComponents[0];
 String endDateString = splitComponents[1];
 
 
LocalDateTime startDate = convertToDate(startDateString);
LocalDateTime endDate = convertToDate(endDateString);
 
return new Task(description,startDate, endDate);

}

private static LocalDateTime convertToDate(String strDate){
		
try {
		return LocalDateTime.parse(strDate, FORMAT_STORAGE_DATETIME);
	} catch (Exception e) {
		return null;
	}
}
		
private static String createCommands(Task task){
	    
		String description = task.getDescription();
		String startDateTime = task.getStartDateTimeInString();
		String endDateTime = task.getEndDateTimeInString();
		
		StringBuilder str = new StringBuilder();
		
		str.append(description);
		
		if(startDateTime != "") {
			str.append("&&");
			str.append(startDateTime);
			
		}
		
		if(endDateTime != "") {
			str.append("&&");
			str.append(endDateTime);
		}
		
		return str.toString();
}

public static void writeToFile(ArrayList<Task>  Tasks) {
		ArrayList<String> commands = new ArrayList<String>();
		
		for(int i=0;i< Tasks.size();i++){
			Task task = Tasks.get(i);
			String command = createCommands(task);
			commands.add(command);
		}

		try {
			FileWriter  fw = new FileWriter(fileName);
			
			BufferedWriter bw = new BufferedWriter(fw);
	
			for(int i=0 ; i<commands.size();i++){
				bw.write(commands.get(i));
				bw.newLine();
			}
			bw.close();
			}catch (IOException e) {
			System.out.println(e);
		}
	}
}








