import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class DisplayTest {
	
	private Map<String, String> command = new HashMap<String,String>();
	private String output;

	@Test
	public void testDisplay() {
		Logic logic = new Logic(false);
		
		command.put("command", "add");
		command.put("description", "aaa");
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "submit report");
		command.put("endDate", "21/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "recess week");
		command.put("startDate", "20/09/2015");
		command.put("endDate", "22/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "display");
		output = logic.test(command);
		command.clear();
		
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.submit report                                21-09-2015 23:00         No\n2.recess week         20-09-2015 23:00         22-09-2015 23:00         No\n3.aaa                                                                   No\n\n", output);

		command.put("command", "add");
		command.put("description", "bbb");
		command.put("endDate", "05/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "display");
		output = logic.test(command);
		command.clear();
		
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.bbb                                          05-09-2015 23:00         No\n2.submit report                                21-09-2015 23:00         No\n3.recess week         20-09-2015 23:00         22-09-2015 23:00         No\n4.aaa                                                                   No\n\n", output);

	}
	
	@Test
	public void testDisplayOn() {
		Logic logic = new Logic(false);
		
		command.put("command", "add");
		command.put("description", "aaa");
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "submit report");
		command.put("endDate", "21/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "recess week");
		command.put("startDate", "20/09/2015");
		command.put("endDate", "22/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "ddd");
		command.put("startDate", "10/09/2015");
		command.put("endDate", "20/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "bbb");
		command.put("endDate", "05/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "ccc");
		command.put("endDate", "20/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "display");
		command.put("subCommand", "on");
		command.put("date", "20/09/2015");
		output = logic.test(command);
		command.clear();
		
		assertEquals("Here is your schedule for 20/09/2015:\n\nDescription                 Start Date             End Date            Done\n1.ddd                 10-09-2015 23:00         20-09-2015 23:00         No\n2.ccc                                          20-09-2015 23:00         No\n3.recess week         20-09-2015 23:00         22-09-2015 23:00         No\n\n", output);

	}
	
	@Test
	public void testDisplayFrom() {
		Logic logic = new Logic(false);
		
		command.put("command", "add");
		command.put("description", "aaa");
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "submit report");
		command.put("endDate", "20/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "recess week");
		command.put("startDate", "20/09/2015");
		command.put("endDate", "22/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "finals");
		command.put("startDate", "20/09/2015");
		command.put("endDate", "25/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "ddd");
		command.put("startDate", "10/09/2015");
		command.put("endDate", "20/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "display");
		command.put("subCommand", "from");
		command.put("date", "20/09/2015");
		output = logic.test(command);
		command.clear();
		
		assertEquals("Here are your events on 20/09/2015:\n\nDescription                 Start Date             End Date            Done\n1.recess week         20-09-2015 23:00         22-09-2015 23:00         No\n2.finals              20-09-2015 23:00         25-09-2015 23:00         No\n\n", output);
		
	}
	
	@Test
	public void testDisplayAfter() {
		Logic logic = new Logic(false);
		
		command.put("command", "add");
		command.put("description", "aaa");
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "submit report");
		command.put("endDate", "20/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "recess week");
		command.put("startDate", "20/09/2015");
		command.put("endDate", "22/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "finals");
		command.put("startDate", "20/09/2015");
		command.put("endDate", "25/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "ddd");
		command.put("startDate", "10/09/2015");
		command.put("endDate", "20/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "display");
		command.put("subCommand", "after");
		command.put("date", "20/09/2015");
		output = logic.test(command);
		command.clear();
		
		assertEquals("Here are your tasks due after 20/09/2015:\n\nDescription                 Start Date             End Date            Done\n1.submit report                                20-09-2015 23:00         No\n2.ddd                 10-09-2015 23:00         20-09-2015 23:00         No\n3.recess week         20-09-2015 23:00         22-09-2015 23:00         No\n4.finals              20-09-2015 23:00         25-09-2015 23:00         No\n\n", output);

		command.put("command", "display");
		command.put("subCommand", "after");
		command.put("date", "22/09/2015");
		output = logic.test(command);
		command.clear();
		
		assertEquals("Here are your tasks due after 22/09/2015:\n\nDescription                 Start Date             End Date            Done\n1.recess week         20-09-2015 23:00         22-09-2015 23:00         No\n2.finals              20-09-2015 23:00         25-09-2015 23:00         No\n\n", output);


		command.put("command", "display");
		command.put("subCommand", "after");
		command.put("date", "23/09/2015");
		output = logic.test(command);
		command.clear();
		
		assertEquals("Here are your tasks due after 23/09/2015:\n\nDescription                 Start Date             End Date            Done\n1.finals              20-09-2015 23:00         25-09-2015 23:00         No\n\n", output);
	}
	
	@Test
	public void testDisplayDue() {
		Logic logic = new Logic(false);
		
		command.put("command", "add");
		command.put("description", "aaa");
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "submit report");
		command.put("endDate", "20/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "recess week");
		command.put("startDate", "20/09/2015");
		command.put("endDate", "22/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "finals");
		command.put("startDate", "20/09/2015");
		command.put("endDate", "25/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "ddd");
		command.put("startDate", "10/09/2015");
		command.put("endDate", "20/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "display");
		command.put("subCommand", "due");
		command.put("date", "20/09/2015");
		output = logic.test(command);
		command.clear();
		
		assertEquals("Here are your tasks due on 20/09/2015:\n\nDescription                 Start Date             End Date            Done\n1.submit report                                20-09-2015 23:00         No\n2.ddd                 10-09-2015 23:00         20-09-2015 23:00         No\n\n", output);
	
		command.put("command", "display");
		command.put("subCommand", "due");
		command.put("date", "21/09/2015");
		output = logic.test(command);
		command.clear();
		
		assertEquals("Looks like there is nothing on your schedule. Enjoy your day!!!\n\n", output);

	}
	
	@Test
	public void testDisplayFloating() {
		Logic logic = new Logic(false);
		
		command.put("command", "add");
		command.put("description", "aaa");
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "submit report");
		command.put("endDate", "21/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "recess week");
		command.put("startDate", "20/09/2015");
		command.put("endDate", "22/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "ddd");
		command.put("startDate", "10/09/2015");
		command.put("endDate", "20/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "bbb");
		command.put("endDate", "05/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "ccc");
		command.put("endDate", "20/09/2015");	
		logic.test(command);
		command.clear();
		
		command.put("command", "display");
		command.put("subCommand", "floating");
		output = logic.test(command);
		command.clear();
		
		assertEquals("Here are all your floating tasks:\n\nDescription                 Start Date             End Date            Done\n1.aaa                                                                   No\n\n", output);

	}
	
//	@Test
	public void testDisplayIncomplete() {
		Logic logic = new Logic(false);
		
		String output = logic.executeCommand("add aaa");
		//Test add output
		assertEquals("Added new Task to TaskBuddy\n\n", output);

		output = logic.executeCommand("display incomplete");
		assertEquals("Here is your entire schedule:\n\nDescription                 Start Date             End Date            Done\n1.aaa                 18-10-2015 14:00                                  No\n\n", output);

	}
}