package com.cs2013t143j.TaskBuddyM.LogicTestCommand;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Command.AddDeadline;
import com.cs2013t143j.TaskBuddyM.Command.AddEvent;
import com.cs2013t143j.TaskBuddyM.Command.AddFloating;
import com.cs2013t143j.TaskBuddyM.Command.Command;
import com.cs2013t143j.TaskBuddyM.Command.DisplayAll;
import com.cs2013t143j.TaskBuddyM.Logic.Logic;

//@@author A0121327U
public class AddTest {
	
	private Command command;
	private String output;

	@Test
	public void testAddFloating() {
		Logic logic = new Logic(false);
		
		command = new AddFloating("aaa");
		output = logic.test2(command);
		
		assertEquals("Here is your entire schedule:\n1.aaa\t-\t-\n", output);

	}
	
	@Test
	public void testAddEvent() {
		Logic logic = new Logic(false);
		
		command = new AddEvent("aaa", "20/10/2015", "1/1/2016");
		logic.test2(command);

		command = new DisplayAll();
		output = logic.test2(command);
		
		assertEquals("Here is your entire schedule:\n1.aaa\t20 Oct 2015 23:00\t01 Jan 2016 23:00\n", output);

	}
	
	@Test
	public void testAddDeadline() {
		Logic logic = new Logic(false);
		
		command = new AddDeadline("aaa", "1/10/2015");
		logic.test2(command);
		
		command = new DisplayAll();
		output = logic.test2(command);
		
		assertEquals("Here is your entire schedule:\n1.aaa\t-\t01 Oct 2015 23:00\n", output);

	}
	
	@Test
	public void testInvalidDescription() {
		Logic logic = new Logic(false);
		
		command = new AddFloating(" ");
		output = logic.test2(command);
		
		assertEquals("Invalid Description(Cannot be empty)", output);
	}
	
	@Test
	public void testEmptyDate() {
		Logic logic = new Logic(false);
		
		command = new AddEvent("aaa", " ", null);
		output = logic.test2(command);
		
		assertEquals("Invalid Start Date", output);
	}
}
