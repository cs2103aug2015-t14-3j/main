import java.time.LocalDateTime;
import java.util.ArrayList;

public class StorageStub extends Storage {
	
	private static ArrayList<Task> tasks = new ArrayList<Task>();
	
	public void add(Task newTask) {
		tasks.add(newTask);
	}
	
	public void delete(int index) {
		tasks.remove(index);
	}
	
	public ArrayList<Task> display() {
		return tasks;
	}
	
	public void updateDescription(int index, String newdescription){
		Task task = tasks.get(index);
		task.setDescription(newdescription);
	}

	public void updateStartDate(int index,LocalDateTime newStartDate) {
		Task task = tasks.get(index);
		task.setStartDateTime(newStartDate);
	}

	public void updateEndDate(int index,LocalDateTime newEndDate) {
		Task task = tasks.get(index);
		task.setEndDateTime(newEndDate);
	}
	
	

}
