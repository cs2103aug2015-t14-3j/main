package com.cs2013t143j.TaskBuddyM.LogicTestDictionary;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class EditTest {
	
	private Map<String, String> command = new HashMap<String,String>();
	private String output;

	@Test
	public void testEditDescription() {
		Logic logic = new Logic(false);
		
		command.put("command", "add");
		command.put("description", "aaa");
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "bbb");
		logic.test(command);
		command.clear();
		
		command.put("command", "display");
		logic.test(command);
		command.clear();
		
		command.put("command", "edit");
		command.put("index","1");
		command.put("field", "description");
		command.put("newValue", "ccc");
		logic.test(command);
		command.clear();
		
		command.put("command", "display");
		output = logic.test(command);
		command.clear();
		
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.ccc                                                                   No\n2.bbb                                                                   No\n\n", output);
	}
	
	@Test
	public void testEditStart() {
		Logic logic = new Logic(false);
		
		command.put("command", "add");
		command.put("description", "aaa");
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "bbb");
		logic.test(command);
		command.clear();
		
		command.put("command", "display");
		logic.test(command);
		command.clear();
		
		command.put("command", "edit");
		command.put("index","1");
		command.put("field", "start date");
		command.put("newValue", "20/10/2015");
		logic.test(command);
		command.clear();
		
		command.put("command", "display");
		output = logic.test(command);
		command.clear();
		
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 20-10-2015 23:59                                  No\n2.bbb                                                                   No\n\n", output);
	}
	
	@Test
	public void testEditEnd() {
		Logic logic = new Logic(false);
		
		command.put("command", "add");
		command.put("description", "aaa");
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "bbb");
		logic.test(command);
		command.clear();
		
		command.put("command", "display");
		logic.test(command);
		command.clear();
		
		command.put("command", "edit");
		command.put("index","1");
		command.put("field", "end date");
		command.put("newValue", "21/10/2015");
		logic.test(command);
		command.clear();
		
		command.put("command", "display");
		output = logic.test(command);
		command.clear();
		
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                                          21-10-2015 23:59         No\n2.bbb                                                                   No\n\n", output);
	}
}
