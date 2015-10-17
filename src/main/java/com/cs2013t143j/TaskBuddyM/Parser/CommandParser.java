/* Types of command (shortcut): 
 * 1. add/create/insert		(a/c/i)
 * 2. delete/remove			(del/r)
 * 3. display/show			(d)
 * 4. edit/update			(e)
 * 5. search				(s)
 * 6. undo					(u)
 * 7. help					(h)
 */

package com.cs2013t143j.TaskBuddyM.Parser;
import java.util.Map;

public class CommandParser {
	
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_CREATE = "create";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_DISPLAY = "display";
	private static final String COMMAND_EDIT = "edit";
	private static final String COMMAND_HELP = "help";
	private static final String COMMAND_INSERT = "insert";
	private static final String COMMAND_REMOVE = "remove";
	private static final String COMMAND_SEARCH= "search";
	private static final String COMMAND_SHOW = "show";
	private static final String COMMAND_UPDATE = "update";
	private static final String COMMAND_UNDO = "undo";

	private static final String DIC_COMMAND = "command";
	private static final String DIC_SUBCOMMAND = "subCommand";
	
	private static final String INVALID_COMMAND = "Invalid command entered.";
	
	private String userInput=null;
	private String[] arr;
	private int index=-1;
	
	public CommandParser (String str) {
		userInput = str;
		arr = userInput.split(" ");
	}
	
	public void extractShortcutCommand(Map<String,String> dictionary) {	
		if (arr[0].equalsIgnoreCase("a") || arr[0].equalsIgnoreCase("c") || arr[0].equalsIgnoreCase("i")) {
			dictionary.put(DIC_COMMAND, COMMAND_ADD);
		} else if (arr[0].equalsIgnoreCase("del") || arr[0].equalsIgnoreCase("r")) {
			dictionary.put(DIC_COMMAND, COMMAND_DELETE);
		} else if (arr[0].equalsIgnoreCase("d")) {
			dictionary.put(DIC_COMMAND, COMMAND_DISPLAY);
			index = 1;
		} else if (arr[0].equalsIgnoreCase("e")) {
			dictionary.put(DIC_COMMAND,COMMAND_EDIT);
		} else if (arr[0].equalsIgnoreCase("s")) {
			dictionary.put(DIC_COMMAND, COMMAND_SEARCH);
		} else if (arr[0].equalsIgnoreCase("u")) {
			dictionary.put(DIC_COMMAND, COMMAND_UNDO);
		} else if (arr[0].equalsIgnoreCase("h")) {
			dictionary.put(DIC_COMMAND, COMMAND_HELP);
		} else {
			extractCommand(dictionary);
		}
	}
	
	private void extractCommand(Map<String,String> dictionary) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equalsIgnoreCase(COMMAND_ADD) || arr[i].equalsIgnoreCase(COMMAND_CREATE) || arr[i].equalsIgnoreCase(COMMAND_INSERT)) {
				dictionary.put(DIC_COMMAND, COMMAND_ADD);
				break;
			} else if (arr[i].equalsIgnoreCase(COMMAND_DELETE) || arr[i].equalsIgnoreCase(COMMAND_REMOVE)) {
				dictionary.put(DIC_COMMAND, COMMAND_DELETE);
				break;
			} else if (arr[i].equalsIgnoreCase(COMMAND_DISPLAY) || arr[i].equalsIgnoreCase(COMMAND_SHOW)) {
				dictionary.put(DIC_COMMAND, COMMAND_DISPLAY);
				index = ++i;
				break;
			} else if (arr[i].equalsIgnoreCase(COMMAND_EDIT) || arr[i].equalsIgnoreCase(COMMAND_UPDATE)) {
				dictionary.put(DIC_COMMAND, COMMAND_EDIT);
				break;
			} else if (arr[i].equalsIgnoreCase(COMMAND_SEARCH)) {
				dictionary.put(DIC_COMMAND, COMMAND_SEARCH);
				break;
			} else if (arr[i].equalsIgnoreCase(COMMAND_UNDO)) {
				dictionary.put(DIC_COMMAND, COMMAND_UNDO);
				break;
			} else if (arr[i].equalsIgnoreCase(COMMAND_HELP)) {
				dictionary.put(DIC_COMMAND, COMMAND_HELP);
				break;
			} else if (i == arr.length - 1){
				//sthrow new InvalidInputException(INVALID_COMMAND);
			}
		}
	}
	
	public void extractSubCommand(Map<String,String> dictionary) {
		String command = dictionary.get(DIC_COMMAND);
		
		if (index < arr.length) {
			if (command.equals(COMMAND_DISPLAY)) {
				switch (arr[index].toLowerCase()) {
				case "on":
				case "from":
				case "after":
				case "due":
				case "incomplete":
				case "floating":
					dictionary.put(DIC_SUBCOMMAND, arr[index]);
					break;
				default:
					dictionary.put(DIC_SUBCOMMAND, "color");
				}
			}
		}
	}
	
	public String removeCommand(String command) {
		String regex = "\\s*\\b" + command + "\\b";
		return userInput.replaceAll(regex,"").trim();
	}

}
