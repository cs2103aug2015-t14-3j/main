package com.cs2013t143j.TaskBuddyM.Parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import com.joestelmach.natty.generated.DateParser_NumericRules.int_00_to_23_optional_prefix_return;

public class DateParser {

	private Map<String,String> resDict;
	
	private String format;
	private int nattyAbsPosition;
	private int localAbsPostion;
	private List<String> nattyParsedDateMatch;
	private String localParsedDateResult;
	private String localParsedDateMatch;
	private String localParsedDateRegexp;
	
	private List<String> wordsContainBothNumberAndAlphabet;
	
	private static final Logger logger =
	        Logger.getLogger(DateParser.class.getName());
	
	public DateParser(Map dict){
		wordsContainBothNumberAndAlphabet = new ArrayList<>();
		nattyParsedDateMatch = new ArrayList<>();
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
		String newDes = null;
		int setid = 2;
		switch (localizedParseResult.size()) {
			case 0:
				logger.log(Level.INFO, "parse case 0");
				parseDateByNatty(0);
				
				if(nattyParsedDateMatch.size() > 0){
					
					for(String r : nattyParsedDateMatch){
						System.out.println("Natty trim is " + r);
						newDes = (String)resDict.get("description").replace(r, "");
						System.out.println("Natty trim res is " + newDes);
						resDict.put("description",newDes);
					}		
					// temp quick fix
					newDes = (String)resDict.get("description").replace("before", "");
					newDes = (String)resDict.get("description").replace("after", "");
					resDict.put("description",newDes);
					setid = 1;
					nattyParsedDateMatch = new ArrayList<>();
				}				
				trimResDescription(setid);
				break;
			case 1:
				logger.log(Level.INFO, "parse case 1");
				for (String d : localizedParseResult.keySet()) {
					localParsedDateResult = d;
				}
				determineStartOrEndDate(description, localParsedDateResult);
				replaceLocalSingleMatchWithStub();
				parseDateByNatty(1);
				if(nattyParsedDateMatch.size() > 0){
					
					for(String r : nattyParsedDateMatch){
						System.out.println("Natty trim is " + r);
						newDes = (String)resDict.get("description").replace(r, "");
						System.out.println("Natty trim res is " + newDes);
						resDict.put("description",newDes);
					}		
					// temp quick fix
					newDes = (String)resDict.get("description").replace("before", "");
					newDes = (String)resDict.get("description").replace("after", "");
					resDict.put("description",newDes);
					setid = 1;
					nattyParsedDateMatch = new ArrayList<>();
				}else{
					resDict.put("description",description);
				}
				trimResDescription(setid);
				break;
			case 2:
				logger.log(Level.INFO, "parse case 2");
				// set by order, first is startDate second is endDate
				for (String d : localizedParseResult.keySet()) {
					if(resDict.get(STARTDATE) == null){
						resDict.put(STARTDATE, d);
					}else if(resDict.get(ENDDATE) == null){
						resDict.put(ENDDATE, d);
					}
				}
				trimResDescription(setid);
				break;
			default:
				// throw error
				logger.log(Level.WARNING, "parse case default");
				throw new TooManyDateFoundException("More Than 2 date has been found in LocalParser");
		}
		logger.exiting(getClass().getName(), "parseDate");
	}
	
