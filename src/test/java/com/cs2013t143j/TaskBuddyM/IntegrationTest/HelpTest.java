package com.cs2013t143j.TaskBuddyM.IntegrationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class HelpTest {

	Logic logic = new Logic();
	String output;
	
	@Test
	public void HelpAdd() {
		
		output = logic.executeCommand("h add");
		assertEquals("Here's the help file for Command: ADD\n",output);
	}
	
	@Test
	public void HelpDelete() {
		
		output = logic.executeCommand("h delete");
		assertEquals("Here's the help file for Command: DELETE\n",output);
	}
	
	@Test
	public void HelpEdit() {
		
		output = logic.executeCommand("h edit");
		assertEquals("Here's the help file for Command: EDIT\n",output);
	}
	
	@Test
	public void HelpSearch() {
		
		output = logic.executeCommand("h search");
		assertEquals("Here's the help file for Command: SEARCH\n",output);
	}
	
	@Test
	public void HelpDone() {
		
		output = logic.executeCommand("h done");
		assertEquals("Here's the help file for Command: DONE\n",output);
	}
	
	@Test
	public void HelpDisplay() {
		
		output = logic.executeCommand("h display");
		assertEquals("Here's the help file for Command: DISPLAY\n",output);
	}
	
	@Test
	public void HelpFail() {
		
		output = logic.executeCommand("h");
		assertEquals("Parser Error: com.cs2013t143j.TaskBuddyM.Parser.InvalidInputException: No content entered.",output);
	}

}
