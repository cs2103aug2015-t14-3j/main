import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class DisplayTest {

	@Test
	public void testDisplay() {
		Logic logic = new Logic(false);
		
		String output = logic.executeCommand("add aaa");

		output = logic.executeCommand("display");
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 18-10-2015 14:00                                  No\n\n", output);
		
//		logic.executeCommand("add submit report -by 21/09/2015");
//		logic.executeCommand("add finals -from 20/09/2015 -to 22/09/2015");
//		output = logic.executeCommand("display");
//		assertEquals("",output);
		
//		logic.executeCommand("add bbb -by 05/09/2015");
//		output = logic.executeCommand("display");
//		assertEquals("",output);
		
//		logic.executeCommand("add ccc -by 20/09/2015");
//		output = logic.executeCommand("display on 20/09/2015");
//		assertEquals("",output);
	}
	
	@Test
	public void testDisplayOn() {
		Logic logic = new Logic(false);
		
		logic.executeCommand("add aaa");
		logic.executeCommand("add submit report by 20/10/2015");
		logic.executeCommand("add reading week from 20/10/2015 to 25/10/2015");

		String output = logic.executeCommand("display on 20/10/2015");
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 18-10-2015 14:00                                  No\n\n", output);

	}
	
	@Test
	public void testDisplayFrom() {
		Logic logic = new Logic(false);
		
		logic.executeCommand("add reading week from 20/10/2015 to 25/10/2015");
		logic.executeCommand("add reading week from 20/10/2015 to 25/10/2015");
		logic.executeCommand("add reading week from 20/10/2015 to 25/10/2015");
		
		String output = logic.executeCommand("display from");
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 18-10-2015 14:00                                  No\n\n", output);

	}
	
	@Test
	public void testDisplayAfter() {
		Logic logic = new Logic(false);
		
		String output = logic.executeCommand("add aaa");
		//Test add output
		assertEquals("Added new Task to TaskBuddy\n\n", output);

		output = logic.executeCommand("display after");
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 18-10-2015 14:00                                  No\n\n", output);

	}
	
	@Test
	public void testDisplayDue() {
		Logic logic = new Logic(false);
		
		logic.executeCommand("add aaa");
		logic.executeCommand("add submit report1 by 20/10/2015");
		logic.executeCommand("add submit report2 by 21/10/2015");
		
		String output = logic.executeCommand("display due 20/10/2015");
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 18-10-2015 14:00                                  No\n\n", output);

	}
	
	@Test
	public void testDisplayFloating() {
		Logic logic = new Logic(false);
		
		logic.executeCommand("add aaa");
		logic.executeCommand("add submit report by 20/10/2015");
		logic.executeCommand("add reading week from 20/10/2015 to 25/10/2015");

		String output = logic.executeCommand("display floating");
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 18-10-2015 14:00                                  No\n\n", output);

	}
	
	@Test
	public void testDisplayIncomplete() {
		Logic logic = new Logic(false);
		
		String output = logic.executeCommand("add aaa");
		//Test add output
		assertEquals("Added new Task to TaskBuddy\n\n", output);

		output = logic.executeCommand("display incomplete");
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 18-10-2015 14:00                                  No\n\n", output);

	}
}