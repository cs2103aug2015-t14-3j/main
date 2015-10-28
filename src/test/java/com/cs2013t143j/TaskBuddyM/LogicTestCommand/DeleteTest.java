package com.cs2013t143j.TaskBuddyM.LogicTestCommand;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Command.AddFloating;
import com.cs2013t143j.TaskBuddyM.Command.Command;
import com.cs2013t143j.TaskBuddyM.Command.DeleteCommand;
import com.cs2013t143j.TaskBuddyM.Command.DisplayAll;
import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class DeleteTest {
	
	private Command command;
	private String output;

	@Test
	public void testDeleteInRange() {
		Logic logic = new Logic(false);
		
		command = new AddFloating("aaa");
		logic.test2(command);
		
		command = new AddFloating("bbb");
		logic.test2(command);
		
		command = new AddFloating("ccc");
		logic.test2(command);
		
		command = new DisplayAll();
		logic.test2(command);
		
		command = new DeleteCommand("2");
		logic.test2(command);
		
		command = new DisplayAll();
		output = logic.test2(command);
		
		assertEquals("Here is your entire schedule:\n1.aaa\t-\t-\tNo\n2.ccc\t-\t-\tNo\n", output);
	}
	
	@Test
	public void testDeleteBelowRange() {
		Logic logic = new Logic(false);
		
		command = new AddFloating("aaa");
		logic.test2(command);
		
		command = new AddFloating("bbb");
		logic.test2(command);
		
		command = new AddFloating("ccc");
		logic.test2(command);
		
		command = new DisplayAll();
		logic.test2(command);
		
		command = new DeleteCommand("-1");
		output = logic.test2(command);
		
		assertEquals("Invalid Index specified\n\n", output);
	}
	
	@Test
	public void testDeleteAboveRange() {
		Logic logic = new Logic(false);
		
		command = new AddFloating("aaa");
		logic.test2(command);
		
		command = new AddFloating("bbb");
		logic.test2(command);
		
		command = new AddFloating("ccc");
		logic.test2(command);
		
		command = new DisplayAll();
		logic.test2(command);
		
		command = new DeleteCommand("4");
		output = logic.test2(command);
		
		assertEquals("Invalid Index specified\n\n", output);
	}
}
