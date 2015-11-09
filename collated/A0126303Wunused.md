# A0126303Wunused
###### src\main\java\com\cs2013t143j\TaskBuddyM\Parser\Content.java
``` java
// Moved to ContentParser

public class Content {

	private static final int ARR_SIZE_EVENT = 3;
	private static final int ARR_SIZE_DEADLINES = 2;
	
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_EDIT = "edit";
	private static final String COMMAND_DISPLAY = "display";
	private static final String COMMAND_HELP = "help";
	
	private static final String DICTIONARY_ADD_START = "startDate";
	private static final String DICTIONARY_ADD_END = "endDate";
	private static final String DICTIONARY_ADD_DESC = "description";
	
	private static final String ADD_KEYWORD_EVENT_START = "from";
	private static final String ADD_KEYWORD_EVENT_END = "to";
	private static final String ADD_KEYWORD_DEADLINE_END = "by";
	
	private static final String DELETE_KEYWORD = "index";
	
	private static final String HELP_KEYWORD = "target";
	
	private static final String DICTIONARY_DISPLAY_SUB = "subCommand";
	private static final String DICTIONARY_DISPLAY_COLOR = "color";
	private static final String DICTIONARY_DISPLAY_DATE = "date";
	private static final String DISPLAY_KEYWORD_ON_DATE = "on";
	private static final String DISPLAY_KEYWORD_FROM_DATE = "from";
	private static final String DISPLAY_KEYWORD_AFTER_DATE = "after";
	private static final String DISPLAY_KEYWORD_DUE_DATE = "due";
	private static final String DISPLAY_KEYWORD_INCOMPLETE = "incomplete";
	private static final String DISPLAY_KEYWORD_FLOATING = "floating";
	
	private static final String EDIT_KEYWORD_DESC = "description";
	private static final String EDIT_KEYWORD_START = "start date";
	private static final String EDIT_KEYWORD_END = "end date";
	private static final String DICTIONARY_EDIT_FIELD = "field";
	private static final String DICTIONARY_EDIT_INDEX = "index";
	private static final String DICTIONARY_EDIT_NEW = "newValue";
	
	private static final String DICTIONARY_SEARCH = "searchKey";
	
	
	private String[] arr;
	private String command;
	private String remainingUserInput;
	
	public Content(String userInput, String command) {
		userInput = userInput.trim();
		this.command = command;
		if (command.equals(COMMAND_ADD) || command.equals(COMMAND_EDIT)) {	
			if(userInput.charAt(0) == '-') {
				userInput = userInput.substring(1);
			}
			arr = userInput.split("-");
			
			for(int i=0; i<arr.length;i++) {
				//System.out.print("arr="+arr[i]);
				arr[i] = arr[i].trim();
			}
			System.out.println();
		} else {
			remainingUserInput = userInput.toLowerCase();
		}
	}
	
	public void extractContent(Map<String,String> dictionary) {
		switch (command) {
		case COMMAND_ADD:
			extractAddContent(dictionary);
			break;
		case COMMAND_DELETE:
			extractDeleteContent(dictionary);
			break;
		case COMMAND_DISPLAY:
			extractDisplayContent(dictionary);
			break;
		case COMMAND_EDIT:
			extractEditContent(dictionary);
			break;
		case COMMAND_HELP:
			extractHelpContent(dictionary);
			break;
		default:
			extractSearchContent(dictionary);
			break;
		}
	}
	
	private void extractHelpContent(Map<String, String> dictionary) {
		dictionary.put(HELP_KEYWORD, remainingUserInput);		
	}

	private void extractAddContent(Map<String,String> dictionary) {
		if(arr.length == ARR_SIZE_EVENT) {
			extractEventContent(dictionary);
		} else if (arr.length == ARR_SIZE_DEADLINES) {
			extractDeadlineContent(dictionary);
		} else {
			extractFloatingContent(dictionary);
		}
	}
	
	private void extractFloatingContent(Map<String, String> dictionary) {
		dictionary.put(DICTIONARY_ADD_DESC, arr[0]);
		dictionary.put(DICTIONARY_ADD_START, null);
		dictionary.put(DICTIONARY_ADD_END, null);
	}

	private void extractDeadlineContent(Map<String, String> dictionary) {
		for (int i=0; i < arr.length; i++) {
			if (arr[i].contains(ADD_KEYWORD_DEADLINE_END)) {
				String endDate = extractEndDate(arr[i]);
				dictionary.put(DICTIONARY_ADD_END, endDate);
				dictionary.put(DICTIONARY_ADD_START, null);
			} else {
				dictionary.put(DICTIONARY_ADD_DESC, arr[i]);
			}
		}
	}

	private void extractEventContent(Map<String, String> dictionary) {
		for (int i=0; i < arr.length; i++) {
			if (arr[i].contains(ADD_KEYWORD_EVENT_START)) {
				String startDate = extractStartDate(arr[i]);
				dictionary.put(DICTIONARY_ADD_START, startDate);
			} else if (arr[i].contains(ADD_KEYWORD_EVENT_END)) {
				String endDate = extractEndDate(arr[i]);
				dictionary.put(DICTIONARY_ADD_END, endDate);
			} else {
				dictionary.put(DICTIONARY_ADD_DESC,arr[i]);
			}
		}
	}

	private String extractStartDate(String str) {
		return str.replace(ADD_KEYWORD_EVENT_START, "").trim();
	}
	
	private String extractEndDate(String str) {
		String extracted = str.replace(ADD_KEYWORD_EVENT_END,"").trim();
		return extracted.replace(ADD_KEYWORD_DEADLINE_END,"").trim();
	}
	
	private void extractDeleteContent(Map<String,String> dictionary) {
		dictionary.put(DELETE_KEYWORD, remainingUserInput);
	}
	
	private void extractDisplayContent(Map<String,String> dictionary) {
		
	
		if (remainingUserInput.equals("")) {
			dictionary.put(DICTIONARY_DISPLAY_SUB, null);
		}  else {
			arr = remainingUserInput.split(" ");
			if (arr[0].equals(DISPLAY_KEYWORD_ON_DATE) || arr[0].equals(DISPLAY_KEYWORD_FROM_DATE)
					|| arr[0].equals(DISPLAY_KEYWORD_AFTER_DATE) || arr[0].equals(DISPLAY_KEYWORD_DUE_DATE)) {
				dictionary.put(DICTIONARY_DISPLAY_SUB, arr[0]);
				dictionary.put(DICTIONARY_DISPLAY_DATE, arr[1]);
			} else if (arr[0].equals(DISPLAY_KEYWORD_INCOMPLETE) || arr[0].equals(DISPLAY_KEYWORD_FLOATING)) { 
				dictionary.put(DICTIONARY_DISPLAY_SUB, arr[0]);
			} else {
				// need to specify types of color available. if not invalid subcommand also can pass through
				dictionary.put(DICTIONARY_DISPLAY_SUB, DICTIONARY_DISPLAY_COLOR);
			}
		}
	}
	
	private void extractEditContent(Map<String,String> dictionary) {
		for (int i=0; i < arr.length; i++) {
			if (i==1) {
//				System.out.println(arr[i]);
				System.out.println(EDIT_KEYWORD_DESC);
			}
			if (arr[i].equals(EDIT_KEYWORD_DESC) || arr[i].equals(EDIT_KEYWORD_START) || arr[i].equals(EDIT_KEYWORD_END)) {
				dictionary.put(DICTIONARY_EDIT_FIELD, arr[i]);
			} else if (isNumeric(arr[i])) {
				dictionary.put(DICTIONARY_EDIT_INDEX, arr[i]);
			} else {
				dictionary.put(DICTIONARY_EDIT_NEW, arr[i]);
			}
		}
	}
	
	public boolean isNumeric(String str) {  
		String regex = "[0-9]+";
	    return str.matches(regex);  
	}  
	
	private void extractSearchContent(Map<String,String> dictionary) {
		dictionary.put(DICTIONARY_SEARCH, remainingUserInput);
	}
}
```
###### src\main\java\com\cs2013t143j\TaskBuddyM\Parser\TBParser.java
``` java
// Moved to TBParserStub

public class TBParser {
	
	private static final String INVALID_INPUT = "Invalid input entered.";
	private CommandParser cmd;
//	private Content content;

	private String userInput;

	public TBParser() {
	}
	
	public Map<String, String> getDictionary (String input) throws InvalidInputException {
		Map<String, String> dictionary = new HashMap<String,String>();
		userInput = input;
		
		if (userInput.equals(null)) {
			throw new InvalidInputException(INVALID_INPUT);
		}
		
		retrieveCommand(dictionary);
		//retrieveContent(dictionary, command);
		return dictionary;
	}

//	private void retrieveContent(Map<String, String> dictionary, String command) {
//		if (!userInput.equals(null)) {
//			content = new Content(userInput,command);
//			content.extractContent(dictionary);
//		}
//	}

	private void retrieveCommand(Map<String, String> dictionary) throws InvalidInputException {
		cmd = new CommandParser(userInput);
		cmd.extractShortcutCommand(dictionary);
		cmd.extractSubCommand(dictionary);
		userInput = cmd.removeWord(dictionary.get("command"));
	} 
}
```
