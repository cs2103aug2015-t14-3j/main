import java.util.ArrayList;
import java.util.Map;

public class Deleter {

	private Storage storage;
	
	private final String DELETE_INDEX = "index";
	private final String DELETE_OUTPUT = "Deleted task no.%d\n";
	
	public Deleter(Storage storage) {
		this.storage = storage;
	}
	
	public String delete(Map<String,String> parsedCommand, ArrayList<Task> lastDisplay) {
		int index = Integer.parseInt(parsedCommand.get(DELETE_INDEX));
		
		Task taskToDelete = lastDisplay.get(index-1);
		ArrayList<Task> allTasks = storage.display();
		
		int storageIndex = allTasks.indexOf(taskToDelete);
		
		storage.delete(storageIndex);
		
		String output = String.format(DELETE_OUTPUT, index);
		return output;
	}
}
