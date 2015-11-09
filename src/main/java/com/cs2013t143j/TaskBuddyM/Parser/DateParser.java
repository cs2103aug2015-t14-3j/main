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

//@@author A0145680A

/*
 * Date Parser is the combination of local date parser and natty parser
 * 
 * local parser parses the string such as ddmmyyyy, dd/mm/yyyy and etc
 * natty parser parses implicit date-related information
 */
public class DateParser {

	private Map<String,String> resDict;
	
	// position of date-related information in the input string
	private int nattyAbsPosition;
	private int localAbsPostion;
	
	// nattyParsedDateMatch is used for removing the date-related info after parsing
	private List<String> nattyParsedDateMatch;
	// these variables are save to avoid re-compute result from local parser 
	private String localParsedDateResult;
	private String localParsedDateMatch;
	private String localParsedDateRegexp;
	
	// there are two sets of regex used to trim the result string
	// this int indicates which set we need to use
	private int TRIM_SET_ID;
	
	private static final Logger logger =
	        Logger.getLogger(DateParser.class.getName());
	
	public DateParser(){
		nattyParsedDateMatch = new ArrayList<>();
		TRIM_SET_ID = 2;
	}
	
	public DateParser(Map dict){
		nattyParsedDateMatch = new ArrayList<>();
		resDict = dict;
		TRIM_SET_ID = 2;
	}
	
	public Map parse(Map dict) throws TooManyDateFoundException {
		resDict = dict;
		resDict.put("startDate", null);
		resDict.put("endDate", null);		
		parseDate();
		return resDict;
	}
	
	/**
	 * The input is firstly parsed by local parser and then parse to Natty parser
	 * 
	 */
	private void parseDate() throws TooManyDateFoundException {
		logger.entering(getClass().getName(), "parseDate");
		// user input is in description
		String description = (String)resDict.get("description");
		String oldDescription = new String(description);
		Map<String, String> localizedParseResult = matchDateByLocalFormat(description);
		switch (localizedParseResult.size()) {
			case 0:
				logger.log(Level.INFO, "local parser find 0");
				parseDateByNatty(0);
				trimDescriptionByMatch(description);
				trimResDescription(TRIM_SET_ID);
				break;
			case 1:
				logger.log(Level.INFO, "local parser find 1");
				// only 1 object in localizedParseResult
				for (String d : localizedParseResult.keySet()) {
					localParsedDateResult = d;
				}
				determineStartOrEndDate(description, localParsedDateResult);
				// Natty has problem to parse localized date format, therefore hide it with stub string
				replaceLocalSingleMatchWithStub();
				parseDateByNatty(1);
				resDict.put("description", oldDescription);
				trimDescriptionByMatch(description);		
				trimResDescription(TRIM_SET_ID);
				break;
			case 2:
				logger.log(Level.INFO, "local parser find 2");
				// 2 is the max we should get, therefore no need to pass to Natty parser
				setDateByArrayOrder(localizedParseResult);
				trimResDescription(TRIM_SET_ID);
				break;
			default:
				// throw error, when there are more than 2 date-related info found
				logger.log(Level.WARNING, "local parser find many");
				throw new TooManyDateFoundException("More Than 2 date has been found in LocalParser");
		}
		logger.exiting(getClass().getName(), "parseDate");
	}

	/*
	 * Using matching to cut off natty matched description
	 * and clean some remanents that missed by Natty
	 * @param description the string we are going to trim
	 */
	private void trimDescriptionByMatch(String description) {
		String newDes;
		if(nattyParsedDateMatch.size() > 0){		
			for(String r : nattyParsedDateMatch){
				newDes = (String)resDict.get("description").replace(r, "");
				resDict.put("description",newDes);
			}		
			newDes = (String)resDict.get("description");
			newDes = newDes.replace("before", "");
			newDes = newDes.replace("after", "");
			newDes = newDes.replace("later", "");
			resDict.put("description",newDes);
			TRIM_SET_ID = 1;
			nattyParsedDateMatch = new ArrayList<>();
		}
	}
	
