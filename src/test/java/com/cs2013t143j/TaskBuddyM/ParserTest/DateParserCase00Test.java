package com.cs2013t143j.TaskBuddyM.ParserTest;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Parser.CommandParser;
import com.cs2013t143j.TaskBuddyM.Parser.DateParser;
import com.cs2013t143j.TaskBuddyM.Parser.InvalidInputException;
import com.cs2013t143j.TaskBuddyM.Parser.TooManyDateFoundException;

//@@author A0145680A

public class DateParserCase00Test {
	@Test
	public void case00test0() {
		Map<String, String> dictionary = new HashMap<String,String>();
		String userInput = "project with wxz";
		dictionary.put("description", userInput);
		
		DateParser parser = new DateParser();
		Map<String, String> result = new HashMap<String,String>();
		try {
			result = parser.parse(dictionary);
		} catch (TooManyDateFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("project with wxz",dictionary.get("description"));
		assertEquals(null,dictionary.get("startDate"));
		assertEquals(null,dictionary.get("endDate"));
	}
	
	@Test
	public void case00test1() {
		Map<String, String> dictionary = new HashMap<String,String>();
		String userInput = "cs2013 project with wxz";
		dictionary.put("description", userInput);
		
		DateParser parser = new DateParser();
		Map<String, String> result = new HashMap<String,String>();
		try {
			result = parser.parse(dictionary);
		} catch (TooManyDateFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("cs2013 project with wxz",dictionary.get("description"));
		assertEquals(null,dictionary.get("startDate"));
		assertEquals(null,dictionary.get("endDate"));
	}
	
	@Test
	public void case00test2() {
		Map<String, String> dictionary = new HashMap<String,String>();
		String userInput = "cs2013 05zz project with wxz";
		dictionary.put("description", userInput);
		
		DateParser parser = new DateParser();
		Map<String, String> result = new HashMap<String,String>();
		try {
			result = parser.parse(dictionary);
		} catch (TooManyDateFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("cs2013 05zz project with wxz",dictionary.get("description"));
		assertEquals(null,dictionary.get("startDate"));
		assertEquals(null,dictionary.get("endDate"));
	}
	
	@Test
	public void case00test3() {
		Map<String, String> dictionary = new HashMap<String,String>();
		String userInput = "cs2013 05zz w1w1 project with wxz";
		dictionary.put("description", userInput);
		
		DateParser parser = new DateParser();
		Map<String, String> result = new HashMap<String,String>();
		try {
			result = parser.parse(dictionary);
		} catch (TooManyDateFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("cs2013 05zz w1w1 project with wxz",dictionary.get("description"));
		assertEquals(null,dictionary.get("startDate"));
		assertEquals(null,dictionary.get("endDate"));
	}

}
