package com.cs2013t143j.TaskBuddyM.Command;

import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

//@@author A0121327U
public class DeleteCommand implements Command {
	
	private String delIndex;
	private ArrayList<Task> deletedTasks = new ArrayList<Task>();
	
	private final String INFO = "Delete no.%s";
	
	private final String DELETE_OUTPUT = "Deleted task(s) %s\n";
	
	private final String ERROR_INT = "Index provided is not an Integer";
	private final String ERROR_NEGATIVE = "Index provided must be larger than 0";
	private final String ERROR_RANGE = "Index provided is larger than last display";

	public DeleteCommand(String _index) {
		delIndex = _index;
	}
	
	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		
		ArrayList<Task> tasks = new ArrayList<Task>();
		
		String deleted = new String();
		
		String[] indexList = delIndex.split(" ");
		
		int i = 0;
		int converted;
		
		for (i=0; i<indexList.length; ++i) {
			try{
				converted = Integer.parseInt(indexList[i]);
			
				if (converted > lastDisplay.size()) {
					throw new CommandAttributeError(ERROR_RANGE);
				} else if (converted <= 0) {
					throw new CommandAttributeError(ERROR_NEGATIVE);
				}
	
				deleted += String.valueOf(converted);
				
				if (i != indexList.length-1) {
					deleted += ", ";
				}
				
				tasks.add(lastDisplay.get(converted-1));
			} catch (NumberFormatException e) {
				throw new CommandAttributeError(ERROR_INT);
			}
		}
		
		for (i=0; i<tasks.size(); ++i) {
			Task taskToDelete = tasks.get(i);
			
			deletedTasks.add(taskToDelete);
			lastDisplay.remove(taskToDelete);
			
			ArrayList<Task> allTasks = sAccess.display();

			int storageIndex = allTasks.indexOf(taskToDelete);

			sAccess.delete(storageIndex);
		}

		String output = String.format(DELETE_OUTPUT, deleted);
		
		Command command = new DisplayLast();
		output += command.execute(lastDisplay, sAccess);
		
		return output;
	}
	
	public boolean isValid() {
		return true;
	}
	
	public void undo(StorageAccess sAccess) {
		
		int i = 0;
		
		for (i=0; i<deletedTasks.size(); ++i) {
			Task deletedTask = deletedTasks.get(i);
			
			sAccess.add(deletedTask);
		}
	}
	
	public void redo(StorageAccess sAccess) {
		
		int i = 0;
		
		for (i=0; i<deletedTasks.size(); ++i) {
			Task taskToDelete = deletedTasks.get(i);
			
			ArrayList<Task> allTasks = sAccess.display();

			int storageIndex = allTasks.indexOf(taskToDelete);

			sAccess.delete(storageIndex);
		}		
	}
	
	public String info() {
		String output = String.format(INFO, delIndex);
		
		return output;
	}
}
