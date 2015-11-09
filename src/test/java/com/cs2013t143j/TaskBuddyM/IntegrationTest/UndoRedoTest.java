package com.cs2013t143j.TaskBuddyM.IntegrationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

//@@author A0121327U
public class UndoRedoTest {

	Logic logic = new Logic();
	String output = new String();
	
	@Test
	public void test() {
		logic.executeCommand("clear");
				
		logic.executeCommand("add aaa");
		logic.executeCommand("e 1 description b");
		logic.executeCommand("e 1 start date 20/10/2015");
		logic.executeCommand("e 1 end date 25/10/2015");
		logic.executeCommand("r 1");
		
		UndoDeleteTest();
		UndoEditEndTest();
		UndoEditStartTest();
		UndoEditDescriptionTest();
		UndoAddTest();
		
		RedoAddTest();
		RedoEditDescriptionTest();
		RedoEditStartTest();
		RedoEditEndTest();
		RedoDeleteTest();
		RedoEmptyTest();
		
		logic.executeCommand("add bbb");
		logic.executeCommand("clear");
		
		UndoClear();
	}
	
	public void UndoDeleteTest() {
		output = logic.executeCommand("undo");
		assertEquals("Undid the last undoable command. Here is your entire schedule:\n1.b	20 Oct 2015 00:00	25 Oct 2015 00:00\n", output);
	}
	
	public void UndoEditEndTest() {
		output = logic.executeCommand("undo");
		assertEquals("Undid the last undoable command. Here is your entire schedule:\n1.b	20 Oct 2015 00:00	-\n", output);
	}
	
	public void UndoEditStartTest() {
		output = logic.executeCommand("undo");
		assertEquals("Undid the last undoable command. Here is your entire schedule:\n1.b	-	-\n", output);	
	}
	
	public void UndoEditDescriptionTest() {
		output = logic.executeCommand("undo");
		assertEquals("Undid the last undoable command. Here is your entire schedule:\n1.aaa	-	-\n", output);
	}
	
	public void UndoAddTest() {
		output = logic.executeCommand("undo");
		assertEquals("Undid the last undoable command. Looks like there is nothing on your schedule. Enjoy your day!!!", output);
	}
	
	public void UndoClear() {
		output = logic.executeCommand("undo");
		assertEquals("Undid the last undoable command. Here is your entire schedule:\n1.bbb	-	-\n", output);
	}
	
	public void RedoAddTest() {
		output = logic.executeCommand("redo");
		assertEquals("Redid the last redoable command. Here is your entire schedule:\n1.aaa	-	-\n", output);
	}
	
	public void RedoEditDescriptionTest() {
		output = logic.executeCommand("redo");
		assertEquals("Redid the last redoable command. Here is your entire schedule:\n1.b	-	-\n", output);
	}
	
	public void RedoEditStartTest() {
		output = logic.executeCommand("redo");
		assertEquals("Redid the last redoable command. Here is your entire schedule:\n1.b	20 Oct 2015 00:00	-\n", output);
	}
	
	public void RedoEditEndTest() {
		output = logic.executeCommand("redo");
		assertEquals("Redid the last redoable command. Here is your entire schedule:\n1.b	20 Oct 2015 00:00	25 Oct 2015 00:00\n", output);
	}
	
	public void RedoDeleteTest() {
		output = logic.executeCommand("redo");
		assertEquals("Redid the last redoable command. Looks like there is nothing on your schedule. Enjoy your day!!!", output);
	}
	
	public void RedoEmptyTest() {
		output = logic.executeCommand("redo");
		assertEquals("No more commands to redo. Looks like there is nothing on your schedule. Enjoy your day!!!", output);
	}
	
}
