import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class LogicTest {

	@Test
	public void testAddDisplay() {
		Logic logic = new Logic(false);
		
		String output = logic.executeCommand("add aaa");
		//Test add output
		assertEquals("Added new Task to TaskBuddy\n\n", output);

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

}
