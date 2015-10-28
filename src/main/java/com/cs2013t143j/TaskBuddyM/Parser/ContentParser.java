package com.cs2013t143j.TaskBuddyM.Parser;

import java.util.Map;

public class ContentParser {
	
	String userInput;
	Map<String,String> dictionary;
	
	DateParser date;
	
	public ContentParser(String userInput, Map<String,String> dictionary) {
		this.userInput = userInput;
		this.dictionary = dictionary;
		date = new DateParser(dictionary);
	}

	public Map<String,String> extractAddContent() {
		replaceWhiteSpace();
		dictionary.put("description",userInput);
		dictionary = date.parse(dictionary);

		return dictionary;
	}

	private void replaceWhiteSpace() {
		String[] temp = userInput.split(" ");
		
		for(int i=0; i < temp.length-1; i++) {
			if(temp[i].equalsIgnoreCase("start") || temp[i].equalsIgnoreCase("end")) {
				if(temp[i+1].equalsIgnoreCase("date")) {
					userInput = removeWord("date");
					userInput = userInput.replace(temp[i], temp[i]+"Date");
				}
			}
		}
	}
	
	public Map<String,String> extractDeleteContent() {
		/*if (userInput.equals("")) {
			throw InvalidInputException("Invalid index entered");
		}*/
		dictionary.put("index", userInput);
		return dictionary;
	}
	
	public Map<String,String> extractDisplayContent()  {
		// need to pass to date parser
		if (userInput.length() != 0) {
			dictionary.put("description", userInput);
			retrieveDisplayDate();
		}
		
		return dictionary;
	}

	private void retrieveDisplayDate() {
		dictionary = date.parse(dictionary);
		/*String temp = dictionary.remove("startDate");
		if(temp != null) {
			temp = temp.substring(temp.indexOf(" ")+1, temp.length());
		}
		dictionary.put("start date",temp);
		
		temp = dictionary.remove("endDate");
		if(temp != null) {
			temp = temp.substring(temp.indexOf(" ")+1, temp.length());
		}
		dictionary.put("end date", temp);*/
		
		String temp = dictionary.remove("startDate");
		
		if(temp == null) {
			temp = dictionary.remove("endDate");
		}
		dictionary.put("date",temp);
		
	}
	
	public Map<String, String> extractEditContent()  {
		//assert userInput != null;
		retrieveEditIndex();
		
		if(userInput.contains("end date")) {
			retrieveEditEndDate();
		} else if (userInput.contains("start date")) {
			retrieveEditStartDate();
		} else if (userInput.contains("description")){
			retrieveEditDescription();
		} else {
			//throw new InvalidInputException("Invalid field");
		}
		
		return dictionary;
	}

	private void retrieveEditIndex() {
		int whiteSpaceIndex = userInput.indexOf(" ");
		String index = userInput.substring(0, whiteSpaceIndex);
		userInput = removeWord(index);
		dictionary.put("index",index);
	}

	private void retrieveEditDescription() {
		dictionary.put("field","description");
		userInput = removeWord("description");
		dictionary.put("newValue",userInput);
	}

	private void retrieveEditStartDate() {
		dictionary.put("field","start date");
		userInput = userInput.replace("start date", "startdate");
		dictionary.put("description",userInput);
		dictionary = date.parse(dictionary);
		dictionary.put("newValue",dictionary.remove("startDate"));
	}

	private void retrieveEditEndDate() {
		dictionary.put("field","end date");
		userInput = userInput.replace("end date", "enddate");
		dictionary.put("description",userInput);
		dictionary = date.parse(dictionary);
		dictionary.put("newValue",dictionary.remove("endDate"));
	}

	public Map<String,String> extractSearchContent() {
		//assert userInput != null;
		dictionary.put("searchKey", userInput);
		return dictionary;
	}
	
	public Map<String,String> extractDoneContent() {
		dictionary.put("index", userInput);
		
		return dictionary;
	}
	
	public String removeWord(String word) {
		String regex = "\\s*\\b" + word + "\\b";
		String temp = userInput.replaceAll(regex,"").trim();
		userInput = temp;
		return temp;
	}
}