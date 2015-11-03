package com.cs2013t143j.TaskBuddyM.Storage;
import com.cs2013t143j.TaskBuddyM.Command.Command;
import com.cs2013t143j.TaskBuddyM.Logic.StackCommand;
import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Map;
import java.lang.NullPointerException;

public class History {
	private StorageAccess storage;
	public static Stack<StackCommand> undoStack  = new Stack<StackCommand>();
	public ArrayList<Task> TaskList = new ArrayList<Task>();
	
	
public History(StorageAccess storage) {
	this.storage =storage;
}
	
public void pushUndoDelete(Task task,int index){
	undoStack.push(new StackCommand(index,task));
}

public void pushUndoEdit(Task task,int index) {
	undoStack.push(new StackCommand(index,task));
}

public void pushUndoAdd(){
	TaskList = storage.display();
	int index = TaskList.size()-1;
	Task task = TaskList.get(index);
	undoStack.push(new StackCommand(index,task));
}


public void pushRedoAdd() {
	TaskList = storage.display();
	int index = TaskList.size() - 1;
	Task task = TaskList.get(index);
	undoStack.push(new StackCommand(index,task));
}

//public void pushRedoEdit() {
	
//}

//public void pushRedoDelete(Task task, int index) {
	//undoStack.push(new StackCommand(index,task));
//}

//public StackCommand popRedoDelete() {
	//StackCommand lastCmd = undoStack.pop();
	
	//return lastCmd;
	
//}

public StackCommand popRedoAdd() {
	StackCommand lastCmd = undoStack.pop();
	
	return lastCmd;
}

public StackCommand popUndoEdit() {
	
	StackCommand lastCmd = undoStack.pop();
	
	return lastCmd;
}

public Task popUndoAdd() {
		Task task = new Task();
		StackCommand  lastCmd = undoStack.pop();
		task = lastCmd.getTask();
		
		return task;
}

public StackCommand popUndoDelete() {
		StackCommand lastCmd = undoStack.pop();
		
		return lastCmd;
}
}
	
	
	
	
	

	

	
