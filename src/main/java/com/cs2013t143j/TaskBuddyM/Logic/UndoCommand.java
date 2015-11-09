package com.cs2013t143j.TaskBuddyM.Logic;

import java.lang.NullPointerException;
import com.cs2013t143j.TaskBuddyM.Storage.History;
import com.cs2013t143j.TaskBuddyM.Storage.StorageIO;
import com.cs2013t143j.TaskBuddyM.Storage.Task;
import com.cs2013t143j.TaskBuddyM.Storage.Storage;
import com.cs2013t143j.TaskBuddyM.Command.Command;
import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Logic.StackCommand;

import java.util.ArrayList;

//@@ author A0101794H
public class UndoCommand {
	private History history;
	private StorageAccess storage;
	private Task task  = new Task();
	StackCommand cmd;
	
	private ArrayList<Task> TaskList = new ArrayList<Task>();
	

	public UndoCommand(History history,StorageAccess storage) {
		this.history = history;
		this.storage = storage;
	}

	public String executeUndoAdd(ArrayList<Task> TaskList, StorageAccess storage)  {
	 String feedback  = "yes";
	 Task task = history.popUndoAdd();
	 
	 TaskList = storage.display();
	 int index = TaskList.size()-1;
	 storage.delete(index);
	 
	 storage.writeToFile();
	 
	 return feedback;
	}

	public String executeUndoDelete(ArrayList<Task> TaskList, StorageAccess storage){
	String feedback = "yes";
	StackCommand cmd;
	Task task;
	int index;
	
	cmd = history.popUndoDelete();
	task = cmd.getTask();
	index  = cmd.getIndex();
	
	TaskList = storage.display();
	TaskList.add(index,task); 
	
	storage.writeToFile();
	
	return feedback;
	}

	public String executeUndoEdit(ArrayList<Task> TaskList, StorageAccess storage){
	String feedback = "yes";
	StackCommand cmd;
	
	cmd = history.popUndoEdit();
	Task task = cmd.getTask();
	int index = cmd.getIndex();
	
	TaskList = storage.display();
	TaskList.add(index,task);
	TaskList.remove(index+1);
	
	storage.writeToFile();
	
	return feedback;
	}
}
