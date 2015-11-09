package com.cs2013t143j.TaskBuddyM.IntegrationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class EditTest {

	Logic logic = new Logic();
	String output = new String();
	
	@Test
	public void EditDescription() {
		logic.executeCommand("clear");
		
		logic.executeCommand("add aaa");
		output = logic.executeCommand("edit 1 description bbb");
		assertEquals("Edited task no.1 description to bbb\n1.bbb	-	-\n", output);
	}
	
	@Test
	public void EditStart() {
		logic.executeCommand("clear");
		
		logic.executeCommand("add aaa");
		output = logic.executeCommand("e 1 start date 21/10/2015");
		assertEquals("Edited task no.1 start date to 00 21/10/2015\n1.aaa	21 Oct 2015 00:00	-\n", output);
	}
	
	@Test
	public void EditEnd() {
		logic.executeCommand("clear");
		
		logic.executeCommand("add aaa");
		output = logic.executeCommand("e 1 end date 25/10/2015");
		assertEquals("Edited task no.1 end date to 00 25/10/2015\n1.aaa	-	25 Oct 2015 00:00\n", output);
	}
	
	@Test
	public void EditNegative() {
		logic.executeCommand("clear");
		
		logic.executeCommand("add aaa");
		output = logic.executeCommand("e -1 end date 25/10/2015");
		assertEquals("Index provided must be larger than 0", output);
	}
	
	@Test
	public void EditOutOfRange() {
		logic.executeCommand("clear");
		
		logic.executeCommand("add aaa");
		output = logic.executeCommand("e 2 start date 25/10/2015");
		assertEquals("Index provided is larger than last display", output);
	}
	
	@Test
	public void EditInvalidField() {
		logic.executeCommand("clear");
		
		logic.executeCommand("add aaa");
		output = logic.executeCommand("e 2 field 25/10/2015");
		assertEquals("Parser Error: com.cs2013t143j.TaskBuddyM.Parser.InvalidInputException: Invalid field entered", output);
	}
}
