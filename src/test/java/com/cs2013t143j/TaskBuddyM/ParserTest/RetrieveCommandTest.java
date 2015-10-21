package com.cs2013t143j.TaskBuddyM.ParserTest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Parser.CommandParser;

public class RetrieveCommandTest {

	private Map<String, String> dictionary = new HashMap<String,String>();
	private String userInput;
	
	@Test
	public void test() {
		retrieveAdd();
		retrieveDelete();
		retrieveSearch();
		retrieveEdit();
		retrieveDisplay();
	}
	
	private void retrieveDisplay() {
		userInput = "display incomplete";
				
		CommandParser cmdParser = new CommandParser(userInput);
		cmdParser.extractCommand(dictionary);
		
		assertEquals("display",dictionary.get("command"));
		System.out.println(dictionary.get("command"));
	}
	
	private void retrieveEdit() {
		userInput = "edit 1 description aaa";
				
		CommandParser cmdParser = new CommandParser(userInput);
		cmdParser.extractCommand(dictionary);
		
		assertEquals("edit",dictionary.get("command"));
		System.out.println(dictionary.get("command"));
	}
	
	private void retrieveSearch() {
		userInput = "search hello";
				
		CommandParser cmdParser = new CommandParser(userInput);
		cmdParser.extractCommand(dictionary);
		
		assertEquals("search",dictionary.get("command"));
		System.out.println(dictionary.get("command"));
	}

	private void retrieveDelete() {
		userInput = "delete 1";
		
		CommandParser cmdParser = new CommandParser(userInput);
		cmdParser.extractCommand(dictionary);
		
		assertEquals("delete",dictionary.get("command"));
		System.out.println(dictionary.get("command"));
	}

	private void retrieveAdd() {
		userInput = "add hello by next week";
				
		CommandParser cmdParser = new CommandParser(userInput);
		cmdParser.extractCommand(dictionary);
		
		assertEquals("add",dictionary.get("command"));
		System.out.println(dictionary.get("command"));
	}
	
	
}
