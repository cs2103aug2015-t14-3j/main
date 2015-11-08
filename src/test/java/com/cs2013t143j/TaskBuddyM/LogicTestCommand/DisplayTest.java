package com.cs2013t143j.TaskBuddyM.LogicTestCommand;

import static org.junit.Assert.*;

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
import com.cs2013t143j.TaskBuddyM.Command.DisplayRange;
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
		
		assertEquals("Here is your entire schedule:\n1.submit report\t-\t21 Sep 2015 23:00\n2.recess week\t20 Sep 2015 23:00\t22 Sep 2015 23:00\n3.aaa\t-\t-\n", output);
		
		command = new AddDeadline("bbb", "05/09/2015");
		logic.test2(command);
		
		command = new DisplayAll();
		output = logic.test2(command);
		
		assertEquals("Here is your entire schedule:\n1.bbb\t-\t05 Sep 2015 23:00\n2.submit report\t-\t21 Sep 2015 23:00\n3.recess week\t20 Sep 2015 23:00\t22 Sep 2015 23:00\n4.aaa\t-\t-\n", output);
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
		assertEquals("Here is your schedule for 20/09/2015:\n1.ddd\t10 Sep 2015 23:00\t20 Sep 2015 23:00\n2.ccc\t-\t20 Sep 2015 23:00\n3.recess week\t20 Sep 2015 23:00\t22 Sep 2015 23:00\n", output);

		command = new DisplayOn("30/09/2015");
		output = logic.test2(command);
		
		//Check on a date that has no tasks
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!", output);
	}
	
	@Test
	public void testOnInvalid() {
		Logic logic = new Logic(false);
		
		command = new DisplayOn(" ");
		output = logic.test2(command);
		
		assertEquals("Invalid or no date specified", output);
	}
	
	@Test
	public void testOnEmpty() {
		Logic logic = new Logic(false);
		
		command = new DisplayOn("20/10/2015");
		output = logic.test2(command);
		
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!", output);		
	}
	
	@Test
	public void testLongDate() {
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
		
		command = new DisplayFrom("17 20/09/2015");
		output = logic.test2(command);
		
		//Check that only events starting on 20/09/2015 are displayed
		assertEquals("Here are your events on 20/09/2015:\n1.recess week\t20 Sep 2015 23:00\t22 Sep 2015 23:00\n2.finals\t20 Sep 2015 23:00\t25 Sep 2015 23:00\n", output);		
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
		assertEquals("Here are your events on 20/09/2015:\n1.recess week\t20 Sep 2015 23:00\t22 Sep 2015 23:00\n2.finals\t20 Sep 2015 23:00\t25 Sep 2015 23:00\n", output);		

		command = new DisplayFrom("30/09/2015");
		output = logic.test2(command);
		
		//Check on a date that has no tasks
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!", output);
	}

	@Test
	public void testFromInvalid() {
		Logic logic = new Logic(false);
		
		command = new DisplayFrom(" ");
		output = logic.test2(command);
		
		assertEquals("Invalid or no date specified", output);
	}
	
	@Test
	public void testFromEmpty() {
		Logic logic = new Logic(false);
		
		command = new DisplayFrom("20/10/2015");
		output = logic.test2(command);
		
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!", output);		
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
		
		assertEquals("Here are your tasks due after 20/09/2015:\n1.recess week\t20 Sep 2015 23:00\t22 Sep 2015 23:00\n2.finals\t20 Sep 2015 23:00\t25 Sep 2015 23:00\n", output);

		command = new DisplayAfter("22/09/2015");
		output = logic.test2(command);
		
		assertEquals("Here are your tasks due after 22/09/2015:\n1.finals\t20 Sep 2015 23:00\t25 Sep 2015 23:00\n", output);

		command = new DisplayAfter("23/09/2015");
		output = logic.test2(command);
		
		assertEquals("Here are your tasks due after 23/09/2015:\n1.finals\t20 Sep 2015 23:00\t25 Sep 2015 23:00\n", output);

		command = new DisplayAfter("30/09/2015");
		output = logic.test2(command);
		
		//Check on a date that has no tasks
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!", output);
	}
	
	@Test
	public void testAfterInvalid() {
		Logic logic = new Logic(false);
		
		command = new DisplayAfter(" ");
		output = logic.test2(command);
		
		assertEquals("Invalid or no date specified", output);
	}
	
	@Test
	public void testAfterEmpty() {
		Logic logic = new Logic(false);
		
		command = new DisplayAfter("20/10/2015");
		output = logic.test2(command);
		
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!", output);		
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
		
		assertEquals("Here are your tasks due on 20/09/2015:\n1.submit report\t-\t20 Sep 2015 23:00\n2.ddd\t10 Sep 2015 23:00\t20 Sep 2015 23:00\n", output);
		command = new DisplayDue("21/09/2015");
		output = logic.test2(command);
		
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!", output);
	}
	
	@Test
	public void testDueInvalid() {
		Logic logic = new Logic(false);
		
		command = new DisplayDue(" ");
		output = logic.test2(command);
		
		assertEquals("Invalid or no date specified", output);
	}
	
	@Test
	public void testDueEmpty() {
		Logic logic = new Logic(false);
		
		command = new DisplayDue("20/10/2015");
		output = logic.test2(command);
		
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!", output);		
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
		
		assertEquals("Here are all your floating tasks:\n1.aaa\t-\t-\n", output);

	}
	
	@Test
	public void testFloatEmpty() {
		Logic logic = new Logic(false);
		
		command = new DisplayFloating();
		output = logic.test2(command);
		
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!", output);		
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