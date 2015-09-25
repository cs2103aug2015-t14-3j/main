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
		extractContent(command, dictionary);
		return dictionary;
	}

	private String extractCommand(Map<String,String> dictionary) {
		String command="";
		if (userInput.contains("add")) {
			command = "add";
			dictionary.put("command", "add");
		} else if (userInput.contains("delete")) {
			command = "delete";
			dictionary.put("command", "delete");
		} else if (userInput.contains("display on")) {
			command = "display on";
			dictionary.put("command", "display on");
		} else if (userInput.contains("display")) {
			command = "display";
			dictionary.put("command", "display");
		} else if (userInput.contains("edit")) {
			command = "edit";
			dictionary.put("command", "edit");
		} else if (userInput.contains("search")) {
			command = "search";
			dictionary.put("command", "search");
		} else if (userInput.contains("undo")) {
			command = "undo";
			dictionary.put("command", "undo");
		} else if (userInput.contains("help")) {
			command = "help";
			dictionary.put("command", "help");
		} else {
			System.out.println(ERROR_COMMAND);
		}
		return command;
	} 
	
	private void extractContent(String command, Map<String,String> dictionary) {
		// code
		if (command.equals("add")) {
		}
	}
}
