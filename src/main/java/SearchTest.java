import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class SearchTest {

	@Test
	public void testSearch() {
		Logic logic = new Logic(false);
		
		logic.executeCommand("add hi");
		logic.executeCommand("add hello");
		logic.executeCommand("add hello world");
		logic.executeCommand("add he-man");
		
		String output = logic.executeCommand("search he");
		
		assertEquals(" ", output);

	}

}
