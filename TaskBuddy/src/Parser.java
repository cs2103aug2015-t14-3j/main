import java.util.*;

// Things to note: First word of userInput should be command
// command: add,insert,create,delete,remove,display,show,edit,search,undo,help

public class Parser {
	
	CommandMatch cmd;
	Content content;
	
	
	String userInput;

	public Parser() {
	}
	
	Map<String, String> getDictionary (String input) {
		Map<String, String> dictionary = new HashMap<String,String>();
		userInput = input.toLowerCase();
		
		cmd = new CommandMatch(userInput);
		String command = cmd.extractCommand(dictionary);
		userInput = cmd.removeCommand(command);
		
		if (!userInput.equals(null)) {
			content = new Content(userInput,command);
			content.extractContent(dictionary);
		}
		return dictionary;
	} 
}
