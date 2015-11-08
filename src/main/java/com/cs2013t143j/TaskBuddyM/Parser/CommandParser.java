/* Types of command (shortcut): 
 * 1. add/create/insert		(a/c/i)
 * 2. delete/remove			(del/r)
 * 3. display/show			(d)
 * 4. edit/update			(e)
 * 5. search				(s)
 * 6. undo					(u)
 * 7. help					(h)
 * 8. done					(f)
 * 9. redo					(z)
 * 10. clear				(x)
 */

package com.cs2013t143j.TaskBuddyM.Parser;
import java.util.Map;

public class CommandParser {
	
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_CLEAR = "clear";
	private static final String COMMAND_CREATE = "create";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_DISPLAY = "display";
	private static final String COMMAND_DONE = "done";
	private static final String COMMAND_OVERDUE = "overdue";
	private static final String COMMAND_MONTH = "month";
	private static final String COMMAND_WEEK = "week";
	private static final String COMMAND_EDIT = "edit";
	private static final String COMMAND_HELP = "help";
	private static final String COMMAND_INSERT = "insert";
	private static final String COMMAND_REMOVE = "remove";
	private static final String COMMAND_SEARCH= "search";
	private static final String COMMAND_SHOW = "show";
	private static final String COMMAND_UPDATE = "update";
	private static final String COMMAND_UNDO = "undo";
	private static final String COMMAND_REDO = "redo";

	private static final String DIC_COMMAND = "command";
	private static final String DIC_SUBCOMMAND = "subCommand";
	
	private static final String DISPLAY_SUBCOMMAND_ON = "on";
	private static final String DISPLAY_SUBCOMMAND_FROM = "from";
	private static final String DISPLAY_SUBCOMMAND_AFTER = "after";
	private static final String DISPLAY_SUBCOMMAND_DUE = "due";
	private static final String DISPLAY_SUBCOMMAND_DONE = "done";
	private static final String DISPLAY_SUBCOMMAND_OVERDUE = "overdue";
	private static final String DISPLAY_SUBCOMMAND_MONTH = "month";
	private static final String DISPLAY_SUBCOMMAND_WEEK = "week";
	private static final String DISPLAY_SUBCOMMAND_FLOATING = "floating";
	private static final String DISPLAY_SUBCOMMAND_RANGE = "range";
	
	private static final String ERROR_COMMAND = "Invalid command";
	
	private String userInput=null;
	private String[] arr;
	private int index=-1;
	
	public CommandParser (String str) {
		userInput = str;
		arr = userInput.split(" ");
	}
	
	public void extractShortcutCommand(Map<String,String> dictionary) throws InvalidInputException {	
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
		} else if (arr[0].equalsIgnoreCase("z")) {
			dictionary.put(DIC_COMMAND,COMMAND_REDO);
		} else if (arr[0].equalsIgnoreCase("x")) {
			dictionary.put(DIC_COMMAND,COMMAND_CLEAR);
		} else if (arr[0].equalsIgnoreCase("f")) {
			dictionary.put(DIC_COMMAND,COMMAND_DONE);
		} else {
			extractCommand(dictionary);
		}
	}
	
	public void extractCommand(Map<String,String> dictionary) throws InvalidInputException {
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
			}  else if (arr[i].equalsIgnoreCase(COMMAND_EDIT) || arr[i].equalsIgnoreCase(COMMAND_UPDATE)) {
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
			} else if (arr[i].equalsIgnoreCase(COMMAND_DONE)) {
				dictionary.put(DIC_COMMAND,COMMAND_DONE);
				break;
			}else if (arr[i].equalsIgnoreCase(COMMAND_OVERDUE)){
				dictionary.put(DIC_COMMAND,COMMAND_OVERDUE);
			} else if (arr[i].equalsIgnoreCase(COMMAND_WEEK)){
				dictionary.put(DIC_COMMAND,COMMAND_WEEK);
			} else if (arr[i].equalsIgnoreCase(COMMAND_MONTH)){
				dictionary.put(DIC_COMMAND,COMMAND_MONTH);
			} else if (arr[i].equalsIgnoreCase(COMMAND_REDO)) {
				dictionary.put(DIC_COMMAND,COMMAND_REDO);
				break;
			} else if (arr[i].equalsIgnoreCase(COMMAND_CLEAR)) {
				dictionary.put(DIC_COMMAND,COMMAND_CLEAR);
				break;
			}else if (i == arr.length - 1) {
				dictionary.put(DIC_COMMAND,null);
				throw new InvalidInputException(ERROR_COMMAND);
			}
		}
	}
	
	public void extractSubCommand(Map<String,String> dictionary) {
		String command = dictionary.get(DIC_COMMAND);
		
		if (index < arr.length) {
			if (command.equals(COMMAND_DISPLAY)) {
				switch (arr[index].toLowerCase()) {
				case DISPLAY_SUBCOMMAND_ON:
				case DISPLAY_SUBCOMMAND_FROM:
				case DISPLAY_SUBCOMMAND_AFTER:
				case DISPLAY_SUBCOMMAND_DUE:
				case DISPLAY_SUBCOMMAND_DONE:
				case DISPLAY_SUBCOMMAND_OVERDUE:
				case DISPLAY_SUBCOMMAND_MONTH:
				case DISPLAY_SUBCOMMAND_WEEK:
				case DISPLAY_SUBCOMMAND_FLOATING:
				case DISPLAY_SUBCOMMAND_RANGE:
					dictionary.put(DIC_SUBCOMMAND, arr[index]);
					userInput = removeWord(arr[index]);
					break;
				default:
					dictionary.put(DIC_SUBCOMMAND, null);
					break;
				}
			}
		}
	}
	
	public String removeWord(String word) {
		assert word != "";
		String regex = "\\s*\\b" + word + "\\b";
		String temp = userInput.replaceAll(regex,"").trim();
		if(temp.equals(userInput)) {
			temp = removeShortcutCommand();
		}
		userInput = temp;
		return temp;
	}

	private String removeShortcutCommand() {
		if(userInput.length() == 1) {
			return "";
		} else {
			int whiteSpaceIndex = userInput.indexOf(" ");
			return userInput.substring(++whiteSpaceIndex);
		}
	}
}
