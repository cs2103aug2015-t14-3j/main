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
		
		assertEquals("Here is your entire schedule:\n1.aaa\t-\t-\tNo\n", output);

	}
	
	@Test
	public void testAddEvent() {
		Logic logic = new Logic(false);
		
		command = new AddEvent("aaa", "20/10/2015", "21/10/2015");
		logic.test2(command);

		command = new DisplayAll();
		output = logic.test2(command);
		
		assertEquals("Here is your entire schedule:\n1.aaa\t20-10-2015 23:00\t21-10-2015 23:00\tNo\n", output);

	}
	
	@Test
	public void testAddDeadline() {
		Logic logic = new Logic(false);
		
		command = new AddDeadline("aaa", "21/10/2015");
		logic.test2(command);
		
		command = new DisplayAll();
		output = logic.test2(command);
		
		assertEquals("Here is your entire schedule:\n1.aaa\t-\t21-10-2015 23:00\tNo\n", output);

	}
	
	@Test
	public void testInvalidDescription() {
		Logic logic = new Logic(false);
		
		command = new AddFloating(" ");
		output = logic.test2(command);
		
		assertEquals("com.cs2013t143j.TaskBuddyM.Command.CommandAttributeError: Invalid Description(Cannot be empty)", output);
	}
	
	@Test
	public void testEmptyDate() {
		Logic logic = new Logic(false);
		
		command = new AddEvent("aaa", " ", null);
		output = logic.test2(command);
		
		assertEquals("com.cs2013t143j.TaskBuddyM.Command.CommandAttributeError: Invalid Start Date", output);
	}
	
	@Test
	public void testInvalidDateFormat() {
		Logic logic = new Logic(false);
		
		command = new AddDeadline("aaa", "2/10/2015");
		output = logic.test2(command);
		
		assertEquals("com.cs2013t143j.TaskBuddyM.Command.CommandAttributeError: Invalid date format(Should be dd/mm/yyyy)", output);
	}
}
