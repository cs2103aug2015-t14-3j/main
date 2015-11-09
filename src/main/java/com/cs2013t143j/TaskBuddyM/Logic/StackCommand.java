package com.cs2013t143j.TaskBuddyM.Logic;

//@@ author A0101794H
import com.cs2013t143j.TaskBuddyM.Storage.Task;

	public class StackCommand {
	private int index;
	private Task task;
	
	public StackCommand(int index, Task task) {
		this.index = index;
		this.task = task;
	}
	
	public int getIndex() {
		return index;
	}

	public Task getTask() {
	return task;
	}
}