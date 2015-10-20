import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class AddTest {

	@Test
	public void testAddFloating() {
		Logic logic = new Logic(false);
		
		String output = logic.executeCommand("add aaa");
		//Test add output
		assertEquals("Added new Task to TaskBuddy\n\n", output);

		output = logic.executeCommand("display");
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 18-10-2015 14:00                                  No\n\n", output);

	}
	
	@Test
	public void testAddEvent() {
		Logic logic = new Logic(false);
		
		String output = logic.executeCommand("add aaa from 20/10/2015 to 21/10/2015");

		output = logic.executeCommand("display");
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 18-10-2015 14:00                                  No\n\n", output);

	}
	
	@Test
	public void testAddDeadline() {
		Logic logic = new Logic(false);
		
		String output = logic.executeCommand("add aaa by 10/10/2015");

		output = logic.executeCommand("display");
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 18-10-2015 14:00                                  No\n\n", output);

	}

}
