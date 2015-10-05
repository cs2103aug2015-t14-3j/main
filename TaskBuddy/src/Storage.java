import java.util.ArrayList;
import java.time.LocalDateTime;

public class Storage {

    private ArrayList<Task> tasks;
	
private void createTaskList() {
	tasks = new ArrayList<Task>();
	StorageIO.readFile(tasks);
}

public void add(Task newTask) {
	tasks.add(newTask);
	StorageIO.writeToFile(tasks); 
}

public void delete(int index) {
	tasks.remove(index);
	StorageIO.writeToFile(tasks);
}

public Task getTask(int index) {
	
	Task task = tasks.get(index);
		return task;
}

public void updateDescription(int index, String newdescription){
	Task task = tasks.get(index);
	task.setDescription(newdescription);
	tasks.add(index,task);
	StorageIO.writeToFile(tasks);
}

public void updateStartDate(int index,LocalDateTime newStartDate) {
	Task task = tasks.get(index);
	task.setStartDateTime(newStartDate);
	tasks.add(index,task);
	StorageIO.writeToFile(tasks);
}

public void updateEndDate(int index,LocalDateTime newEndDate) {
	Task task = tasks.get(index);
	task.setEndDateTime(newEndDate);
	tasks.add(index,task);
	
	StorageIO.writeToFile(tasks);
}
}
