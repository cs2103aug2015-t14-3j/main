package com.cs2013t143j.TaskBuddyM.ParserTest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Parser.CommandParser;
import com.cs2013t143j.TaskBuddyM.Parser.InvalidInputException;

//@@author A0126303W

public class CheckCommandExistsText {
	
	private Map<String, String> dictionary = new HashMap<String,String>();
	private String userInput;
	
	@Test
	public void test() {
		userInput = "aaa project by today";
		
		CommandParser cmdParser = new CommandParser(userInput);
		
		try {
			cmdParser.extractCommand(dictionary);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			assertEquals("Invalid command",e.getMessage());
		}
		
		assertEquals(null,dictionary.get("command"));
		System.out.println(dictionary.get("command"));
	}

}