	private void parseDateByNatty(int lastMatch) throws TooManyDateFoundException {
		String originalDescription = (String)resDict.get("description");
		System.out.println(originalDescription);
		replaceWordContainBothNumberAndAlphabetWithStub();
		String description = (String)resDict.get("description");
		List<String> NattyParseResult = matchDateByNatty(description);
		int resultSize = NattyParseResult.size();
		if(resultSize == 0){
			logger.log(Level.INFO, "parse Natty case result size is 0");
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
			if(localAbsPostion>nattyAbsPosition){
				System.out.println("checkpoint1");
				resDict.put(STARTDATE, NattyParseResult.get(0));
				resDict.put(ENDDATE, localParsedDateResult);
			}else if(localAbsPostion<nattyAbsPosition){
				System.out.println("checkpoint2");
				resDict.put(STARTDATE, localParsedDateResult);
				resDict.put(ENDDATE, NattyParseResult.get(0));
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
		
//		if(wordsContainBothNumberAndAlphabet.size()>0){
//			for(String word : wordsContainBothNumberAndAlphabet){
//				
//			}
//		}
		System.out.println(originalDescription);
		resDict.put("description",originalDescription);
	}
	
	private Map<String, String> matchDateByLocalFormat(String userInput) {
		Map<String, String> res = new LinkedHashMap<>();
		int lastMatchLength = 0;
	    for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
	    	// this check may be redundant 
	        if (userInput.toLowerCase().matches("^.*"+regexp+".*$")&&regexp.length()>lastMatchLength) {
	        	lastMatchLength = regexp.length();
	        	System.out.println("hhhh1");
	        	System.out.println(regexp);
	        	localParsedDateRegexp = regexp;
	        	String format = DATE_FORMAT_REGEXPS.get(regexp);
	        	Pattern p = Pattern.compile(regexp);
	            Matcher m = p.matcher(userInput);
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
	
	private List<String> matchDateByNatty(String userInput) throws TooManyDateFoundException{
		Parser p = new Parser();
		String[] parsePhases = userInput.split(" to ",2);
		List<String> returnList = new ArrayList<>();
		if(parsePhases.length==1){
			List<DateGroup> groups = p.parse(userInput);
	    	for(DateGroup group:groups) {
	    	  List<Date> dates = group.getDates();
	    	  for(Date d : dates){
	    		  String timeString = convertDateToString(d);
	    		  returnList.add(timeString);
	    	  }
	    	  nattyParsedDateMatch.add(group.getText());
	    	  nattyAbsPosition = group.getAbsolutePosition();
	    	}
		}else if(parsePhases.length == 2){
			//ArrayUtils.reverse(parsePhases);
			for(String phase : parsePhases){
				System.out.println("Natty MultiMatch is "+phase);
				List<DateGroup> groups = p.parse(phase.replaceFirst("from ", ""));
		    	for(DateGroup group:groups) {
		    	  List<Date> dates = group.getDates();
		    	  for(Date d : dates){
		    		  String timeString = convertDateToString(d);
		    		  returnList.add(timeString);
		    	  }
		    	  String matchText = group.getText();
		    	  nattyParsedDateMatch.add(matchText);
		    	  System.out.println("Natty MultiMatched is "+matchText);
		    	  nattyAbsPosition = group.getAbsolutePosition();
		    	}
			}
			//Collections.reverse(returnList);
		}else{
			throw new TooManyDateFoundException("More Than 2 date has been found in NattyParser match");
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
	
	private void replaceLocalSingleMatchWithStub() {
		String description = (String)resDict.get("description");
		System.out.println("old des is " + description);
		Pattern p = Pattern.compile(localParsedDateRegexp);
        Matcher m = p.matcher(description);
        while(m.find()) {
        	localParsedDateMatch = m.group();
        	String replacement = generateStubString("x", localParsedDateMatch.length());
        	String newDescription = description.replace(localParsedDateMatch, replacement);
        	localAbsPostion = newDescription.indexOf(replacement);
        	System.out.println("newDes is " + newDescription);
        	resDict.put("description", newDescription);
        }
	}
	
	private void replaceWordContainBothNumberAndAlphabetWithStub() {
		String description = (String)resDict.get("description");
		System.out.println("old des2 is " + description);
		Pattern p = Pattern.compile("(([a-zA-Z]\\w*[0-9])|([0-9]\\w*[a-zA-Z]))");
        Matcher m = p.matcher(description);
        String newDescription = description;
        while(m.find()) {
        	String word = m.group();
        	wordsContainBothNumberAndAlphabet.add(word);
        	String replacement = generateStubString("y", word.length());
        	newDescription = newDescription.replaceFirst(word, replacement);
        	System.out.println("newDes2 is " + newDescription);
        }
        resDict.put("description", newDescription);
	}
	
	private String generateStubString(String repeater, int repeatTime){
		String replacement = "";
		for(int i = 0; i < repeatTime; i++){
    		replacement += repeater;
    	}
		return replacement;
	}
	
	private void trimResDescription(int setid) {
		String description = (String)resDict.get("description");
		if(setid==1){
			for(String reg : TRIM_KEY_REGEXPS1){
				Pattern p = Pattern.compile(reg);
		        Matcher m = p.matcher(description);
		        while(m.find()) {
		        	System.out.println("Trim key is " + reg);
		        	String matchString = m.group();
		        	description = description.replace(matchString, "");
		        }
			}
		}
		
		if(setid==2){
			for(String reg : TRIM_KEY_REGEXPS2){
				Pattern p = Pattern.compile(reg);
		        Matcher m = p.matcher(description);
		        while(m.find()) {
		        	System.out.println("Trim key is " + reg);
		        	String matchString = m.group();
		        	description = description.replace(matchString, "");
		        }
			}
		}
		
		resDict.put("description",description);
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
	private static final List<String> TRIM_KEY_REGEXPS1;
	private static final List<String> TRIM_KEY_REGEXPS2;
//	private static final List<String> NATTY_SEPARATOR_REGEXPS;
	static{
		DATE_FORMAT_REGEXPS = new LinkedHashMap<>();
		DATE_FORMAT_REGEXPS.put("\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}", "dd-MM-yyyy HH:mm");
		DATE_FORMAT_REGEXPS.put("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}", "yyyy-MM-dd HH:mm");
		DATE_FORMAT_REGEXPS.put("\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}", "dd/MM/yyyy HH:mm");
		DATE_FORMAT_REGEXPS.put("\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}", "yyyy/MM/dd HH:mm");
		DATE_FORMAT_REGEXPS.put("\\d{1,2}:\\d{2}\\s\\d{1,2}-\\d{1,2}-\\d{4}", "HH:mm dd-MM-yyyy");
		DATE_FORMAT_REGEXPS.put("\\d{1,2}:\\d{2}\\s\\d{4}-\\d{1,2}-\\d{1,2}", "HH:mm yyyy-MM-dd");
		DATE_FORMAT_REGEXPS.put("\\d{1,2}:\\d{2}\\s\\d{1,2}/\\d{1,2}/\\d{4}", "HH:mm dd/MM/yyyy");
		DATE_FORMAT_REGEXPS.put("\\d{1,2}:\\d{2}\\s\\d{4}/\\d{1,2}/\\d{1,2}", "HH:mm yyyy/MM/dd");
		DATE_FORMAT_REGEXPS.put("\\d{8}\\s\\d{4}", "yyyyMMdd HHmm");
		DATE_FORMAT_REGEXPS.put("\\d{4}\\s\\d{8}", "HHmm yyyyMMdd");
		DATE_FORMAT_REGEXPS.put("\\d{8}", "ddMMyyyy");
		DATE_FORMAT_REGEXPS.put("\\d{1,2}-\\d{1,2}-\\d{4}", "dd-MM-yyyy");
		DATE_FORMAT_REGEXPS.put("\\d{4}-\\d{1,2}-\\d{1,2}", "yyyy-MM-dd");
		DATE_FORMAT_REGEXPS.put("\\d{1,2}/\\d{1,2}/\\d{4}", "dd/MM/yyyy");
		DATE_FORMAT_REGEXPS.put("\\d{4}/\\d{1,2}/\\d{1,2}", "yyyy/MM/dd");
		DATE_FORMAT_REGEXPS.put("\\d{12}", "yyyyMMddHHmm");
		
		
		
		// below are the match for seconds, no need for this project
//	    DATE_FORMAT_REGEXPS.put("^\\d{14}$", "yyyyMMddHHmmss");
//	    DATE_FORMAT_REGEXPS.put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
//	    DATE_FORMAT_REGEXPS.put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
//	    DATE_FORMAT_REGEXPS.put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
//	    DATE_FORMAT_REGEXPS.put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
//	    DATE_FORMAT_REGEXPS.put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
//	    DATE_FORMAT_REGEXPS.put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
//	    DATE_FORMAT_REGEXPS.put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
		
		TIME_KEY_REGEXPS = new LinkedHashMap<>();
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
		TIME_KEY_REGEXPS.put("on", true);
		
		TRIM_KEY_REGEXPS1 = new ArrayList<>();
		for(String i : DATE_FORMAT_REGEXPS.keySet()){
			for(String j : DATE_FORMAT_REGEXPS.keySet()){
				TRIM_KEY_REGEXPS1.add("from\\s+("+i+")?\\s+to\\s+("+j+")?");
				TRIM_KEY_REGEXPS1.add("("+i+")?\\s+to\\s+("+j+")?");
				TRIM_KEY_REGEXPS1.add("from\\s+("+i+")?\\s+until\\s+("+j+")?");
				TRIM_KEY_REGEXPS1.add("("+i+")?\\s+until\\s+("+j+")?");
			}
		}
		for(String i : DATE_FORMAT_REGEXPS.keySet()){
			TRIM_KEY_REGEXPS1.add("start\\w{0,3}\\s+by\\s+("+i+")?");
			TRIM_KEY_REGEXPS1.add("start\\w{0,3}\\s+on\\s+("+i+")?");
			TRIM_KEY_REGEXPS1.add("start\\w{0,3}\\s+at\\s+("+i+")?");
			TRIM_KEY_REGEXPS1.add("beg[iau]n\\w{0,4}\\s+by\\s+("+i+")?");
			TRIM_KEY_REGEXPS1.add("beg[iau]n\\w{0,4}\\s+on\\s+("+i+")?");
			TRIM_KEY_REGEXPS1.add("beg[iau]n\\w{0,4}\\s+at\\s+("+i+")?");
			TRIM_KEY_REGEXPS1.add("end\\w{0,3}\\s+at\\s+("+i+")?");
			TRIM_KEY_REGEXPS1.add("finish\\w{0,3}\\s+at\\s+("+i+")?");
			TRIM_KEY_REGEXPS1.add("from\\s+("+i+")?");
			TRIM_KEY_REGEXPS1.add("until\\s+("+i+")?");
			TRIM_KEY_REGEXPS1.add("after\\s+("+i+")?");
			TRIM_KEY_REGEXPS1.add("before\\s+("+i+")?");
			TRIM_KEY_REGEXPS1.add("by\\s+("+i+")?");
			TRIM_KEY_REGEXPS1.add("at\\s+("+i+")?");
			TRIM_KEY_REGEXPS1.add("due\\s+("+i+")?");
			TRIM_KEY_REGEXPS1.add("on\\s+("+i+")?");
			TRIM_KEY_REGEXPS1.add(i);
		}
		
		TRIM_KEY_REGEXPS2 = new ArrayList<>();
		for(String i : DATE_FORMAT_REGEXPS.keySet()){
			for(String j : DATE_FORMAT_REGEXPS.keySet()){
				TRIM_KEY_REGEXPS2.add("from\\s+("+i+"){1}\\s+to\\s+("+j+"){1}");
				TRIM_KEY_REGEXPS2.add("("+i+"){1}\\s+to\\s+("+j+"){1}");
				TRIM_KEY_REGEXPS2.add("from\\s+("+i+"){1}\\s+until\\s+("+j+"){1}");
				TRIM_KEY_REGEXPS2.add("("+i+"){1}\\s+until\\s+("+j+"){1}");
			}
		}
		for(String i : DATE_FORMAT_REGEXPS.keySet()){
			TRIM_KEY_REGEXPS2.add("start\\w{0,3}\\s+by\\s+("+i+"){1}");
			TRIM_KEY_REGEXPS2.add("start\\w{0,3}\\s+on\\s+("+i+"){1}");
			TRIM_KEY_REGEXPS2.add("start\\w{0,3}\\s+at\\s+("+i+"){1}");
			TRIM_KEY_REGEXPS2.add("beg[iau]n\\w{0,4}\\s+by\\s+("+i+"){1}");
			TRIM_KEY_REGEXPS2.add("beg[iau]n\\w{0,4}\\s+on\\s+("+i+"){1}");
			TRIM_KEY_REGEXPS2.add("beg[iau]n\\w{0,4}\\s+at\\s+("+i+"){1}");
			TRIM_KEY_REGEXPS2.add("end\\w{0,3}\\s+at\\s+("+i+"){1}");
			TRIM_KEY_REGEXPS2.add("finish\\w{0,3}\\s+at\\s+("+i+"){1}");
			TRIM_KEY_REGEXPS2.add("from\\s+("+i+"){1}");
			TRIM_KEY_REGEXPS2.add("until\\s+("+i+"){1}");
			TRIM_KEY_REGEXPS2.add("after\\s+("+i+"){1}");
			TRIM_KEY_REGEXPS2.add("before\\s+("+i+"){1}");
			TRIM_KEY_REGEXPS2.add("by\\s+("+i+"){1}");
			TRIM_KEY_REGEXPS2.add("at\\s+("+i+"){1}");
			TRIM_KEY_REGEXPS2.add("due\\s+("+i+"){1}");
			TRIM_KEY_REGEXPS2.add("on\\s+("+i+"){1}");
			TRIM_KEY_REGEXPS2.add(i);
		}
		
		
	}
}