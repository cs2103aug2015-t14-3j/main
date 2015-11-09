package com.cs2013t143j.TaskBuddyM.IntegrationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

//@@author A0121327U
public class DisplayTest {

	Logic logic = new Logic();
	String output = new String();
	
	private void setup() {
		logic.executeCommand("clear");
		
		logic.executeCommand("add submit report 20/10/2015");
		logic.executeCommand("add holidays 18/10/2015 23/10/2015");
		logic.executeCommand("add exam week 18/10/2015 22/10/2015");
		logic.executeCommand("add aaa 20/10/2015 30/10/2015");
		logic.executeCommand("add hello world");
		logic.executeCommand("add recess week 10/10/2015 20/10/2015");
		logic.executeCommand("add abc");
	}
	
	@Test
	public void DisplayAll() {
		setup();
		
		output = logic.executeCommand("d");
		assertEquals("Here is your entire schedule:\n1.submit report 	-	20 Oct 2015 00:00\n2.recess week  	10 Oct 2015 00:00	20 Oct 2015 00:00\n3.exam week  	18 Oct 2015 00:00	22 Oct 2015 00:00\n4.holidays  	18 Oct 2015 00:00	23 Oct 2015 00:00\n5.aaa  	20 Oct 2015 00:00	30 Oct 2015 00:00\n6.abc	-	-\n7.hello world	-	-\n", output);
	}
	
	@Test
	public void DisplayAfter() {
		setup();
		
		output = logic.executeCommand("display after 18/10/2015");
		assertEquals("Here are your tasks due after 18/10/2015:\n1.submit report 	-	20 Oct 2015 00:00\n2.recess week  	10 Oct 2015 00:00	20 Oct 2015 00:00\n3.exam week  	18 Oct 2015 00:00	22 Oct 2015 00:00\n4.holidays  	18 Oct 2015 00:00	23 Oct 2015 00:00\n5.aaa  	20 Oct 2015 00:00	30 Oct 2015 00:00\n", output);
	}
	
	@Test
	public void DisplayAfterEmpty() {
		setup();
		
		output = logic.executeCommand("d after 30/10/2015");
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!", output);
	}
	
	@Test
	public void DisplayDone() {
		setup();
		
		logic.executeCommand("done 1");
		output = logic.executeCommand("d done");
		assertEquals("Here are all your done tasks:\n1.submit report 	-	20 Oct 2015 00:00\n", output);
	}
	
	@Test
	public void DisplayDoneEmpty() {
		setup();
		
		output = logic.executeCommand("d done");
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!", output);
	}
	
	@Test
	public void DisplayDue() {
		setup();
		
		output = logic.executeCommand("d due 20/10/2015");
		assertEquals("Here are your tasks due on 20/10/2015:\n1.submit report 	-	20 Oct 2015 00:00\n2.recess week  	10 Oct 2015 00:00	20 Oct 2015 00:00\n", output);
	}
	
	@Test
	public void DisplayDueEmpty() {
		setup();
		
		output = logic.executeCommand("d due 1/1/2016");
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!", output);
	}
	
	@Test
	public void DisplayFloating() {
		setup();
		
		output = logic.executeCommand("d floating");
		assertEquals("Here are all your floating tasks:\n1.abc	-	-\n2.hello world	-	-\n", output);
	}
	
	@Test
	public void DisplayFrom() {
		setup();
		
		output = logic.executeCommand("d from 18/10/2015");
		assertEquals("Here are your events on 18/10/2015:\n1.exam week  	18 Oct 2015 00:00	22 Oct 2015 00:00\n2.holidays  	18 Oct 2015 00:00	23 Oct 2015 00:00\n" , output);
	}
	
	@Test
	public void DisplayFromEmpty() {
		setup();
		
		output = logic.executeCommand("d from 15/10/2015");
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!", output);
	}
	
	@Test
	public void DisplayOn() {
		setup();
		
		output = logic.executeCommand("d on 20/10/2015");
		assertEquals("Here is your schedule for 20/10/2015:\n1.submit report 	-	20 Oct 2015 00:00\n2.recess week  	10 Oct 2015 00:00	20 Oct 2015 00:00\n3.aaa  	20 Oct 2015 00:00	30 Oct 2015 00:00\n", output);
	}
	
	@Test
	public void DisplayOnEmpty() {
		setup();
		
		output = logic.executeCommand("d on 21/10/2015");
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!", output);
	}
	
	//@@author A0126303W
	@Test
	public void DisplayWeek() {
		setup();
		
		output = logic.executeCommand("d week");
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!", output);
	}
	
	@Test
	public void DisplayMonth() {
		setup();
		
		output = logic.executeCommand("d month");
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!", output);
	}
	
	@Test
	public void DisplayRange() {
		setup();
		
		output = logic.executeCommand("d range 18/10/2015 to 23/10/2015");
		assertEquals("Here are your tasks from 18/10/2015 to 23/10/2015\n1.exam week  	18 Oct 2015 00:00	22 Oct 2015 00:00\n2.holidays  	18 Oct 2015 00:00	23 Oct 2015 00:00\n", output);
	}
	
	@Test
	public void DisplayOverdue() {
		setup();
		
		output = logic.executeCommand("d overdue");
		assertEquals("Here are all your overdue tasks:\n1.submit report 	-	20 Oct 2015 00:00\n2.recess week  	10 Oct 2015 00:00	20 Oct 2015 00:00\n3.exam week  	18 Oct 2015 00:00	22 Oct 2015 00:00\n4.holidays  	18 Oct 2015 00:00	23 Oct 2015 00:00\n5.aaa  	20 Oct 2015 00:00	30 Oct 2015 00:00\n", output);
	}
}
