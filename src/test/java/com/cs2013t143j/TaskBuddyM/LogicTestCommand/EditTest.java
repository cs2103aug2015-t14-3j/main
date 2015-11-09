package com.cs2013t143j.TaskBuddyM.LogicTestCommand;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Command.AddFloating;
import com.cs2013t143j.TaskBuddyM.Command.Command;
import com.cs2013t143j.TaskBuddyM.Command.DisplayAll;
import com.cs2013t143j.TaskBuddyM.Command.EditDescription;
import com.cs2013t143j.TaskBuddyM.Command.EditEnd;
import com.cs2013t143j.TaskBuddyM.Command.EditStart;
import com.cs2013t143j.TaskBuddyM.Logic.Logic;

//@@author A0121327U
public class EditTest {
	
	private Command command;
	private String output;

	@Test
	public void testEditDescription() {
		Logic logic = new Logic(false);
		
		command = new AddFloating("aaa");
		logic.test2(command);
		
		command = new AddFloating("bbb");
		logic.test2(command);
		
		command = new DisplayAll();
		logic.test2(command);
		
		command = new EditDescription("1", "ccc");
		output = logic.test2(command);
		
		assertEquals("Edited task no.1 description to ccc\n1.bbb\t-\t-\n2.ccc\t-\t-\n", output);
	}
	
	@Test
	public void testEditStart() {
		Logic logic = new Logic(false);
		
		command = new AddFloating("aaa");
		logic.test2(command);
		
		command = new AddFloating("bbb");
		logic.test2(command);
		
		command = new DisplayAll();
		logic.test2(command);
		
		command = new EditStart("1", "20/10/2015");
		output = logic.test2(command);
		
		assertEquals("Edited task no.1 start date to 20/10/2015\n1.aaa\t20 Oct 2015 23:00\t-\n2.bbb\t-\t-\n", output);
	}
	
	@Test
	public void testEditEnd() {
		Logic logic = new Logic(false);
		
		command = new AddFloating("aaa");
		logic.test2(command);
		
		command = new AddFloating("bbb");
		logic.test2(command);
		
		command = new DisplayAll();
		logic.test2(command);
		
		command = new EditEnd("1", "21/10/2015");
		output = logic.test2(command);
		
		assertEquals("Edited task no.1 end date to 21/10/2015\n1.aaa\t-\t21 Oct 2015 23:00\n2.bbb\t-\t-\n", output);
	}
}
