package com.cs2013t143j.TaskBuddyM.LogicTestCommand;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Command.AddDeadline;
import com.cs2013t143j.TaskBuddyM.Command.AddEvent;
import com.cs2013t143j.TaskBuddyM.Command.AddFloating;
import com.cs2013t143j.TaskBuddyM.Command.Command;
import com.cs2013t143j.TaskBuddyM.Command.DisplayAll;
import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class AddTest {
	
	private Command command;
	private String output;

	@Test
	public void testAddFloating() {
		Logic logic = new Logic(false);
		
		command = new AddFloating("aaa");
		output = logic.test2(command);
		//Test add output
		assertEquals("Added new Task to TaskBuddy\n\n", output);

		command = new DisplayAll();
		output = logic.test2(command);
		
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                                                                   No\n\n", output);

	}
	
	@Test
	public void testAddEvent() {
		Logic logic = new Logic(false);
		
		command = new AddEvent("aaa", "20/10/2015", "21/10/2015");
		logic.test2(command);

		command = new DisplayAll();
		output = logic.test2(command);
		
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 20-10-2015 23:00         21-10-2015 23:00         No\n\n", output);

	}
	
	@Test
	public void testAddDeadline() {
		Logic logic = new Logic(false);
		
		command = new AddDeadline("aaa", "21/10/2015");
		logic.test2(command);
		
		command = new DisplayAll();
		output = logic.test2(command);
		
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                                          21-10-2015 23:00         No\n\n", output);

	}

}
