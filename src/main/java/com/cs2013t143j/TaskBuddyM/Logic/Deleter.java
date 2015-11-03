package com.cs2013t143j.TaskBuddyM.Logic;
import java.util.ArrayList;

import java.util.Map;
import com.cs2013t143j.TaskBuddyM.Storage.Task;
import com.cs2013t143j.TaskBuddyM.Storage.History;
public class Deleter {

	private StorageAccess storage;
	private History history;
	
	private final String DELETE_INDEX = "index";
	private final String DELETE_OUTPUT = "Deleted task no.%d\n\n";
	private final String INVALID_INDEX = "Invalid Index specified\n\n";
	
	public Deleter(StorageAccess storage,History history) {
		this.storage = storage;
		this.history = history;
	}
	
	public String delete(Map<String,String> parsedCommand, ArrayList<Task> lastDisplay) throws ParserContentError {
		
		String delIndex = parsedCommand.get(DELETE_INDEX);
		
		if (delIndex == null) {
			throw new ParserContentError("Delete Index is null");
		}
		
		int index = -1;
		try	{
			index = Integer.parseInt(parsedCommand.get(DELETE_INDEX));
		} catch (NumberFormatException e) {
			throw new ParserContentError("Integer cannot be parsed");
		}
		
		if (index > lastDisplay.size()){
			return INVALID_INDEX;
		}
		
		Task taskToDelete = lastDisplay.get(index-1);
		ArrayList<Task> allTasks = storage.display();
		
		int storageIndex = allTasks.indexOf(taskToDelete);
		
		storage.delete(storageIndex);
		//history.pushRedoDelete(taskToDelete,storageIndex);
		history.pushUndoDelete(taskToDelete,storageIndex);
		
		
		String output = String.format(DELETE_OUTPUT, index);
		return output;
	}
}
