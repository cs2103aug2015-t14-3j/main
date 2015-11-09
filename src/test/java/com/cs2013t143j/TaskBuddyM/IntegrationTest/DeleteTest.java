package com.cs2013t143j.TaskBuddyM.IntegrationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

//@@author A0121327U
public class DeleteTest {

	Logic logic = new Logic();
	String output = new String();
	
	@Test
	public void DeleteOne() {
		logic.executeCommand("clear");
		
		logic.executeCommand("add aaa");
		logic.executeCommand("add bbb");
		
		output = logic.executeCommand("r 2");
		assertEquals("Deleted task(s) 2\n1.aaa	-	-\n", output);
	}
	
	@Test
	public void DeleteMultiple() {
		logic.executeCommand("clear");
		
		logic.executeCommand("add aaa");
		logic.executeCommand("add bbb");
		logic.executeCommand("add ccc");

		output = logic.executeCommand("r 1 2");
		assertEquals("Deleted task(s) 1, 2\n1.ccc	-	-\n", output);
	}
	
	@Test
	public void DeleteNegative() {
		logic.executeCommand("clear");
		
		logic.executeCommand("add bbb");
		
		output = logic.executeCommand("r -1");	
		assertEquals("Index provided must be larger than 0", output);
	}
	
	@Test
	public void DeleteOverRange() {
		logic.executeCommand("clear");
		
		logic.executeCommand("add bbb");
		
		output = logic.executeCommand("r 2");
		assertEquals("Index provided is larger than last display", output);
	}

}
