package com.cs2013t143j.TaskBuddyM;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cs2013t143j.TaskBuddyM.Parser.DateParser;
import com.cs2013t143j.TaskBuddyM.Parser.TooManyDateFoundException;
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
//    	int groupid = 0;
//    	List<DateGroup> groups = p.parse("sing from today to xxxxxxx");
//    	for(DateGroup group:groups) {
//    	  List dates = group.getDates();
//    	  int line = group.getLine();
//    	  int column = group.getPosition();
//    	  String matchingValue = group.getText();
//    	  String syntaxTree = group.getSyntaxTree().toStringTree();
//    	  boolean isRecurreing = group.isRecurring();
//    	  Date recursUntil = group.getRecursUntil();
//    	  
//    	  System.out.println( "DateGroup # " + groupid);
//    	  System.out.println( dates.get(0).toString() );
//    	  //System.out.println( dates.get(1).toString() );
//    	  System.out.println( "line # " + line );
//    	  System.out.println( "column # " + line );
//    	  System.out.println( "matchingValue # " + matchingValue );
//    	  System.out.println( "syntaxTree # " + syntaxTree );
//    	  System.out.println( "AbsPosition # " + group.getAbsolutePosition() );
//
//    	  groupid++;
//    	}
    	Map<String, String> dMap = new HashMap<>();
    	dMap.put("description", "sing");
    	DateParser dateParser = new DateParser(dMap);
    	try {
			dMap = dateParser.parse();
		} catch (TooManyDateFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
		}
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
