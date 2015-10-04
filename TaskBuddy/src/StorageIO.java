import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.Boolean;
import java.lang.Object;
import java.lang.String;

public class StorageIO {
	public static final DateTimeFormatter FORMAT_STORAGE_DATETIME = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	public static String fileName;
	private static List<String>  commands;
	private  static ArrayList<Task> Tasks;

	public static void readFile() {
		try {
			String command;
			File file = new File(fileName);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			commands.clear();

			while ((command = br.readLine()) != null) {
				commands.add(command);
			}

			for(int i=0; i< commands.size();i++) {
				createTask(commands.get(i));

			}
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}


		br.close();
		fr.close();


	}


	public static Task createTask(String command){

		String description = "";
		String startDateString = "";
		String endDateString = "";
		String done = "";


		String[] splitComponents = command.split("");


		description = splitComponents[0];
		startDateString = splitComponents[1];
		endDateString = splitComponents[2];
		done = splitComponents[3];

		LocalDateTime startDate = convertToDate(startDateString);
		LocalDateTime endDate = convertToDate(endDateString);


		Task task = new Task(description,startDate,endDate);
		return task;
	}

	private static LocalDateTime convertToDate(String strDate){
		return LocalDateTime.parse(strDate, FORMAT_STORAGE_DATETIME);
	}


	private static String createCommands(Task task) {

		commands =  new ArrayList<String>();


		String description = task.getDescription();
		commands.add(description);

		String startDateTime = task.getStartDateTimeInString();
		commands.add(startDateTime);

		String endDateTime = task.getEndDateTimeInString();
		commands.add(endDateTime);

		String done = task.isDone() + "";
		commands.add(done);

		for(int i=0; i< commands.size();i++){
			commands.get(i);
		}

		return description;
	}

	public static void writeToFile() {


		try { 
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);


			for(int i= 0; i<Tasks.size();i++){
				createCommands(Tasks.get(i));
			}

			for(int i=0 ; i<commands.size();i++){
				bw.write(commands.get(i));
			}



			bw.close();
			fw.close();
		}
		catch (Exception e) {

			e.printStackTrace();
		}


	}

}








