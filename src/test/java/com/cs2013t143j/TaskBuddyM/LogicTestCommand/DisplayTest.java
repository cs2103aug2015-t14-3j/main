package com.cs2013t143j.TaskBuddyM.LogicTestCommand;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Command.AddDeadline;
import com.cs2013t143j.TaskBuddyM.Command.AddEvent;
import com.cs2013t143j.TaskBuddyM.Command.AddFloating;
import com.cs2013t143j.TaskBuddyM.Command.Command;
import com.cs2013t143j.TaskBuddyM.Command.DisplayAfter;
import com.cs2013t143j.TaskBuddyM.Command.DisplayAll;
import com.cs2013t143j.TaskBuddyM.Command.DisplayDue;
import com.cs2013t143j.TaskBuddyM.Command.DisplayFloating;
import com.cs2013t143j.TaskBuddyM.Command.DisplayFrom;
import com.cs2013t143j.TaskBuddyM.Command.DisplayOn;
import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class DisplayTest {
	
	private Command command;
	private String output;

	@Test
	public void testDisplayAll() {
		Logic logic = new Logic(false);
		
		command = new AddFloating("aaa");
		logic.test2(command);
		
		command = new AddDeadline("submit report", "21/09/2015");
		logic.test2(command);
		
		command = new AddEvent("recess week", "20/09/2015", "22/09/2015");
		logic.test2(command);

		command = new DisplayAll();
		output = logic.test2(command);
		
		assertEquals("Here is your entire schedule:\n1.submit report\t-\t21-09-2015 23:00\tNo\n2.recess week\t20-09-2015 23:00\t22-09-2015 23:00\tNo\n3.aaa\t-\t-\tNo\n", output);
		
		command = new AddDeadline("bbb", "05/09/2015");
		logic.test2(command);
		
		command = new DisplayAll();
		output = logic.test2(command);
		
		assertEquals("Here is your entire schedule:\n1.bbb\t-\t05-09-2015 23:00\tNo\n2.submit report\t-\t21-09-2015 23:00\tNo\n3.recess week\t20-09-2015 23:00\t22-09-2015 23:00\tNo\n4.aaa\t-\t-\tNo\n", output);
		}
	
	@Test
	public void testDisplayOn() {
		Logic logic = new Logic(false);
		
		command = new AddFloating("aaa");
		logic.test2(command);
		
		command = new AddDeadline("submit report", "21/09/2015");
		logic.test2(command);
		
		command = new AddEvent("recess week", "20/09/2015", "22/09/2015");
		logic.test2(command);
		
		command = new AddEvent("ddd", "10/09/2015", "20/09/2015");
		logic.test2(command);
		
		command = new AddDeadline("bbb", "05/09/2015");
		logic.test2(command);
		
		command = new AddDeadline("ccc", "20/09/2015");
		logic.test2(command);
		
		command = new DisplayOn("20/09/2015");
		output = logic.test2(command);
		
		//Check that events starting or ending and deadlines on the date are extracted
		assertEquals("Here is your schedule for 20/09/2015:\n1.ddd\t10-09-2015 23:00\t20-09-2015 23:00\tNo\n2.ccc\t-\t20-09-2015 23:00\tNo\n3.recess week\t20-09-2015 23:00\t22-09-2015 23:00\tNo\n", output);

		command = new DisplayOn("30/09/2015");
		output = logic.test2(command);
		
		//Check on a date that has no tasks
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!\n\n", output);
	}
	
	@Test
	public void testDisplayFrom() {
		Logic logic = new Logic(false);
		
		command = new AddFloating("aaa");
		logic.test2(command);
		
		command = new AddDeadline("submit report", "21/09/2015");
		logic.test2(command);
		
		command = new AddEvent("recess week", "20/09/2015", "22/09/2015");
		logic.test2(command);
		
		command = new AddEvent("finals", "20/09/2015", "25/09/2015");
		logic.test2(command);
		
		command = new AddEvent("ddd", "10/09/2015", "20/09/2015");
		logic.test2(command);
		
		command = new DisplayFrom("20/09/2015");
		output = logic.test2(command);
		
		//Check that only events starting on 20/09/2015 are displayed
		assertEquals("Here are your events on 20/09/2015:\n1.recess week\t20-09-2015 23:00\t22-09-2015 23:00\tNo\n2.finals\t20-09-2015 23:00\t25-09-2015 23:00\tNo\n", output);		

		command = new DisplayFrom("30/09/2015");
		output = logic.test2(command);
		
		//Check on a date that has no tasks
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!\n\n", output);
	}
	
	@Test
	public void testDisplayAfter() {
		Logic logic = new Logic(false);
		
		command = new AddFloating("aaa");
		logic.test2(command);
		
		command = new AddDeadline("submit report", "20/09/2015");
		logic.test2(command);
		
		command = new AddEvent("recess week", "20/09/2015", "22/09/2015");
		logic.test2(command);
		
		command = new AddEvent("finals", "20/09/2015", "25/09/2015");
		logic.test2(command);
		
		command = new AddEvent("ddd", "10/09/2015", "20/09/2015");
		logic.test2(command);
		
		command = new DisplayAfter("20/09/2015");
		output = logic.test2(command);
		
		assertEquals("Here are your tasks due after 20/09/2015:\n1.submit report\t-\t20-09-2015 23:00\tNo\n2.ddd\t10-09-2015 23:00\t20-09-2015 23:00\tNo\n3.recess week\t20-09-2015 23:00\t22-09-2015 23:00\tNo\n4.finals\t20-09-2015 23:00\t25-09-2015 23:00\tNo\n", output);

		command = new DisplayAfter("22/09/2015");
		output = logic.test2(command);
		
		assertEquals("Here are your tasks due after 22/09/2015:\n1.recess week\t20-09-2015 23:00\t22-09-2015 23:00\tNo\n2.finals\t20-09-2015 23:00\t25-09-2015 23:00\tNo\n", output);

		command = new DisplayAfter("23/09/2015");
		output = logic.test2(command);
		
		assertEquals("Here are your tasks due after 23/09/2015:\n1.finals\t20-09-2015 23:00\t25-09-2015 23:00\tNo\n", output);

		command = new DisplayAfter("30/09/2015");
		output = logic.test2(command);
		
		//Check on a date that has no tasks
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!\n\n", output);
	}
	
	@Test
	public void testDisplayDue() {
		Logic logic = new Logic(false);
		
		command = new AddFloating("aaa");
		logic.test2(command);
		
		command = new AddDeadline("submit report", "20/09/2015");
		logic.test2(command);
		
		command = new AddEvent("recess week", "20/09/2015", "22/09/2015");
		logic.test2(command);
		
		command = new AddEvent("finals", "20/09/2015", "25/09/2015");
		logic.test2(command);
		
		command = new AddEvent("ddd", "10/09/2015", "20/09/2015");
		logic.test2(command);
		
		command = new DisplayDue("20/09/2015");
		output = logic.test2(command);
		
		assertEquals("Here are your tasks due on 20/09/2015:\n1.submit report\t-\t20-09-2015 23:00\tNo\n2.ddd\t10-09-2015 23:00\t20-09-2015 23:00\tNo\n", output);
		command = new DisplayDue("21/09/2015");
		output = logic.test2(command);
		
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!\n\n", output);
	}
	
	@Test
	public void testDisplayFloating() {
		Logic logic = new Logic(false);
		
		command = new AddFloating("aaa");
		logic.test2(command);
		
		command = new AddDeadline("submit report", "20/09/2015");
		logic.test2(command);
		
		command = new AddEvent("recess week", "20/09/2015", "22/09/2015");
		logic.test2(command);
		
		command = new AddEvent("finals", "20/09/2015", "25/09/2015");
		logic.test2(command);
		
		command = new AddEvent("ddd", "10/09/2015", "20/09/2015");
		logic.test2(command);

		command = new AddDeadline("bbb", "05/09/2015");
		logic.test2(command);
		
		command = new AddDeadline("ccc", "20/09/2015");
		logic.test2(command);
		
		command = new DisplayFloating();
		output = logic.test2(command);
		
		assertEquals("Here are all your floating tasks:\n1.aaa\t-\t-\tNo\n", output);

	}
	
//	@Test
	public void testDisplayIncomplete() {
		Logic logic = new Logic(false);
		
		String output = logic.executeCommand("add aaa");
		//Test add output
		assertEquals("Added new Task to TaskBuddy\n\n", output);

		output = logic.executeCommand("display incomplete");
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 18-10-2015 14:00                                  No\n\n", output);

	}
}