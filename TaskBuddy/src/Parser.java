import java.util.*;

public class Parser {
	
	
	final String ERROR_COMMAND = "Invalid User Command.";
	String userInput;

	public Parser() {		
	}
	
	Map<String, String> getDictionary (String input) {
		Map<String, String> dictionary = new HashMap<String,String>();
		
		userInput = input;
		String command = extractCommand(dictionary);
		removeCommand(command);
		extractContent(command, dictionary);
		return dictionary;
	}

	private String extractCommand(Map<String,String> dictionary) {
		String command = "";
		if (userInput.contains("add")) {
			command = "add";
			dictionary.put("command", "add");
			extractAddContent(dictionary);
		} else if (userInput.contains("delete")) {
			command = "delete";
			dictionary.put("command", "delete");
			extractDeleteContent(dictionary);
		} else if (userInput.contains("display")) {
			command = "display";
			dictionary.put("command", "display");
			extractDisplayContent(dictionary);
		} else if (userInput.contains("edit")) {
			command = "edit";
			dictionary.put("command", "edit");
			extractEditContent(dictionary);
		} else if (userInput.contains("search")) {
			command = "search";
			dictionary.put("command", "search");
			extractSearchContent(dictionary);
		} else if (userInput.contains("undo")) {
			command = "undo";
			dictionary.put("command", "undo");
		} else if (userInput.contains("help")) {
			command = "help";
			dictionary.put("command", "help");
		} 
		return command;
	} 
	
	public void removeCommand(String command) {
		userInput.replace(command, "");
	}
	
	public void extractContent(String command, Map<String,String> dictionary) {
		switch (command) {
		case "add":
			extractAddContent(dictionary);
		case "delete":
			extractDeleteContent(dictionary);
		case "display":
			extractDisplayContent(dictionary);
		case "edit":
			extractEditContent(dictionary);
		case "search":
			extractSearchContent(dictionary);
		default:
			System.out.println(ERROR_COMMAND);
		}
	}
	
	private void extractAddContent(Map<String,String> dictionary) {
		// code
		if (userInput.contains("from")) {
			// code
			dictionary.put("description", userInput.substring(0,userInput.indexOf("from")));
			dictionary.put("startDate", 
					userInput.substring(userInput.indexOf("to")+1, userInput.length()));
		} else if (userInput.contains("by")) {
			// code
			dictionary.put("description", userInput.substring(0,userInput.indexOf("by")));
		} else {
			dictionary.put("description",userInput);
			dictionary.put("startDate", null);
			dictionary.put("endDate", null);
		}
	}
	
	private void extractDeleteContent(Map<String,String> dictionary) {
		dictionary.put("index", userInput);
	}
	
	private void extractDisplayContent(Map<String,String> dictionary) {
		
		int whiteSpaceIndex = userInput.indexOf(" ");
		
		if (whiteSpaceIndex == -1) {
			if (userInput.equals("incomplete")) {
				dictionary.put("subCommand", "incomplete");
			} else if (userInput.equals("floating")) {
				dictionary.put("subCommnad", "floating");
			} else {
				dictionary.put("subCommand", "color");
				dictionary.put("color", userInput);
			}
		} else {
			dictionary.put("subCommand", userInput.substring(0,whiteSpaceIndex));
			dictionary.put("date", userInput.substring(whiteSpaceIndex+1,userInput.length()));
		}	
	}
	
	private  void extractEditContent(Map<String,String> dictionary) {
		
		int whiteSpaceIndex = userInput.indexOf(" ");
		
		dictionary.put("field", userInput.substring(0,whiteSpaceIndex));
		dictionary.put("newValue", 
				userInput.substring(whiteSpaceIndex+1,userInput.length()));
	}
	
	private  void extractSearchContent(Map<String,String> dictionary) {
		dictionary.put("searchKey", userInput);
	}
}
