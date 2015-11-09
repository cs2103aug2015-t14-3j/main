package com.cs2013t143j.TaskBuddyM.ParserTest;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Parser.CommandParser;
import com.cs2013t143j.TaskBuddyM.Parser.ContentParser;
import com.cs2013t143j.TaskBuddyM.Parser.InvalidInputException;
import com.cs2013t143j.TaskBuddyM.Parser.TooManyDateFoundException;

//@@author A0126303W

public class RetrieveContentTest {

	private Map<String, String> dictionary = new HashMap<String,String>();
	private String userInput;
	@Test
	public void test() throws TooManyDateFoundException, InvalidInputException {
		retrieveAddContent();
		retrieveEditContent();
		retrieveDisplayContent();
	}
	
	void retrieveAddContent() throws TooManyDateFoundException, InvalidInputException {
		userInput = "project by 10 Nov 2015";
		
		ContentParser contentParser = new ContentParser(userInput, dictionary);
		contentParser.extractAddContent();
		
		String timeStamp = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
		
		assertEquals("project ",dictionary.get("description"));
		System.out.println(dictionary.get("description"));
		
		assertEquals(timeStamp + " 10/11/2015",dictionary.get("endDate"));
		System.out.println(dictionary.get("endDate"));
	}

	void retrieveEditContent() throws InvalidInputException, TooManyDateFoundException {
		userInput = "1 start date today";
		
		ContentParser contentParser = new ContentParser(userInput, dictionary);
		contentParser.extractEditContent();
		
		String timeStamp = new SimpleDateFormat("HH dd/MM/yyyy").format(Calendar.getInstance().getTime());
		
		assertEquals("start date",dictionary.get("field"));
		System.out.println(dictionary.get("field"));
		
		assertEquals(timeStamp,dictionary.get("newValue"));
		System.out.println(dictionary.get("newValue"));
	}
	
	void retrieveDisplayContent() throws TooManyDateFoundException, InvalidInputException {
		userInput = "d on today";
		
		CommandParser cmdParser = new CommandParser(userInput);
		cmdParser.extractShortcutCommand(dictionary);
		cmdParser.extractSubCommand(dictionary);
		
		ContentParser contentParser = new ContentParser("today", dictionary);
		contentParser.extractDisplayContent();
		
		String timeStamp = new SimpleDateFormat("HH dd/MM/yyyy").format(Calendar.getInstance().getTime());
		
		assertEquals("on",dictionary.get("subCommand"));
		System.out.println(dictionary.get("subCommand"));
		
		assertEquals(timeStamp,dictionary.get("date"));
		System.out.println(dictionary.get("date"));
	}
}
