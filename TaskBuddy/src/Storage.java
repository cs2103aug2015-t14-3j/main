import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.time.LocalDateTime;



public class Storage {

	private ArrayList<Task> tasks;
	private String dataFile;
	
	public Storage(String dataFile) {
		this.dataFile = dataFile;
	}

	private void createTaskList() {
		tasks = new ArrayList<Task>();
		StorageIO.readFile();
	}

	public void add(Task newTask) {
		tasks.add(newTask);
		StorageIO.writeToFile(); 
	}

	public void delete(int index) {
		tasks.remove(index);
		StorageIO.writeToFile();
	}

	public Task getTask(int index) {
		Task task = tasks.get(index);
		return task;

	}

	public void updateDescription(int index, String newdescription){
		Task task = tasks.get(index);
		task.setDescription(newdescription);
		tasks.add(index,task);
		StorageIO.writeToFile();
	}

	public void updateStartDate(int index,LocalDateTime newStartDate) {
		Task task = tasks.get(index);
		task.setStartDateTime(newStartDate);
		tasks.add(index,task);
		StorageIO.writeToFile();
	}

	public void updateEndDate(int index,LocalDateTime newEndDate) {
		Task task = tasks.get(index);
		task.setEndDateTime(newEndDate);
		tasks.add(index,task);

		StorageIO.writeToFile();
	}




}
