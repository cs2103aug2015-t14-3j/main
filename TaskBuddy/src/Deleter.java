import java.util.ArrayList;
import java.util.Map;

public class Deleter {

	private Storage storage;
	
	private final String DELETE_INDEX = "index";
	
	public Deleter(Storage storage) {
		this.storage = storage;
	}
	
	public String delete(Map<String,String> parsedCommand, ArrayList<Task> tasks) {
		int index = Integer.parseInt(parsedCommand.get(DELETE_INDEX)) - 1;
		
		Task taskToDelete = tasks.get(index);
		
//		storage.delete(taskToDelete.getIndex());
		
		return "command: delete\n";
	}
}