package com.cs2013t143j.TaskBuddyM;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cs2013t143j.TaskBuddyM.Parser.DateParser;
import com.joestelmach.natty.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//    	Parser p = new Parser();
//    	
//    	List<DateGroup> groups = p.parse("the day before next thursday");
//    	for(DateGroup group:groups) {
//    	  List dates = group.getDates();
//    	  int line = group.getLine();
//    	  int column = group.getPosition();
//    	  String matchingValue = group.getText();
//    	  String syntaxTree = group.getSyntaxTree().toStringTree();
//    	  boolean isRecurreing = group.isRecurring();
//    	  Date recursUntil = group.getRecursUntil();
//    	  System.out.println( dates.get(1).toString() );
//    	}
    	Map<String, String> dMap = new HashMap<>();
    	dMap.put("description", "sing began by 10102015");
    	DateParser dateParser = new DateParser(dMap);
    	dMap = dateParser.parse();
    	//for (String t : dMap.keySet()) {
	    	// this check may be redundant 
    		String s = "None";
    		String e = "None";
    		String des = dMap.get("description");
    		if(dMap.get("startDate") != null){
    			s = dMap.get("startDate");
    		}
    		if(dMap.get("endDate") != null){
    			e = dMap.get("endDate");
    		}
    		System.out.println("This dict--------");
    		System.out.println("description is--------"+des);
    		System.out.println("start date is--------"+s);
    		System.out.println("end date is--------"+e);
	    //}
    	
    	
    }
    
    private static List<String> parseDateToStringArray(String userInput){
		Parser p = new Parser();
    	List<DateGroup> groups = p.parse(userInput);
    	List<String> returnList = new ArrayList();
    	for(DateGroup group:groups) {
    	  List<Date> dates = group.getDates();
    	  for(Date d : dates){
    		  String timeString = convertDateToString(d);
    		  returnList.add(timeString);
    	  }
    	}
    	return returnList;
	}
	
	private static String convertDateToString(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // Note: zero based!
        String dateString = String.format("%02d %02d/%02d/%d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        return dateString;
    }
}
