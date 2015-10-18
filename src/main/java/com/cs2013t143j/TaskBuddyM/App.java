package com.cs2013t143j.TaskBuddyM;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    	List<String> list = parseDateToStringArray("the day before next thursday");
    	for(String s : list){
    		System.out.println(s);
    	}
    	
    	
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
