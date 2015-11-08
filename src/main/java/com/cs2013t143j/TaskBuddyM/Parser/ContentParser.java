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
	private static final String DIC_HELP_KEY = "helpKey";
	
	private static final String ERROR_INDEX = "No index entered";
	private static final String ERROR_FIELD = "Invalid field entered";
	private static final String ERROR_EDIT_CONTENT = "Invalid content for 'edit' entered";
	
	String userInput;
	
	Map<String,String> dictionary;
	
	DateParser date;
	
	Logger logger = Logger.getLogger(Logic.class.getName());
	
	public ContentParser(String userInput, Map<String,String> dictionary) {
		this.userInput = userInput;
		logger.log(Level.INFO, "Leftover content to be parsed: " + userInput);
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
			InvalidInputException e = new InvalidInputException("No description entered for new task");
			logger.log(Level.SEVERE, "Exception in ContentParser (extractAddContent)");
			throw e;
		}
		
		logger.log(Level.INFO, "Add Description: " + dictionary.get(DIC_DESCRIPTION));
		logger.log(Level.INFO, "Add Start date: " + dictionary.get(DIC_START_DATE));
		logger.log(Level.INFO, "Add End date: " + dictionary.get(DIC_END_DATE));
		
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
		checkException("extractDeleteContent", ERROR_INDEX);
		logger.log(Level.INFO, "Delete Index: " + userInput);
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
		
		if (!dictionary.get(DIC_SUBCOMMAND).equalsIgnoreCase(DIC_SUBCOMMAND_RANGE)) {
			String temp = dictionary.remove(DIC_START_DATE);

			if (temp == null) {
				temp = dictionary.remove(DIC_END_DATE);
			}
			dictionary.put("date", temp);
		}
		logger.log(Level.INFO, "Display Start date: " + dictionary.get(DIC_START_DATE));
		logger.log(Level.INFO, "Display End date: " + dictionary.get(DIC_END_DATE));
		logger.log(Level.INFO, "Display Date: " + dictionary.get("date"));
	}
	
	public Map<String, String> extractEditContent() throws InvalidInputException, TooManyDateFoundException  {
		assert userInput != "";
		
		retrieveEditIndex();
		
		checkException("extractEditContent", ERROR_EDIT_CONTENT);
		
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
		logger.log(Level.INFO, "Edit Index: " + index);
	}

	private void retrieveEditDescription() {
		dictionary.put(DIC_FIELD,DIC_DESCRIPTION);
		userInput = removeWord(DIC_DESCRIPTION);
		dictionary.put(DIC_NEW_VALUE,userInput);
		logger.log(Level.INFO, "Edit Description: " + userInput);
	}

	private void retrieveEditStartDate() throws TooManyDateFoundException {
		dictionary.put(DIC_FIELD,"start date");
		userInput = userInput.replace("start date", "startdate");
		dictionary.put(DIC_DESCRIPTION,userInput);
		dictionary = date.parse(dictionary);
		dictionary.put(DIC_NEW_VALUE,dictionary.remove(DIC_START_DATE));
		logger.log(Level.INFO, "Edit Start Date: " + dictionary.get(DIC_NEW_VALUE));
	}

	private void retrieveEditEndDate() throws TooManyDateFoundException  {
		dictionary.put(DIC_FIELD,"end date");
		userInput = userInput.replace("end date", "enddate");
		dictionary.put(DIC_DESCRIPTION,userInput);
		dictionary = date.parse(dictionary);
		dictionary.put(DIC_NEW_VALUE,dictionary.remove(DIC_END_DATE));
		logger.log(Level.INFO, "Edit End Date: " + dictionary.get(DIC_NEW_VALUE));
	}

	public Map<String,String> extractSearchContent() {
		assert userInput != null;
		assert userInput != "";
		dictionary.put(DIC_SEARCH_KEY, userInput);
		logger.log(Level.INFO, "Search Content: " + userInput);
		return dictionary;
	}
	
	public Map<String,String> extractDoneContent() {
		assert userInput != null;
		assert userInput != ""; 
		dictionary.put(DIC_INDEX, userInput);
		logger.log(Level.INFO, "Done Index: " + userInput);
		return dictionary;
	}
	
	public Map<String, String> extractHelpContent()  {
		assert userInput != null;
		assert userInput != "";
		dictionary.put(DIC_HELP_KEY, userInput);
		return dictionary;
	}
	
	public String removeWord(String word) {
		assert word != null;
		assert word != "";
		String regex = "\\s*\\b" + word + "\\b";
		String temp = userInput.replaceAll(regex,"").trim();
		userInput = temp;
		return temp;
	}
	
	private void checkException(String method, String error) throws InvalidInputException {
		assert method != null;
		assert method != "";
		if (userInput.equals(null) || userInput.equals("") || userInput.equals(" ")) {
			InvalidInputException e = new InvalidInputException(error);
			logger.log(Level.SEVERE, "Exception in ContentParser " + method);
			throw e;
		}
	}

}
