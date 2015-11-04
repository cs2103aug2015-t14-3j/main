package com.cs2013t143j.TaskBuddyM.Logic;
import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;




public class ClearCommand {
	private StorageAccess storage;
	

	private final String CLEAR_OUTPUT = "Deleted all";
	
	public ClearCommand(StorageAccess storage) {
		this.storage = storage;
	}
	
	
public String execute(){
	
	storage.clear();
	
	String output = String.format(CLEAR_OUTPUT);
	return output;
    }
	
	
	
	
}

