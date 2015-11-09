<<<<<<< HEAD
package com.cs2013t143j.TaskBuddyM.IntegrationTest;

import static org.junit.Assert.*;


import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

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
=======
package com.cs2013t143j.TaskBuddyM.IntegrationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

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
	
	@Test
	public void DeleteNoIndex() {
		logic.executeCommand("clear");
		
		logic.executeCommand("add bbb");
		
		output = logic.executeCommand("r");
		assertEquals("Parser Error: com.cs2013t143j.TaskBuddyM.Parser.InvalidInputException: No content entered.", output);
	}

}
>>>>>>> branch 'master' of https://github.com/cs2103aug2015-t14-3j/main.git
