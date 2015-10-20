import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class DeleteTest {

	@Test
	public void testDelete() {
		Logic logic = new Logic(false);
		
		logic.executeCommand("add aaa");
		logic.executeCommand("add bbb");
		logic.executeCommand("add ccc");
		
		logic.executeCommand("display");
		logic.executeCommand("delete 2");

		String output = logic.executeCommand("display");
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 18-10-2015 14:00                                  No\n\n", output);

	}
}
