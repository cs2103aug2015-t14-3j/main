import java.util.Map;

public class Content {

	private static final int ARR_SIZE_EVENT = 3;
	private static final int ARR_SIZE_DEADLINES = 2;
	
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_EDIT = "edit";
	private static final String COMMAND_DISPLAY = "display";
	
	private static final String DICTIONARY_ADD_START = "startDate";
	private static final String DICTIONARY_ADD_END = "endDate";
	private static final String DICTIONARY_ADD_DESC = "description";
	
	private static final String ADD_KEYWORD_EVENT_START = "from";
	private static final String ADD_KEYWORD_EVENT_END = "to";
	private static final String ADD_KEYWORD_DEADLINE_END = "by";
	
	private static final String DELETE_KEYWORD = "index";
	
	private static final String DICTIONARY_DISPLAY_SUB = "subCommand";
	private static final String DICTIONARY_DISPLAY_COLOR = "color";
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
			arr = userInput.split("-");
		} else {
			remainingUserInput = userInput;
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
		default:
			extractSearchContent(dictionary);
			break;
		}
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
		return str.replace(ADD_KEYWORD_EVENT_END,"").trim();
	}
	
	private void extractDeleteContent(Map<String,String> dictionary) {
		dictionary.put(DELETE_KEYWORD, arr[0]);
	}
	
	private void extractDisplayContent(Map<String,String> dictionary) {
		arr = remainingUserInput.split(" ");
		
		if (arr.length == 0) {
			dictionary.put(DICTIONARY_DISPLAY_SUB, null);
		} else if (arr[0].equals(DISPLAY_KEYWORD_ON_DATE) || arr[0].equals(DISPLAY_KEYWORD_FROM_DATE) 
				|| arr[0].equals(DISPLAY_KEYWORD_AFTER_DATE) || arr[0].equals(DISPLAY_KEYWORD_DUE_DATE)
				|| arr[0].equals(DISPLAY_KEYWORD_INCOMPLETE) || arr[0].equals(DISPLAY_KEYWORD_FLOATING)) {
			dictionary.put(DICTIONARY_DISPLAY_SUB, arr[0]);
		} else {
			dictionary.put(DICTIONARY_DISPLAY_SUB, DICTIONARY_DISPLAY_COLOR );
		}
	}
	
	private void extractEditContent(Map<String,String> dictionary) {
		for (int i=0; i < arr.length; i++) {
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
	    return str.matches("[-+]?\\d*\\.?\\d+");  
	}  
	
	private void extractSearchContent(Map<String,String> dictionary) {
		dictionary.put(DICTIONARY_SEARCH, remainingUserInput);
	}
}
