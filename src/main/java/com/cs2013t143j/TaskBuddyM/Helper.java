package com.cs2013t143j.TaskBuddyM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Helper {
	
	private String addHelp = "addHelper.txt";
	private String dispHelp = "dispHelper.txt";
	private String delHelp = "delHelper.txt";
	private String searchHelp = "searchHelper.txt";
	private String editHelp = "editHelper.txt";
	
	private File fileToRead;
	
	private final String INVALID_COMMAND = "Invalid Commmand Specified\n\n";
	
	public String help(String command) {
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
			output += INVALID_COMMAND;
			return output;
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
