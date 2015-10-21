package com.cs2013t143j.TaskBuddyM.Parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class DateParser {

	private Map<String,String> resDict;
	
	private String format;
	
	public DateParser(Map dict){
		resDict = dict;
	}
	
	public Map parse() {
		parseDate();
		return resDict;
	}
	
	public Map parse(Map dict) {
		resDict = dict;
		parseDate();
		return resDict;
	}
	
	private void init() {
		resDict.put("startDate", null);
		resDict.put("endDate", null);
		
		format = null;
	}
	
	private void parseDate() {
		init();
		String description = (String)resDict.get("description");
		Map<String, String> localizedParseResult = matchDateByLocalFormat(description);
		switch (localizedParseResult.size()) {
			case 0:
				System.out.println("parse1");
				parseDateByNatty(0);
				break;
			case 1:
				System.out.println("parse2");
				String dateString = null;
				for (String dateFormat : localizedParseResult.keySet()) {
					dateString = localizedParseResult.get(dateFormat);
				}
				determineLocal(description, dateString);
				parseDateByNatty(1);
				break;
			case 2:
				System.out.println("parse3");
				// set by order, first is startDate second is endDate
				for (String dateFormat : localizedParseResult.keySet()) {
					if(resDict.get(STARTDATE) == null){
						resDict.put(STARTDATE, localizedParseResult.get(dateFormat));
					}else if(resDict.get(ENDDATE) == null){
						resDict.put(ENDDATE, localizedParseResult.get(dateFormat));
					}
				}
				break;
			default:
				// throw error
				break;
		}
	}
	
	private void parseDateByNatty(int lastMatch) {
		String description = (String)resDict.get("description");
		List<String> NattyParseResult = matchDateByNatty(description);
		int resultSize = NattyParseResult.size();
		if(lastMatch >= resultSize){
			return;
		}else if (lastMatch == 0 && resultSize == 1) {
			determineLocal(description, NattyParseResult.get(0));
		}else if (lastMatch == 0 && resultSize == 2) {
			for (String dateFormat : NattyParseResult) {
				if(resDict.get(STARTDATE) == null){
					resDict.put(STARTDATE, dateFormat);
				}else if(resDict.get(ENDDATE) == null){
					resDict.put(ENDDATE, dateFormat);
				}
			}
		}else{
			
		}
	}
	
	public Map<String, String> matchDateByLocalFormat(String dateString) {
		Map<String, String> res = new LinkedHashMap<>();
	    for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
	    	// this check may be redundant 
	        if (dateString.toLowerCase().matches(".*"+regexp)) {
	        	String format = DATE_FORMAT_REGEXPS.get(regexp);
	        	Pattern p = Pattern.compile(regexp);
	            Matcher m = p.matcher(dateString);
	            while(m.find()) {
	            	String matchDateString = m.group();
	            	System.out.println(matchDateString);
	            	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
	            	try {
						Date matchDate = simpleDateFormat.parse(matchDateString);
						String reformatInputDate = convertDateToString(matchDate);
						res.put(regexp+reformatInputDate, reformatInputDate);
					} catch (ParseException e) {
						System.out.println("Error when use SimpleDateFormat");
						e.printStackTrace();
					}
	            	
	            }
	        }
	    }
	    System.out.println("res size is "+ res.size());
	    return res; 
	}
	
	private List<String> matchDateByNatty(String userInput){
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
	
	private void determineLocal(String input, String dateString) {
		for (String regexp : TIME_KEY_REGEXPS.keySet()) {
	    	// this check may be redundant 
			System.out.println("Somet1");
			System.out.println(input.toLowerCase());
	        if (input.toLowerCase().matches("^.*"+regexp+".*$")) {
	        	System.out.println("Somet2");
	        	boolean d = TIME_KEY_REGEXPS.get(regexp);
	        	if(d){
	        		resDict.put(STARTDATE, dateString);
	        	}else {
	        		resDict.put(ENDDATE, dateString);
				}
	        	return;
	        }
	    }
		resDict.put(ENDDATE, dateString);
	}
	
	private String convertDateToString(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // Note: zero based!
        String dateString = String.format("%02d %02d/%02d/%d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        return dateString;
    }
	
	private static final String DESCRIPTION = "description";
	private static final String STARTDATE = "startDate";
	private static final String ENDDATE = "endDate";
	private static final Map<String, String> DATE_FORMAT_REGEXPS;
	private static final Map<String, Boolean> TIME_KEY_REGEXPS;
	
	static{
		DATE_FORMAT_REGEXPS = new HashMap<>();
		DATE_FORMAT_REGEXPS.put("\\d{8}", "ddMMyyyy");
		DATE_FORMAT_REGEXPS.put("\\d{1,2}-\\d{1,2}-\\d{4}", "dd-MM-yyyy");
		DATE_FORMAT_REGEXPS.put("\\d{4}-\\d{1,2}-\\d{1,2}", "yyyy-MM-dd");
		DATE_FORMAT_REGEXPS.put("\\d{1,2}/\\d{1,2}/\\d{4}", "dd/MM/yyyy");
		DATE_FORMAT_REGEXPS.put("\\d{4}/\\d{1,2}/\\d{1,2}", "yyyy/MM/dd");
		DATE_FORMAT_REGEXPS.put("\\d{12}", "yyyyMMddHHmm");
		DATE_FORMAT_REGEXPS.put("\\d{8}\\s\\d{4}", "yyyyMMdd HHmm");
		DATE_FORMAT_REGEXPS.put("\\d{4}\\s\\d{8}", "HHmm yyyyMMdd");
		DATE_FORMAT_REGEXPS.put("\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}", "dd-MM-yyyy HH:mm");
		DATE_FORMAT_REGEXPS.put("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}", "yyyy-MM-dd HH:mm");
		DATE_FORMAT_REGEXPS.put("\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}", "dd/MM/yyyy HH:mm");
		DATE_FORMAT_REGEXPS.put("\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}", "yyyy/MM/dd HH:mm");
		DATE_FORMAT_REGEXPS.put("\\d{1,2}:\\d{2}\\s\\d{1,2}-\\d{1,2}-\\d{4}", "HH:mm dd-MM-yyyy");
		DATE_FORMAT_REGEXPS.put("\\d{1,2}:\\d{2}\\s\\d{4}-\\d{1,2}-\\d{1,2}", "HH:mm yyyy-MM-dd");
		DATE_FORMAT_REGEXPS.put("\\d{1,2}:\\d{2}\\s\\d{1,2}/\\d{1,2}/\\d{4}", "HH:mm dd/MM/yyyy");
		DATE_FORMAT_REGEXPS.put("\\d{1,2}:\\d{2}\\s\\d{4}/\\d{1,2}/\\d{1,2}", "HH:mm yyyy/MM/dd");
		
		// below are the match for seconds, no need for this project
//	    DATE_FORMAT_REGEXPS.put("^\\d{14}$", "yyyyMMddHHmmss");
//	    DATE_FORMAT_REGEXPS.put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
//	    DATE_FORMAT_REGEXPS.put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
//	    DATE_FORMAT_REGEXPS.put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
//	    DATE_FORMAT_REGEXPS.put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
//	    DATE_FORMAT_REGEXPS.put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
//	    DATE_FORMAT_REGEXPS.put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
//	    DATE_FORMAT_REGEXPS.put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
		
		TIME_KEY_REGEXPS = new HashMap<>();
		TIME_KEY_REGEXPS.put("startdate", true);
		TIME_KEY_REGEXPS.put("enddate", false);
		TIME_KEY_REGEXPS.put("start\\w{0,3} by", true);
		TIME_KEY_REGEXPS.put("beg[iau]n\\w{0,4} by", true);
		TIME_KEY_REGEXPS.put("end\\w{0,3} at", false);
		TIME_KEY_REGEXPS.put("finish\\w{0,3} at", false);
		TIME_KEY_REGEXPS.put("from", true);
		TIME_KEY_REGEXPS.put("until", false);
		TIME_KEY_REGEXPS.put("after", true);
		TIME_KEY_REGEXPS.put("before", false);
		TIME_KEY_REGEXPS.put("by", false);
		TIME_KEY_REGEXPS.put("at", true);
		TIME_KEY_REGEXPS.put("due", false);
		
	}
}
