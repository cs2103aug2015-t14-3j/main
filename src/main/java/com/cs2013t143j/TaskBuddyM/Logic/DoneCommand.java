package com.cs2013t143j.TaskBuddyM.Logic;
import java.util.Map;
import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;




public class DoneCommand {
	private StorageAccess storage;
	
	private final String DONE_INDEX = "index";
	private final String DONE_OUTPUT = "Done task no.%d\n\n";
	
	public DoneCommand(StorageAccess storage) {
		this.storage = storage;
	}
	
	
public String execute(Map<String,String> parsedCommand){
	
	int index = Integer.parseInt(parsedCommand.get(DONE_INDEX));	
	
	storage.done(index);
	
	String output = String.format(DONE_OUTPUT, index);
	return output;
    }
	
	
	
	
}
