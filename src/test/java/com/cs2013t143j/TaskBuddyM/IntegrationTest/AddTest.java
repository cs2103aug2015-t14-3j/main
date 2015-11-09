package com.cs2013t143j.TaskBuddyM.IntegrationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class AddTest {

	Logic logic = new Logic();
	String output = new String();

	@Test
	public void AddFloating() {
		logic.executeCommand("clear");
		
		output = logic.executeCommand("add aaa");
		
		assertEquals("Here is your entire schedule:\n1.aaa\t-\t-\n", output);
	}
	
	@Test
	public void AddDeadLine() {
		logic.executeCommand("clear");
		
		output = logic.executeCommand("add bbb 20/10/2015");
		assertEquals("Here is your entire schedule:\n1.bbb \t-\t20 Oct 2015 00:00\n", output);
	}
	
	@Test
	public void AddEvent() {
		logic.executeCommand("clear");
		
		output = logic.executeCommand("add ccc 25/10/2015 30/10/2015");
		assertEquals("Here is your entire schedule:\n1.ccc  	25 Oct 2015 00:00	30 Oct 2015 00:00\n", output);
	}
	
	@Test
	public void AddFailNoContent() {
		logic.executeCommand("clear");
		
		output = logic.executeCommand("add");
		assertEquals("Parser Error: com.cs2013t143j.TaskBuddyM.Parser.InvalidInputException: No content entered.", output);
	}
	
	@Test
	public void AddFailNoDescription() {
		logic.executeCommand("clear");
		
		output = logic.executeCommand("add by today");
		assertEquals("Parser Error: com.cs2013t143j.TaskBuddyM.Parser.InvalidInputException: No description entered for new task", output);
	}
}
