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
	public Map<String, String> getDictionary (String input)  {
		Map<String, String> dictionary = new HashMap<String,String>();
		userInput = input;
		
		/*if (userInput.equals(null)) {
			throw new InvalidInputException(ERROR_COMMAND);
		}*/

		retrieveCommand(dictionary);
		retrieveContent(dictionary);
		return dictionary;
	}
	
	private void retrieveCommand(Map<String, String> dictionary) {
		CommandParser cmd = new CommandParser(userInput);
		cmd.extractShortcutCommand(dictionary);
		cmd.extractSubCommand(dictionary);
		userInput = cmd.removeCommand(dictionary.get("command"));
	} 
	
	private void retrieveContent(Map<String,String> dictionary) {
		switch(dictionary.get("command")) {
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
		default:
			extractSearchContent(dictionary);
			break;
		}
	}
	
	private void extractAddContent(Map<String,String> dictionary) {
		//assert userInput != null;
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
		//assert userInput != null;
		String[] inputs = userInput.split(" ",2);
		dictionary.put("index", inputs[1]);
	}
	
	private void extractDisplayContent(Map<String,String> dictionary) {
				dictionary.put("subCommand", null);
	}
	
	private void extractEditContent(Map<String,String> dictionary) {
		//assert userInput != null;
		
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
		//assert userInput != null;
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
	
	public String testDatePraser(String testString) {
		List<String> tList = parseDateToStringArray(testString);
		String tS = "";
		for(String s : tList){
			tS = s;
		}
		return tS;
		
	}
}
