package com.cs2013t143j.TaskBuddyM.ParserTest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Parser.CommandParser;
import com.cs2013t143j.TaskBuddyM.Parser.InvalidInputException;

//@@author A0126303W

public class RetrieveShortcutCommandTest {

	private Map<String, String> dictionary = new HashMap<String,String>();
	private String userInput;
	
	@Test
	public void test() throws InvalidInputException {
		retrieveShorcutAdd();
		retrieveShorcutDelete();
		retrieveShorcutSearch();
		retrieveShorcutEdit();
		retrieveShorcutDisplay();
	}
	
	private void retrieveShorcutDisplay() throws InvalidInputException {
		userInput = "d incomplete";
				
		CommandParser cmdParser = new CommandParser(userInput);
		cmdParser.extractShortcutCommand(dictionary);
		
		assertEquals("display",dictionary.get("command"));
		System.out.println(dictionary.get("command"));
	}
	
	private void retrieveShorcutEdit() throws InvalidInputException {
		userInput = "e 1 description aaa";
				
		CommandParser cmdParser = new CommandParser(userInput);
		cmdParser.extractShortcutCommand(dictionary);
		
		assertEquals("edit",dictionary.get("command"));
		System.out.println(dictionary.get("command"));
	}
	
	private void retrieveShorcutSearch() throws InvalidInputException {
		userInput = "s hello";
				
		CommandParser cmdParser = new CommandParser(userInput);
		cmdParser.extractShortcutCommand(dictionary);
		
		assertEquals("search",dictionary.get("command"));
		System.out.println(dictionary.get("command"));
	}

	private void retrieveShorcutDelete() throws InvalidInputException {
		userInput = "r 1";
		
		CommandParser cmdParser = new CommandParser(userInput);
		cmdParser.extractShortcutCommand(dictionary);
		
		assertEquals("delete",dictionary.get("command"));
		System.out.println(dictionary.get("command"));
	}

	private void retrieveShorcutAdd() throws InvalidInputException {
		userInput = "a hello by next week";
				
		CommandParser cmdParser = new CommandParser(userInput);
		cmdParser.extractShortcutCommand(dictionary);
		
		assertEquals("add",dictionary.get("command"));
		System.out.println(dictionary.get("command"));
	}
	
	
}
