import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class SearchTest {
	
	private Map<String, String> command = new HashMap<String,String>();
	private String output;

	@Test
	public void testSearch() {
		Logic logic = new Logic(false);
		
		command.put("command", "add");
		command.put("description", "hi");		
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "hello");		
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "hello world");		
		logic.test(command);
		command.clear();
		
		command.put("command", "add");
		command.put("description", "he-man");		
		logic.test(command);
		command.clear();
		
		command.put("command", "search");
		command.put("searchKey", "he");		
		output = logic.test(command);
		command.clear();
		
		assertEquals("Search returned 3 result(s):\n1.hello\n2.hello world\n3.he-man\n\n", output);

	}

}
