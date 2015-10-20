import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class EditTest {

	@Test
	public void testEditDescription() {
		Logic logic = new Logic(false);
		
		logic.executeCommand("add aaa");
		logic.executeCommand("add bbb");
		
		logic.executeCommand("display");
		
		logic.executeCommand("edit -1 -description -ccc");

		String output = logic.executeCommand("display");
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 18-10-2015 14:00                                  No\n\n", output);

	}
	
	@Test
	public void testEditStart() {
		Logic logic = new Logic(false);
		
		logic.executeCommand("add aaa");
		logic.executeCommand("add bbb");
		
		logic.executeCommand("display");
		
		logic.executeCommand("edit -1 -start date -20/10/2015");
		
		String output = logic.executeCommand("display");
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 18-10-2015 14:00                                  No\n\n", output);

	}
	
	@Test
	public void testEditEnd() {
		Logic logic = new Logic(false);
		
		logic.executeCommand("add aaa");
		logic.executeCommand("add bbb");
		
		logic.executeCommand("display");
		
		logic.executeCommand("edit -1 -end date -29/11/2015");
		
		String output = logic.executeCommand("display");
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 18-10-2015 14:00                                  No\n\n", output);

	}
}
