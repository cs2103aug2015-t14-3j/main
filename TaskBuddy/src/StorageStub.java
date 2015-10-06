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
	
	

}
