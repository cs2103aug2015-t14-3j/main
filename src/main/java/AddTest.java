import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class AddTest {
	
	private Map<String, String> command = new HashMap<String,String>();
	private String output;

	@Test
	public void testAddFloating() {
		Logic logic = new Logic(false);
		
		command.put("command", "add");
		command.put("description", "aaa");		
		output = logic.test(command);
		command.clear();
		//Test add output
		assertEquals("Added new Task to TaskBuddy\n\n", output);

		command.put("command", "display");
		output = logic.test(command);
		command.clear();
		
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                                                                   No\n\n", output);

	}
	
	@Test
	public void testAddEvent() {
		Logic logic = new Logic(false);
		
		command.put("command", "add");
		command.put("description", "aaa");
		command.put("startDate", "20/10/2015");
		command.put("endDate", "21/10/2015");	
		logic.test(command);
		command.clear();

		command.put("command", "display");	
		output = logic.test(command);
		command.clear();
		
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 20-10-2015 23:00         21-10-2015 23:00         No\n\n", output);

	}
	
	@Test
	public void testAddDeadline() {
		Logic logic = new Logic(false);
		
		command.put("command", "add");
		command.put("description", "aaa");
		command.put("endDate", "21/10/2015");	
		logic.test(command);
		command.clear();

		command.put("command", "display");
		output = logic.test(command);
		command.clear();
		
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                                          21-10-2015 23:00         No\n\n", output);

	}

}
