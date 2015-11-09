package com.cs2013t143j.TaskBuddyM.ParserTest;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class DateParserCase11Test {
	@Test
	public void case11test0() {
		Map<String, String> dictionary = new HashMap<String,String>();
		String userInput = "project with wxz from today to 09112015";
		dictionary.put("description", userInput);
		
		DateParser parser = new DateParser();
		Map<String, String> result = new HashMap<String,String>();
		try {
			result = parser.parse(dictionary);
		} catch (TooManyDateFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date today = new Date();
		Date date = convertStringToDate1("11092015");
		assertEquals("project with wxz ",dictionary.get("description"));
		assertEquals(convertDateToString(today),dictionary.get("startDate"));
		assertEquals(convertDateToString(date),dictionary.get("endDate"));
	}
	
	@Test
	public void case10test1() {
		Map<String, String> dictionary = new HashMap<String,String>();
		String userInput = "cs2013 project with wxz from 10111994 to today";
		dictionary.put("description", userInput);
		
		DateParser parser = new DateParser();
		Map<String, String> result = new HashMap<String,String>();
		try {
			result = parser.parse(dictionary);
		} catch (TooManyDateFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date today = new Date();
		Date date = convertStringToDate1("11101994");
		assertEquals("cs2013 project with wxz ",dictionary.get("description"));
		assertEquals(convertDateToString(date),dictionary.get("startDate"));
		assertEquals(convertDateToString(today),dictionary.get("endDate"));
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
	
	private Date convertStringToDate1(String s){
		SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
		Date date = new Date();
		try {
			date = format.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	private Date convertStringToDate2(String s){
		SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
		Date date = new Date();
		Calendar c1 = Calendar.getInstance(); 
		c1.setTime(date); 
		int hour = c1.get(Calendar.HOUR_OF_DAY);
		try {
			date = format.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar c2 = Calendar.getInstance(); 
		c2.setTime(date); 
		c2.set(Calendar.HOUR_OF_DAY, hour);
		return c2.getTime();
	}
}
