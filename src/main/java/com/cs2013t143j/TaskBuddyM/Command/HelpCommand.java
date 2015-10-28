package com.cs2013t143j.TaskBuddyM.Command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

public class HelpCommand implements Command {
	
	private String command;
	
	private String addHelp = "addHelper.txt";
	private String dispHelp = "dispHelper.txt";
	private String delHelp = "delHelper.txt";
	private String searchHelp = "searchHelper.txt";
	private String editHelp = "editHelper.txt";
	
	private File fileToRead;
	
	private final String ERROR_COMMAND = "Invalid Help Command Specified";
	
	public HelpCommand(String _command) {
		command = _command;
	}

	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		String output = "\n";
		
		switch(command) {
		case "add":
			fileToRead = new File(addHelp);
			break;
		case "display":
			fileToRead = new File(dispHelp);
			break;
		case "delete":
			fileToRead = new File(delHelp);
			break;
		case "search":
			fileToRead = new File(searchHelp);
			break;
		case "edit":
			fileToRead = new File(editHelp);
			break;
		default:
			throw new CommandAttributeError(ERROR_COMMAND);
		}
		
		try{
		BufferedReader reader = new BufferedReader(new FileReader(fileToRead));
		String line = reader.readLine();
		while (line != null) {
		output += line;
		output += "\n";
		line = reader.readLine();
		}
		reader.close();
		} catch (FileNotFoundException ex) {
			//Should not happen unless user deletes all the text files
		} catch (IOException ex) {

		}
		
		output += "\n";
		
		return output;
		
	}
	
	
}