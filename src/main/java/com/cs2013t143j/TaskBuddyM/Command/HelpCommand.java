package com.cs2013t143j.TaskBuddyM.Command;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.cs2013t143j.TaskBuddyM.Logic.StorageAccess;
import com.cs2013t143j.TaskBuddyM.Storage.Task;

//@@author A0121327U
public class HelpCommand implements Command {
	
	private String command;
	
	private String addHelp = "addHelper.txt";
	private String dispHelp = "dispHelper.txt";
	private String delHelp = "delHelper.txt";
	private String searchHelp = "searchHelper.txt";
	private String editHelp = "editHelper.txt";
	private String doneHelp = "doneHelper.txt";
	
	private final String INFO = "Help";
		
	private final String HELP_DEFAULT = "You can get help for the following commands: add, display, delete, search, done, edit";
	private final String ERROR_OPEN = "Cannot open help file";
	private final String HELP_OUT = "Here's the help file for Command: %s\n";
	private final String tempFile = "HELP";
	
	public HelpCommand(String _command) {
		command = _command;
	}

	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) throws CommandAttributeError {
		String output = new String();
		
		String file;
		
		switch(command) {
		case "add":
			output = String.format(HELP_OUT, "ADD");
			file = addHelp;
			break;
		case "display":
			output = String.format(HELP_OUT, "DISPLAY");
			file = dispHelp;
			break;
		case "delete":
			output = String.format(HELP_OUT, "DELETE");
			file = delHelp;
			break;
		case "search":
			output = String.format(HELP_OUT, "SEARCH");
			file = searchHelp;
			break;
		case "done":
			output = String.format(HELP_OUT, "DONE");
			file = doneHelp;
			break;
		case "edit":
			output = String.format(HELP_OUT, "EDIT");
			file = editHelp;
			break;
		default:
			return HELP_DEFAULT;
		}
			
		try{
			openFile(file);
		} catch (IOException e) {
			throw new CommandAttributeError(ERROR_OPEN);
		}
		
		Command command = new DisplayLast();
		output += command.execute(lastDisplay, sAccess);
		
		return output;		
	}
	
	public boolean isValid() {
		return true;
	}

	private void openFile(String fileName) throws IOException {
		InputStream is = getClass().getResourceAsStream(fileName);
		
		byte[] data = new byte[is.available()];
		is.read(data);
		is.close();
		
		File temp = File.createTempFile(tempFile, ".txt");
		FileOutputStream fos = new FileOutputStream(temp);
		
		fos.write(data);
		fos.flush();
		fos.close();
		
		Desktop.getDesktop().open(temp);
	}
	
	public void undo(StorageAccess sAccess) {
		return;
	}

	public String info() {
		String output = INFO;
		
		return output;
	}
	
	public void redo(StorageAccess sAccess) {
		return;
	}
}