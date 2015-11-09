package com.cs2013t143j.TaskBuddyM.ParserTest;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Parser.CommandParser;
import com.cs2013t143j.TaskBuddyM.Parser.DateParser;
import com.cs2013t143j.TaskBuddyM.Parser.InvalidInputException;
import com.cs2013t143j.TaskBuddyM.Parser.TooManyDateFoundException;

//@@author A0145680A

public class DateParserCase01Test {
	@Test
	public void case01test0() {
		Map<String, String> dictionary = new HashMap<String,String>();
		String userInput = "project with wxz due today";
		dictionary.put("description", userInput);
		
		DateParser parser = new DateParser();
		Map<String, String> result = new HashMap<String,String>();
		try {
			result = parser.parse(dictionary);
		} catch (TooManyDateFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("project with wxz ",dictionary.get("description"));
		assertEquals(null,dictionary.get("startDate"));
		assertEquals(convertDateToString(new Date()),dictionary.get("endDate"));
	}
	
	@Test
	public void case01test1() {
		Map<String, String> dictionary = new HashMap<String,String>();
		String userInput = "cs2013 project with wxz due tomorrow";
		dictionary.put("description", userInput);
		
		DateParser parser = new DateParser();
		Map<String, String> result = new HashMap<String,String>();
		try {
			result = parser.parse(dictionary);
		} catch (TooManyDateFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date date = new Date();
		date = changeDate(date, 1);
		assertEquals("cs2013 project with wxz ",dictionary.get("description"));
		assertEquals(null,dictionary.get("startDate"));
		assertEquals(convertDateToString(date),dictionary.get("endDate"));
	}
	
	@Test
	public void case01test2() {
		Map<String, String> dictionary = new HashMap<String,String>();
		String userInput = "cs2013 project with wxz start by today";
		dictionary.put("description", userInput);
		
		DateParser parser = new DateParser();
		Map<String, String> result = new HashMap<String,String>();
		try {
			result = parser.parse(dictionary);
		} catch (TooManyDateFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("cs2013 project with wxz ",dictionary.get("description"));
		assertEquals(convertDateToString(new Date()),dictionary.get("startDate"));
		assertEquals(null,dictionary.get("endDate"));
	}
	
	@Test
	public void case01test3() {
		Map<String, String> dictionary = new HashMap<String,String>();
		String userInput = "cs2013 05zz project with wxz at today";
		dictionary.put("description", userInput);
		
		DateParser parser = new DateParser();
		Map<String, String> result = new HashMap<String,String>();
		try {
			result = parser.parse(dictionary);
		} catch (TooManyDateFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("cs2013 05zz project with wxz ",dictionary.get("description"));
		assertEquals(convertDateToString(new Date()),dictionary.get("startDate"));
		assertEquals(null,dictionary.get("endDate"));
	}

	private Date changeDate(Date date, int i) {
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, i);
		return c.getTime();
	}
	
	private String convertDateToString(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // Note: zero based!
        String dateString = String.format("%02d %02d/%02d/%d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        return dateString;
    }
}
