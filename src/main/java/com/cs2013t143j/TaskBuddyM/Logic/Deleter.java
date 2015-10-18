package com.cs2013t143j.TaskBuddyM.Logic;
import java.util.ArrayList;
import java.util.Map;
import com.cs2013t143j.TaskBuddyM.Storage.Task;
public class Deleter {

	private StorageAccess storage;
	
	private final String DELETE_INDEX = "index";
	private final String DELETE_OUTPUT = "Deleted task no.%d\n\n";
	private final String INVALID_INDEX = "Invalid Index specified\n\n";
	
	public Deleter(StorageAccess storage) {
		this.storage = storage;
	}
	
	public String delete(Map<String,String> parsedCommand, ArrayList<Task> lastDisplay) {
		int index = Integer.parseInt(parsedCommand.get(DELETE_INDEX));
		
		if (index > lastDisplay.size()){
			return INVALID_INDEX;
		}
		
		Task taskToDelete = lastDisplay.get(index-1);
		ArrayList<Task> allTasks = storage.display();
		
		int storageIndex = allTasks.indexOf(taskToDelete);
		
		storage.delete(storageIndex);
		
		String output = String.format(DELETE_OUTPUT, index);
		return output;
	}
}
