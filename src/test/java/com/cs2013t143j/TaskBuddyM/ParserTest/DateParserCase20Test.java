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

public class DateParserCase20Test {
	@Test
	public void case20test0() {
		Map<String, String> dictionary = new HashMap<String,String>();
		String userInput = "project with wxz from 06112015 to 09112015";
		dictionary.put("description", userInput);
		
		DateParser parser = new DateParser();
		Map<String, String> result = new HashMap<String,String>();
		try {
			result = parser.parse(dictionary);
		} catch (TooManyDateFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date d1 = convertStringToDate("11062015");
		Date d2 = convertStringToDate("11092015");
		assertEquals("project with wxz ",dictionary.get("description"));
		assertEquals(convertDateToString(d1),dictionary.get("startDate"));
		assertEquals(convertDateToString(d2),dictionary.get("endDate"));
	}
	
	@Test
	public void case20test1() {
		Map<String, String> dictionary = new HashMap<String,String>();
		String userInput = "project with wxz from 06102015 to 09122015";
		dictionary.put("description", userInput);
		
		DateParser parser = new DateParser();
		Map<String, String> result = new HashMap<String,String>();
		try {
			result = parser.parse(dictionary);
		} catch (TooManyDateFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date d1 = convertStringToDate("10062015");
		Date d2 = convertStringToDate("12092015");
		assertEquals("project with wxz ",dictionary.get("description"));
		assertEquals(convertDateToString(d1),dictionary.get("startDate"));
		assertEquals(convertDateToString(d2),dictionary.get("endDate"));
	}
	
	@Test
	public void case20test2() {
		Map<String, String> dictionary = new HashMap<String,String>();
		String userInput = "project with wxz 06112015 to 09112015";
		dictionary.put("description", userInput);
		
		DateParser parser = new DateParser();
		Map<String, String> result = new HashMap<String,String>();
		try {
			result = parser.parse(dictionary);
		} catch (TooManyDateFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date d1 = convertStringToDate("11062015");
		Date d2 = convertStringToDate("11092015");
		assertEquals("project with wxz ",dictionary.get("description"));
		assertEquals(convertDateToString(d1),dictionary.get("startDate"));
		assertEquals(convertDateToString(d2),dictionary.get("endDate"));
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
	
	private Date convertStringToDate(String s){
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
}
