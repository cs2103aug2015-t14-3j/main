package com.cs2013t143j.TaskBuddyM.Parser;
import java.util.*;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class TBParserStub {
	
	
	final String ERROR_COMMAND = "Invalid User Command.";
	String userInput;

	public TBParserStub() {		
	}
	
	// parse input to a dict
	public Map<String, String> getDictionary (String input) {
		Map<String, String> dictionary = new HashMap<String,String>();
		userInput = input;
		String command = matchCommand(dictionary);
		String content = removeCommand(command);
		return dictionary;
	}

	public String matchCommand(Map<String,String> dictionary) {
		String command = "";
		if (userInput.startsWith("add") || userInput.startsWith("create") || userInput.startsWith("insert")) {
			command = "add";
			dictionary.put("command", "add");
			extractAddContent(dictionary);
		} else if (userInput.startsWith("delete") || userInput.startsWith("remove")) {
			command = "delete";
			dictionary.put("command", "delete");
			extractDeleteContent(dictionary);
		} else if (userInput.startsWith("display") || userInput.startsWith("show")) {
			command = "display";
			dictionary.put("command", "display");
			extractDisplayContent(dictionary);
		} else if (userInput.startsWith("edit")) {
			command = "edit";
			dictionary.put("command", "edit");
			extractEditContent(dictionary);
		} else if (userInput.startsWith("search") || userInput.startsWith("look for")) {
			command = "search";
			dictionary.put("command", "search");
			extractSearchContent(dictionary);
		} else if (userInput.startsWith("undo")) {
			command = "undo";
			dictionary.put("command", "undo");
		} else if (userInput.startsWith("help")) {
			command = "help";
			dictionary.put("command", "help");
		} else {
			System.out.println(ERROR_COMMAND);
		}
		return command;
	}
	
	public String removeCommand(String command) {
		return userInput.replace(command+" ", "").trim();
	}
	
	private void extractAddContent(Map<String,String> dictionary) {
		String[] inputs = userInput.split(" ",2);
		List<String> dateStringList = parseDateToStringArray(userInput);
    	for(String s : dateStringList){
    		System.out.println(s);
    	}
		int countList = dateStringList.size();
		if (countList == 2) {
			// code
			dictionary.put("description", inputs[1]);
			dictionary.put("startDate", dateStringList.get(0));
			dictionary.put("endDate", dateStringList.get(1));
		} else if (countList == 1) {
			// code
			dictionary.put("description", inputs[1]);
			dictionary.put("startDate", dateStringList.get(0));
			dictionary.put("endDate", null);
		} else {
			dictionary.put("description",inputs[1]);
			dictionary.put("startDate", convertDateToString(new Date()));
			dictionary.put("endDate", null);
		}
	}
	
	private void extractDeleteContent(Map<String,String> dictionary) {
		String[] inputs = userInput.split(" ",2);
		dictionary.put("index", inputs[1]);
	}
	
	private void extractDisplayContent(Map<String,String> dictionary) {
				dictionary.put("subCommand", null);
	}
	
	private void extractEditContent(Map<String,String> dictionary) {
		String[] inputs = userInput.split(" ",3);
		if(inputs.length == 3){
			dictionary.put("field", inputs[1]);
			dictionary.put("newValue", inputs[2]);
		}else if(inputs.length == 2){
			dictionary.put("field", "description");
			dictionary.put("newValue", inputs[1]);
		}else{
			dictionary.put("field", "description");
			dictionary.put("newValue", "stub");
		}
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
        String dateString = String.format("%02d %02d/%02d/%d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        return dateString;
    }
}
