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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class DateParser {

	private Map<String,String> resDict;
	
	private String format;
	
	private static final Logger logger =
	        Logger.getLogger(DateParser.class.getName());
	
	public DateParser(Map dict){
		resDict = dict;
	}
	
	public Map parse() throws TooManyDateFoundException {
		parseDate();
		return resDict;
	}
	
	public Map parse(Map dict) throws TooManyDateFoundException {
		resDict = dict;
		parseDate();
		return resDict;
	}
	
	private void init() {
		resDict.put("startDate", null);
		resDict.put("endDate", null);
		
		format = null;
	}
	
	private void parseDate() throws TooManyDateFoundException {
		logger.entering(getClass().getName(), "parseDate");
		init();
		String description = (String)resDict.get("description");
		Map<String, String> localizedParseResult = matchDateByLocalFormat(description);
		switch (localizedParseResult.size()) {
			case 0:
				logger.log(Level.INFO, "parse case 0");
				parseDateByNatty(0);
				break;
			case 1:
				logger.log(Level.INFO, "parse case 0");
				String dateString = null;
				for (String d : localizedParseResult.keySet()) {
					dateString = d;
				}
				determineStartOrEndDate(description, dateString);
				parseDateByNatty(1);
				break;
			case 2:
				logger.log(Level.INFO, "parse case 0");
				// set by order, first is startDate second is endDate
				for (String d : localizedParseResult.keySet()) {
					if(resDict.get(STARTDATE) == null){
						resDict.put(STARTDATE, d);
					}else if(resDict.get(ENDDATE) == null){
						resDict.put(ENDDATE, d);
					}
				}
				break;
			default:
				// throw error
				logger.log(Level.WARNING, "parse case default");
				throw new TooManyDateFoundException("More Than 2 date has been found in LocalParser");
		}
		logger.exiting(getClass().getName(), "parseDate");
	}
	
	private void parseDateByNatty(int lastMatch) throws TooManyDateFoundException {
		String description = (String)resDict.get("description");
		List<String> NattyParseResult = matchDateByNatty(description);
		int resultSize = NattyParseResult.size();
		if(resultSize == 0){
			logger.log(Level.INFO, "parse Natty case result size is 0");
			return;
		}else if (resultSize > 2) {
			logger.log(Level.WARNING, "parse Natty case result size greater than 2");
			throw new TooManyDateFoundException("More Than 2 date has been found in NattyParser");
		}else if (lastMatch == 0 && resultSize == 1) {
			logger.log(Level.INFO, "parse Natty case 01");
			determineStartOrEndDate(description, NattyParseResult.get(0));
		}else if (lastMatch == 0 && resultSize == 2) {
			logger.log(Level.INFO, "parse Natty case 02");
			for (String dateFormat : NattyParseResult) {
				if(resDict.get(STARTDATE) == null){
					resDict.put(STARTDATE, dateFormat);
				}else if(resDict.get(ENDDATE) == null){
					resDict.put(ENDDATE, dateFormat);
				}
			}
		}else if (lastMatch == 1 && resultSize == 1) {
			logger.log(Level.INFO, "parse Natty case 11");
			int pivat = description.indexOf("to");		
			if(pivat<0){
				Map<String, String> localizedParseResult = matchDateByLocalFormat(description);
				for (String date : localizedParseResult.keySet()) {
					determineStartOrEndDate(description, date);
				}
				
			}else{
				Map<String, String> localizedParseResult = matchDateByLocalFormat(description);
				for (String date : localizedParseResult.keySet()) {
					int numStringIndex = description.indexOf(date);
					if(pivat-numStringIndex>0){
						resDict.put(STARTDATE, date);
						resDict.put(ENDDATE, NattyParseResult.get(1));
					}else{
						resDict.put(STARTDATE, NattyParseResult.get(0));
						resDict.put(ENDDATE, date);
					}
				}
				
			}			
		}else{
			logger.log(Level.INFO, "parse Natty case 12");
			assert lastMatch == 1 : "LastMatch is "+lastMatch;
			assert resultSize == 2 : "resultSize is "+resultSize;
			//System.out.println("LastMatch is "+ lastMatch+" Resultsize is "+resultSize);
			Map<String, String> localizedParseResult = matchDateByLocalFormat(description);
			for (String date : localizedParseResult.keySet()) {
				String trimedDescription = description.replace(date, "");
				List<String> newNattyParseResult = matchDateByNatty(trimedDescription);
				if(newNattyParseResult.get(0).equals(NattyParseResult.get(0))){
					System.out.println("checkpoint");
					resDict.put(STARTDATE, NattyParseResult.get(0));
					resDict.put(ENDDATE, date);
				}else{
					System.out.println("checkpoint2");
					resDict.put(STARTDATE, date);
					resDict.put(ENDDATE, NattyParseResult.get(1));
				}
			}
		}
	}
	
	public Map<String, String> matchDateByLocalFormat(String dateString) {
		Map<String, String> res = new LinkedHashMap<>();
	    for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
	    	// this check may be redundant 
	        if (dateString.toLowerCase().matches("^.*"+regexp+".*$")) {
	        	System.out.println("hhhh1");
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
						res.put(reformatInputDate, regexp);
					} catch (ParseException e) {
						System.out.println("Invaild SimpleDateFormat: "+e.getMessage());
					}
	            	
	            }
	        }
	    }
	    //System.out.println("res size is "+ res.size());
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
	
	private void determineStartOrEndDate(String input, String dateString) {
		for (String regexp : TIME_KEY_REGEXPS.keySet()) {
	    	// this check may be redundant 
			//System.out.println("Somet1");
			//System.out.println(input.toLowerCase());
	        if (input.toLowerCase().matches("^.*"+regexp+".*$")) {
	        	//System.out.println("Somet2");
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


