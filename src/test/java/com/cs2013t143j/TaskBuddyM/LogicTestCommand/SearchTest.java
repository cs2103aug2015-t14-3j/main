package com.cs2013t143j.TaskBuddyM.LogicTestCommand;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Command.AddFloating;
import com.cs2013t143j.TaskBuddyM.Command.Command;
import com.cs2013t143j.TaskBuddyM.Command.SearchCommand;
import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class SearchTest {
	
	private Command command;
	private String output;

	@Test
	public void testSearch() {
		Logic logic = new Logic(false);
		
		command = new AddFloating("hi");
		logic.test2(command);
		
		command = new AddFloating("hello");
		logic.test2(command);
		
		command = new AddFloating("hello world");
		logic.test2(command);
		
		command = new AddFloating("he-man");
		logic.test2(command);
		
		command = new SearchCommand("he");
		output = logic.test2(command);
		
		assertEquals("Search returned 3 result(s):\n1.hello\t-\t-\tNo\n2.hello world\t-\t-\tNo\n3.he-man\t-\t-\tNo\n", output);
	}

}
