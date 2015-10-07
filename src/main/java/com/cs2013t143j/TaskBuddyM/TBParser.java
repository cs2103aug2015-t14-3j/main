package com.cs2013t143j.TaskBuddyM;
import java.util.*;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class TBParser {
	
	
	final String ERROR_COMMAND = "Invalid User Command.";
	String userInput;

	public TBParser() {		
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
		if (userInput.contains("add ") || userInput.contains("create ") || userInput.contains("insert ")) {
			command = "add";
			dictionary.put("command", "add");
		} else if (userInput.contains("delete ") || userInput.contains("remove ")) {
			command = "delete";
			dictionary.put("command", "delete");
		} else if (userInput.contains("display ") || userInput.contains("show ")) {
			command = "display";
			dictionary.put("command", "display");
		} else if (userInput.contains("edit ")) {
			command = "edit";
			dictionary.put("command", "edit");
		} else if (userInput.contains("search ") || userInput.contains("look for ")) {
			command = "search";
			dictionary.put("command", "search");
		} else if (userInput.contains("undo ")) {
			command = "undo";
			dictionary.put("command", "undo");
		} else if (userInput.contains("help ")) {
			command = "help";
			dictionary.put("command", "help");
		} 
		return command;
	} 
	
	public void removeCommand(String command) {
		userInput = userInput.replace(command, "");
	}
	
	public void extractContent(String command, Map<String,String> dictionary) {
		switch (command) {
		case "add":
			extractAddContent(dictionary);
			break;
		case "delete":
			extractDeleteContent(dictionary);
			break;
		case "display":
			extractDisplayContent(dictionary);
			break;
		case "edit":
			extractEditContent(dictionary);
			break;
		case "search":
			extractSearchContent(dictionary);
			break;
		default:
			System.out.println(ERROR_COMMAND);
		}
	}
	
	private void extractAddContent(Map<String,String> dictionary) {
		List<String> dateStringList = parseDateToStringArray(userInput);
		int countList = dateStringList.size();
		if (countList == 2) {
			// code
			dictionary.put("description", userInput);
			dictionary.put("startDate", dateStringList.get(0));
			dictionary.put("endDate", dateStringList.get(1));
		} else if (countList == 1) {
			// code
			dictionary.put("description", userInput);
			dictionary.put("startDate", dateStringList.get(0));
			dictionary.put("endDate", null);
		} else {
			dictionary.put("description",userInput.substring(1));
			dictionary.put("startDate", convertDateToString(new Date()));
			dictionary.put("endDate", null);
		}
	}
	
	private void extractDeleteContent(Map<String,String> dictionary) {
		dictionary.put("index", userInput.substring(7));
	}
	
	private void extractDisplayContent(Map<String,String> dictionary) {
		
		int whiteSpaceIndex = userInput.indexOf(" ");
		
		if (whiteSpaceIndex == -1) {
			if (userInput.equals("incomplete")) {
				dictionary.put("subCommand", "incomplete");
			} else if (userInput.equals("floating")) {
				dictionary.put("subCommnad", "floating");
			} else if (userInput.equals("")) {
				dictionary.put("subCommand", null);
			} else {
				dictionary.put("subCommand", "color");
				dictionary.put("color", userInput);
			}
		} else {
			dictionary.put("subCommand", userInput.substring(0,whiteSpaceIndex));
			dictionary.put("date", userInput.substring(whiteSpaceIndex+1,userInput.length()));
		}	
	}
	
	private void extractEditContent(Map<String,String> dictionary) {
		
		int whiteSpaceIndex = userInput.indexOf(" ");
		
		dictionary.put("field", userInput.substring(0,whiteSpaceIndex));
		dictionary.put("newValue", 
				userInput.substring(whiteSpaceIndex+1,userInput.length()));
	}
	
	private void extractSearchContent(Map<String,String> dictionary) {
		dictionary.put("searchKey", userInput.substring(1));
	}
	
	private List<String> parseDateToStringArray(String userInput){
		Parser p = new Parser();
    	List<DateGroup> groups = p.parse(userInput);
    	List<String> returnList = new ArrayList<>();
    	for(DateGroup group:groups) {
    	  List<Date> dates = group.getDates();
    	  for(Date d : dates){
    		  String timeString = convertDateToString(d);
    		  returnList.add(timeString);
    	  }
    	}
    	return returnList;
	}
	
	private String convertDateToString(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // Note: zero based!
        String dateString = String.format("02%d 02%d%02d%d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        return dateString;
    }
}
