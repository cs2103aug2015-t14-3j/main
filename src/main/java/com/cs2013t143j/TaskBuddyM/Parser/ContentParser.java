package com.cs2013t143j.TaskBuddyM.Parser;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class ContentParser {
	
	private static final String DIC_DESCRIPTION = "description";
	private static final String DIC_START_DATE = "startDate";
	private static final String DIC_END_DATE = "endDate";
	private static final String DIC_INDEX = "index";
	private static final String DIC_FIELD = "field";
	private static final String DIC_NEW_VALUE = "newValue";
	private static final String DIC_SEARCH_KEY = "searchKey";
	private static final String DIC_SUBCOMMAND = "subCommand";
	private static final String DIC_SUBCOMMAND_RANGE = "range";
	
	private static final String ERROR_INDEX = "Invalid index entered";
	private static final String ERROR_FIELD = "Invalid field entered";
	
	String userInput;
	
	Map<String,String> dictionary;
	
	DateParser date;
	
	Logger logger = Logger.getLogger(Logic.class.getName());
	
	public ContentParser(String userInput, Map<String,String> dictionary) {
		this.userInput = userInput;
		this.dictionary = dictionary;
		date = new DateParser(dictionary);
	}

	public Map<String,String> extractAddContent() throws TooManyDateFoundException, InvalidInputException {
		String[] temp = userInput.split(" ");
		dictionary.put(DIC_DESCRIPTION, userInput);
		if(temp.length>1) {
			replaceWhiteSpace(temp);
			dictionary = date.parse(dictionary);
		} 
		
		if (dictionary.get(DIC_DESCRIPTION).equals("")) {
			InvalidInputException e = new InvalidInputException("No descripton entered for new task");
			logger.log(Level.SEVERE, "Exception(extractAddContent)");
			throw e;
		}
		return dictionary;
	}

	private void replaceWhiteSpace(String[] temp) {
		for(int i=0; i < temp.length-1; i++) {
			if(temp[i].equalsIgnoreCase("start") || temp[i].equalsIgnoreCase("end")) {
				if(temp[i+1].equalsIgnoreCase("date")) {
					userInput = removeWord("date");
					userInput = userInput.replace(temp[i], temp[i]+"Date");
				}
			}
		}
	}
	
	public Map<String,String> extractDeleteContent() throws InvalidInputException {
		if (userInput.equals("")) {
			throw new InvalidInputException(ERROR_INDEX);
		}
		dictionary.put("index", userInput);
		return dictionary;
	}
	
	public Map<String,String> extractDisplayContent() throws TooManyDateFoundException  {
		if (userInput.length() != 0) {
			dictionary.put(DIC_DESCRIPTION, userInput);
			retrieveDisplayDate();
		}
		
		return dictionary;
	}

	private void retrieveDisplayDate() throws TooManyDateFoundException {
		dictionary = date.parse(dictionary);
		
		/*if (dictionary.get(DIC_START_DATE) == null || dictionary.get(DIC_START_DATE) == ""
				|| dictionary.get(DIC_END_DATE) == null || dictionary.get(DIC_END_DATE) == "") {
			String temp = dictionary.remove(DIC_START_DATE);

			if (temp == null) {
				temp = dictionary.remove(DIC_END_DATE);
			}
			dictionary.put("date", temp);
		}*/
		
		if (!dictionary.get(DIC_SUBCOMMAND).equalsIgnoreCase(DIC_SUBCOMMAND_RANGE)) {
			String temp = dictionary.remove(DIC_START_DATE);

			if (temp == null) {
				temp = dictionary.remove(DIC_END_DATE);
			}
			dictionary.put("date", temp);
		}
	}
	
	public Map<String, String> extractEditContent() throws InvalidInputException  {
		//assert userInput != null;
		retrieveEditIndex();
		
		if(userInput.toLowerCase().contains("end date") || userInput.toLowerCase().contains("enddate")) {
			retrieveEditEndDate();
		} else if (userInput.toLowerCase().contains("start date") || userInput.toLowerCase().contains("startdate")) {
			retrieveEditStartDate();
		} else if (userInput.toLowerCase().contains(DIC_DESCRIPTION)){
			retrieveEditDescription();
		} else {
			throw new InvalidInputException(ERROR_FIELD);
		}
		
		return dictionary;
	}

	private void retrieveEditIndex() {
		int whiteSpaceIndex = userInput.indexOf(" ");
		String index = userInput.substring(0, whiteSpaceIndex);
		userInput = removeWord(index);
		dictionary.put(DIC_INDEX,index);
	}

	private void retrieveEditDescription() {
		dictionary.put(DIC_FIELD,DIC_DESCRIPTION);
		userInput = removeWord(DIC_DESCRIPTION);
		dictionary.put(DIC_NEW_VALUE,userInput);
	}

	private void retrieveEditStartDate() {
		dictionary.put(DIC_FIELD,"start date");
		userInput = userInput.replace("start date", "startdate");
		dictionary.put(DIC_DESCRIPTION,userInput);
		try {
			dictionary = date.parse(dictionary);
		} catch (TooManyDateFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dictionary.put(DIC_NEW_VALUE,dictionary.remove(DIC_START_DATE));
	}

	private void retrieveEditEndDate() {
		dictionary.put(DIC_FIELD,"end date");
		userInput = userInput.replace("end date", "enddate");
		dictionary.put(DIC_DESCRIPTION,userInput);
		try {
			dictionary = date.parse(dictionary);
		} catch (TooManyDateFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dictionary.put(DIC_NEW_VALUE,dictionary.remove(DIC_END_DATE));
	}

	public Map<String,String> extractSearchContent() {
		//assert userInput != null;
		dictionary.put(DIC_SEARCH_KEY, userInput);
		return dictionary;
	}
	
	public Map<String,String> extractDoneContent() {
		dictionary.put(DIC_INDEX, userInput);
		
		return dictionary;
	}
	
	public String removeWord(String word) {
		String regex = "\\s*\\b" + word + "\\b";
		String temp = userInput.replaceAll(regex,"").trim();
		userInput = temp;
		return temp;
	}
}
