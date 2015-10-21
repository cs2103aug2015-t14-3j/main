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
	public void testDelete() {
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
		
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                                                                   No\n2.ccc                                                                   No\n\n", output);
	}
}
