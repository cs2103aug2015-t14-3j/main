import java.util.ArrayList;
import java.time.LocalDateTime;

public class Storage {

private ArrayList<Task> tasks = new ArrayList<Task>();

private Storage() {
	tasks = StorageIO.readFile();
}
	
public void add(Task newTask) {
	tasks.add(newTask);
	writeToFile(); 
}

public void delete(int index) {
	tasks.remove(index);
	writeToFile();
}

public Task getTask(int index) {
	
	Task task = tasks.get(index);
		return task;
}

public void updateDescription(int index, String newdescription){
	Task task = tasks.get(index);
	task.setDescription(newdescription);
	tasks.add(index,task);
	writeToFile();
}

public void updateStartDate(int index,LocalDateTime newStartDate) {
	Task task = tasks.get(index);
	task.setStartDateTime(newStartDate);
	tasks.add(index,task);
	writeToFile();
}

public void updateEndDate(int index,LocalDateTime newEndDate) {
	Task task = tasks.get(index);
	task.setEndDateTime(newEndDate);
	tasks.add(index,task);
	
	writeToFile();
}

public ArrayList<Task> display() {
	return tasks;
}

public void writeToFile() {
	StorageIO.writeToFile(tasks);
}
}
