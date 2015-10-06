import java.util.*;

// Things to note: 
// 1) First word of userInput should be command
// 2) Commands: add,insert,create,delete,remove,display,show,edit,search,undo,help
// 3) For add and edit, user will have to include '-' before every keywords
//		e.g. add -submit report -by 25 Sep 2015
//		e.g. edit -2 -description -submit CS2010 report
// 4) date format return to logic will be in the following format: HHmm dd/MM/yyyy

public class Parser {
	
	private CommandMatch cmd;
	private Content content;

	private String userInput;

	public Parser() {
	}
	
	Map<String, String> getDictionary (String input) {
		Map<String, String> dictionary = new HashMap<String,String>();
		userInput = input.toLowerCase();
		
		String command = retrieveCommand(dictionary);
		retrieveContent(dictionary, command);
		return dictionary;
	}

	private void retrieveContent(Map<String, String> dictionary, String command) {
		if (!userInput.equals(null)) {
			content = new Content(userInput,command);
			content.extractContent(dictionary);
		}
	}

	private String retrieveCommand(Map<String, String> dictionary) {
		cmd = new CommandMatch(userInput);
		String command = cmd.extractCommand(dictionary);
		userInput = cmd.removeCommand(command);
		return command;
	} 
}
