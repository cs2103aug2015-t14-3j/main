package com.cs2013t143j.TaskBuddyM.IntegrationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

//@@author A0121327U
public class SearchTest {

	Logic logic = new Logic();
	String output = new String();
	
	@Test
	public void Search() {
		logic.executeCommand("clear");
		
		logic.executeCommand("add hello");
		logic.executeCommand("add he-man 20/10/2015");
		logic.executeCommand("add hello world 10/10/2015 21/10/2015");
		logic.executeCommand("add hi");
		
		output = logic.executeCommand("search he");
		assertEquals("Search returned 3 result(s):\n1.hello	-	-\n2.he-man 	-	20-10-2015 00:00\n3.hello world  	10-10-2015 00:00	21-10-2015 00:00\n", output);
	}
	
	@Test
	public void SearchEmpty() {
		logic.executeCommand("clear");
		
		logic.executeCommand("add hello");
		logic.executeCommand("add he-man 20/10/2015");
		logic.executeCommand("add hello world 10/10/2015 21/10/2015");
		logic.executeCommand("add hi");
		
		output = logic.executeCommand("search me");
		assertEquals("Search returned no results", output);
	}

}
