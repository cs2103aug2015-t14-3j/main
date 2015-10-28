package com.cs2013t143j.TaskBuddyM.ParserTest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Parser.CommandParser;
import com.cs2013t143j.TaskBuddyM.Parser.InvalidInputException;

public class RetrieveCommandTest {

	private Map<String, String> dictionary = new HashMap<String,String>();
	private String userInput;
	
	@Test
	public void test() throws InvalidInputException {
		retrieveAdd();
		retrieveDelete();
		retrieveSearch();
		retrieveEdit();
		retrieveDisplay();
	}
	
	private void retrieveDisplay() throws InvalidInputException {
		userInput = "display incomplete";
				
		CommandParser cmdParser = new CommandParser(userInput);
		cmdParser.extractCommand(dictionary);
		
		assertEquals("display",dictionary.get("command"));
		System.out.println(dictionary.get("command"));
	}
	
	private void retrieveEdit() throws InvalidInputException {
		userInput = "edit 1 description aaa";
				
		CommandParser cmdParser = new CommandParser(userInput);
		cmdParser.extractCommand(dictionary);
		
		assertEquals("edit",dictionary.get("command"));
		System.out.println(dictionary.get("command"));
	}
	
	private void retrieveSearch() throws InvalidInputException {
		userInput = "search hello";
				
		CommandParser cmdParser = new CommandParser(userInput);
		cmdParser.extractCommand(dictionary);
		
		assertEquals("search",dictionary.get("command"));
		System.out.println(dictionary.get("command"));
	}

	private void retrieveDelete() throws InvalidInputException {
		userInput = "delete 1";
		
		CommandParser cmdParser = new CommandParser(userInput);
		cmdParser.extractCommand(dictionary);
		
		assertEquals("delete",dictionary.get("command"));
		System.out.println(dictionary.get("command"));
	}

	private void retrieveAdd() throws InvalidInputException {
		userInput = "add hello by next week";
				
		CommandParser cmdParser = new CommandParser(userInput);
		cmdParser.extractCommand(dictionary);
		
		assertEquals("add",dictionary.get("command"));
		System.out.println(dictionary.get("command"));
	}
	
	
}
