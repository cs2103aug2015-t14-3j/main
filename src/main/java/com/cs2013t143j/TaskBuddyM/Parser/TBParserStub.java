package com.cs2013t143j.TaskBuddyM.Parser;
import java.util.*;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class TBParserStub {
	
	
	final String ERROR_NO_COMMAND = "No command entered.";
	CommandParser cmd;
	DateParser date;
	String userInput;

	public TBParserStub() {	
	}
	
	// parse input to a dict
	public Map<String, String> getDictionary (String input)  {
		Map<String, String> dictionary = new HashMap<String,String>();
		date = new DateParser(dictionary);
		userInput = input;
		
		/*if (userInput.equals("")) {
			throw new InvalidInputException(ERROR_NO_COMMAND);
		}*/

		retrieveCommand(dictionary);
		retrieveContent(dictionary);
		return dictionary;
	}
	
	private void retrieveCommand(Map<String, String> dictionary) {
		cmd = new CommandParser(userInput);
		cmd.extractShortcutCommand(dictionary);
		cmd.extractSubCommand(dictionary);
		userInput = cmd.removeWord(dictionary.get("command"));
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
		case "done":
			extractDoneContent(dictionary);
			break;
		default:
			extractSearchContent(dictionary);
			break;
		}
	}
	
	private void extractAddContent(Map<String,String> dictionary) {
		dictionary.put("description",userInput);
		dictionary = date.parse(dictionary);
	}
	
	private void extractDeleteContent(Map<String,String> dictionary) {
		/*if (userInput.equals("")) {
			throw InvalidInputException("Invalid index entered");
		}*/
		dictionary.put("index", userInput);
	}
	

	private void extractDisplayContent(Map<String,String> dictionary) {
		// need to pass to date parser
		if (userInput.length() != 0) {
			dictionary.put("description", userInput);
			dictionary = date.parse(dictionary);
			String temp = dictionary.remove("startDate");
			
			if (temp == null) {
				temp = dictionary.remove("endDate");
			}
			
			dictionary.put("date", temp);
		}
	}
	
	private void extractEditContent(Map<String,String> dictionary) {
		//assert userInput != null;
		int whiteSpaceIndex = userInput.indexOf(" ");
		String index = userInput.substring(0, whiteSpaceIndex);
		userInput = cmd.removeWord(index);
		dictionary.put("index",index);
		
		if(userInput.contains("end date")) {
			editEndDate(dictionary);
		} else if (userInput.contains("start date")) {
			editStartDate(dictionary);
		} else if (userInput.contains("description")){
			editDescription(dictionary);
		} else {
			//throw new InvalidInputException("Invalid field");
		}
	}

	private void editDescription(Map<String, String> dictionary) {
		dictionary.put("field","description");
		userInput = cmd.removeWord("description");
		dictionary.put("newValue",userInput);
	}

	private void editStartDate(Map<String, String> dictionary) {
		dictionary.put("field","start date");
		//userInput = cmd.removeWord("start date");
		userInput = userInput.replace("start date", "startdate");
		dictionary.put("description",userInput);
		dictionary = date.parse(dictionary);
		dictionary.put("newValue",dictionary.remove("startDate"));
	}

	private void editEndDate(Map<String, String> dictionary) {
		dictionary.put("field","end date");
		//userInput = cmd.removeWord("end date");
		userInput = userInput.replace("end date", "enddate");
		dictionary.put("description",userInput);
		dictionary = date.parse(dictionary);
		dictionary.put("newValue",dictionary.remove("endDate"));
	}

	private void extractSearchContent(Map<String,String> dictionary) {
		//assert userInput != null;
		dictionary.put("searchKey", userInput);
	}
	
	private void extractDoneContent(Map<String, String> dictionary) {
		dictionary.put("index", userInput);
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
	
	public String testDateParser(String testString) {
		List<String> tList = parseDateToStringArray(testString);
		String tS = "";
		for(String s : tList){
			tS = s;
		}
		return tS;
		
	}
}
