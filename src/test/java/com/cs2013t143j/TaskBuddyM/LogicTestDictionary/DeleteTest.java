package com.cs2013t143j.TaskBuddyM.LogicTestDictionary;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class DeleteTest {
	
	private Map<String, String> command = new HashMap<String,String>();
	private String output;

	@Test
	public void testDelete() {
		Logic logic = new Logic(false);
		
		command.put("command", "add");
		command.put("description", "aaa");
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "bbb");
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "ccc");
		logic.test(command);
		command.clear();
		
		command.put("command", "display");
		logic.test(command);
		command.clear();
		
		command.put("command", "delete");
		command.put("index", "2");
		logic.test(command);
		command.clear();

		command.put("command", "display");
		output = logic.test(command);
		command.clear();
		
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                                                                   No\n2.ccc                                                                   No\n\n", output);
	}
}
