package com.cs2013t143j.TaskBuddyM;

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
    	Parser p = new Parser();
    	
    	List<DateGroup> groups = p.parse("the day before next thursday");
    	for(DateGroup group:groups) {
    	  List dates = group.getDates();
    	  int line = group.getLine();
    	  int column = group.getPosition();
    	  String matchingValue = group.getText();
    	  String syntaxTree = group.getSyntaxTree().toStringTree();
    	  boolean isRecurreing = group.isRecurring();
    	  Date recursUntil = group.getRecursUntil();
    	  System.out.println( dates.get(1).toString() );
    	}
        
    }
}
