package com.cs2013t143j.TaskBuddyM.LogicTestCommand;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Command.AddFloating;
import com.cs2013t143j.TaskBuddyM.Command.Command;
import com.cs2013t143j.TaskBuddyM.Command.DeleteCommand;
import com.cs2013t143j.TaskBuddyM.Command.DisplayAll;
import com.cs2013t143j.TaskBuddyM.Logic.Logic;

//@@author A0121327U
public class DeleteTest {
	
	private Command command;
	private String output;

	@Test
	public void DeleteOne() {
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
		output = logic.test2(command);
		
		assertEquals("Deleted task(s) 2\n1.aaa\t-\t-\n2.ccc\t-\t-\n", output);
	}
	
	@Test
	public void DeleteMultiple() {
		Logic logic = new Logic(false);
		
		command = new AddFloating("aaa");
		logic.test2(command);
		
		command = new AddFloating("bbb");
		logic.test2(command);
		
		command = new AddFloating("ccc");
		logic.test2(command);

		command = new DeleteCommand("1 2");
		output = logic.test2(command);
		assertEquals("Deleted task(s) 1, 2\n1.ccc	-	-\n", output);
	}
	
	@Test
	public void testDeleteInvalidInt() {
		Logic logic = new Logic(false);
		
		command = new AddFloating("aaa");
		logic.test2(command);
		
		command = new DeleteCommand("Hello");
		output = logic.test2(command);
		
		assertEquals("Index provided is not an Integer", output);
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
		
		assertEquals("Index provided must be larger than 0", output);
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
		
		assertEquals("Index provided is larger than last display", output);
	}
}