	/**
	 * Set start date and end date based on how many local parser found and how many Natty found 
	 * @param lastMatch represent how many date that local parser found
	 * @throws TooManyDateFoundException
	 */
	private void parseDateByNatty(int lastMatch) throws TooManyDateFoundException {
		String originalDescription = (String)resDict.get("description");
		// prevent Natty from missing parsing the string with number
		replaceWordContainBothNumberAndAlphabetWithStub();
		String description = (String)resDict.get("description");
		List<String> NattyParseResult = matchDateByNatty(description);
		int resultSize = NattyParseResult.size();
		if(resultSize == 0){
			logger.log(Level.INFO, "Natty find 0 date");
		}else if (resultSize > 2) {
			logger.log(Level.WARNING, "Natty find many date");
			throw new TooManyDateFoundException("More Than 2 date has been found in NattyParser");
		}else if (lastMatch == 0 && resultSize == 1) {
			logger.log(Level.INFO, "parse Natty case 01");
			determineStartOrEndDate(description, NattyParseResult.get(0));
		}else if (lastMatch == 0 && resultSize == 2) {
			logger.log(Level.INFO, "parse Natty case 02");
			setDateByArrayOrder(NattyParseResult);
		}else if (lastMatch == 1 && resultSize == 1) {
			logger.log(Level.INFO, "parse Natty case 11");
			setDateByPosition(NattyParseResult);		
		}else{
			logger.log(Level.INFO, "parse Natty case 12");
			assert lastMatch == 1 : "LastMatch is "+lastMatch;
			assert resultSize == 2 : "resultSize is "+resultSize;
			setDateByComparison(description, NattyParseResult);
		}
		resDict.put("description",originalDescription);
	}

	private void setDateByComparison(String description, List<String> NattyParseResult)
			throws TooManyDateFoundException {
		Map<String, String> localizedParseResult = matchDateByLocalFormat(description);
		for (String date : localizedParseResult.keySet()) {
			String trimedDescription = description.replace(date, "");
			List<String> newNattyParseResult = matchDateByNatty(trimedDescription);
			if(newNattyParseResult.get(0).equals(NattyParseResult.get(0))){
				resDict.put(STARTDATE, NattyParseResult.get(0));
				resDict.put(ENDDATE, date);
			}else{
				resDict.put(STARTDATE, date);
				resDict.put(ENDDATE, NattyParseResult.get(1));
			}
		}
	}

	private void setDateByPosition(List<String> NattyParseResult) {
		if(localAbsPostion>nattyAbsPosition){
			resDict.put(STARTDATE, NattyParseResult.get(0));
			resDict.put(ENDDATE, localParsedDateResult);
		}else if(localAbsPostion<nattyAbsPosition){
			resDict.put(STARTDATE, localParsedDateResult);
			resDict.put(ENDDATE, NattyParseResult.get(0));
		}
	}

	private void setDateByArrayOrder(List<String> NattyParseResult) {
		for (String dateFormat : NattyParseResult) {
			if(resDict.get(STARTDATE) == null){
				resDict.put(STARTDATE, dateFormat);
			}else if(resDict.get(ENDDATE) == null){
				resDict.put(ENDDATE, dateFormat);
			}
		}
	}
	
	private void setDateByArrayOrder(Map<String, String> localizedParseResult) {
		// set by order, first is startDate second is endDate
		for (String d : localizedParseResult.keySet()) {
			if(resDict.get(STARTDATE) == null){
				resDict.put(STARTDATE, d);
			}else if(resDict.get(ENDDATE) == null){
				resDict.put(ENDDATE, d);
			}
		}
	}
	/**
	 * Match input with DATE_FORMAT_REGEXPS
	 * @param userInput 
	 * @return
	 */
	private Map<String, String> matchDateByLocalFormat(String userInput) {
		Map<String, String> res = new LinkedHashMap<>();
		int lastMatchLength = 0;
	    for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
	    	// only match with longer regexp 
	        if (userInput.toLowerCase().matches("^.*"+regexp+".*$")&&regexp.length()>lastMatchLength) {
	        	lastMatchLength = regexp.length();
	        	localParsedDateRegexp = regexp;
	        	String format = DATE_FORMAT_REGEXPS.get(regexp);
	        	Pattern p = Pattern.compile(regexp);
	            Matcher m = p.matcher(userInput);
	            while(m.find()) {
	            	String matchDateString = m.group();
	            	// convert input date format to unified hh ddMMyyyy format
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
	    return res; 
	}
	
	private List<String> matchDateByNatty(String userInput) throws TooManyDateFoundException{
		Parser p = new Parser();
		String separator = " to ";
		String[] parsePhases = userInput.split(separator,2);
		List<String> returnList = new ArrayList<>();
		if(parsePhases.length==1){
			List<DateGroup> groups = p.parse(userInput);
	    	for(DateGroup group:groups) {
	    		addNattyResultToList(returnList, group);
	    		nattyAbsPosition = group.getAbsolutePosition();
	    	}
		}else if(parsePhases.length == 2){
			int cumulativePosition = 0;
			for(String phase : parsePhases){
				List<DateGroup> groups = p.parse(phase.replaceFirst("from ", ""));
		    	for(DateGroup group:groups) {
		    	  addNattyResultToList(returnList, group);
		    	  nattyAbsPosition = cumulativePosition + group.getAbsolutePosition();
		    	}
		    	cumulativePosition = phase.length()+separator.length();
			}
		}else{
			throw new TooManyDateFoundException("More Than 2 date has been found in NattyParser match");
		}
    	
    	return returnList;
	}

	private void addNattyResultToList(List<String> returnList, DateGroup group) {
		List<Date> dates = group.getDates();
		  for(Date d : dates){
			  String timeString = convertDateToString(d);
			  returnList.add(timeString);
		  }
		  String matchText = group.getText();
		  nattyParsedDateMatch.add(matchText);
	}	
	
	/**
	 * In case of only one date is found, we need to guess it is a start date or end date
	 * @param input
	 * @param dateString
	 */
	private void determineStartOrEndDate(String input, String dateString) {
		for (String regexp : TIME_KEY_REGEXPS.keySet()) {
	        if (input.toLowerCase().matches("^.*"+regexp+".*$")) {
	        	boolean determined = TIME_KEY_REGEXPS.get(regexp);
	        	if(determined){
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
		Pattern p = Pattern.compile(localParsedDateRegexp);
        Matcher m = p.matcher(description);
        while(m.find()) {
        	localParsedDateMatch = m.group();
        	String replacement = generateStubString("x", localParsedDateMatch.length());
        	String newDescription = description.replace(localParsedDateMatch, replacement);
        	localAbsPostion = newDescription.indexOf(replacement);
        	resDict.put("description", newDescription);
        }
	}
	
	private void replaceWordContainBothNumberAndAlphabetWithStub() {
		String description = (String)resDict.get("description");
		Pattern p = Pattern.compile("(([a-zA-Z]\\w*[0-9])|([0-9]\\w*[a-zA-Z]))");
        Matcher m = p.matcher(description);
        String newDescription = description;
        while(m.find()) {
        	String word = m.group();
        	String replacement = generateStubString("y", word.length());
        	newDescription = newDescription.replaceFirst(word, replacement);
        }
        resDict.put("description", newDescription);
	}
	
	private void replaceNumberWithStub() {
		String description = (String)resDict.get("description");
		Pattern p = Pattern.compile("\\b\\d+\\b");
        Matcher m = p.matcher(description);
        String newDescription = description;
        while(m.find()) {
        	String word = m.group();
        	String replacement = generateStubString("z", word.length());
        	newDescription = newDescription.replaceFirst(word, replacement);
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
	
	/**
	 * After Natty's trimming, TRIM_KEY_REGEXPS1 is better for trimming the remanent string
	 * @param TRIM_SET_ID
	 */
	private void trimResDescription(int TRIM_SET_ID) {
		String description = (String)resDict.get("description");
		List<String> trimKeyRegex;
		if(TRIM_SET_ID==1)
			trimKeyRegex = TRIM_KEY_REGEXPS1;
		else
			trimKeyRegex = TRIM_KEY_REGEXPS2;
		
		for(String reg : trimKeyRegex){
			Pattern p = Pattern.compile(reg);
	        Matcher m = p.matcher(description);
	        while(m.find()) {
	        	String matchString = m.group();
	        	description = description.replace(matchString, "");
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
	
	private static final String STARTDATE = "startDate";
	private static final String ENDDATE = "endDate";
	// the local date format regexps for local parser
	private static final Map<String, String> DATE_FORMAT_REGEXPS;
	// some key word that may imply about state date and end date
	private static final Map<String, Boolean> TIME_KEY_REGEXPS;
	// regexps for trimming
	private static final List<String> TRIM_KEY_REGEXPS1;
	private static final List<String> TRIM_KEY_REGEXPS2;
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