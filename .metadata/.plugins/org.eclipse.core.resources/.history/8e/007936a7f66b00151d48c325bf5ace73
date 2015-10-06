import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Displayer {
	
	private Storage storage;
	
	private final String DISPLAY_SUB = "subCommand";
	private final String DISPLAY_DATE = "date";
	
	private final String DISPLAY_HEADER = "Here is your schedule for %s:\n\nDescription         Start Date          End Date\n";
	private final String DISPLAY_FORMAT = "%d.%-18s%-20s%s\n";
	
	private final String INVALID_DISPLAY_SUB = "Invalid display subcommand specified\n";

	public Displayer(Storage storage) {
		this.storage = storage;
	}
	
	public String display(Map<String, String> parsedCommand, ArrayList<Task> tasks) {
		String subCommand = parsedCommand.get(DISPLAY_SUB);
		String date = parsedCommand.get(DISPLAY_DATE);
		 
		if (subCommand == null) {
			//Display All
//			tasks = 
		} else {
			switch (subCommand) {
			case "on":
//				tasks = 
				break;
			case "from":
//				tasks = 
				break;
			case "after":
//				tasks = 
				break;
			case "due":
//				tasks = 
				break;
			case "incomplete":
//				tasks = 
				break;
			case "floating":
//				tasks = 
				break;
			default:
				return INVALID_DISPLAY_SUB;
			}
		}

		tasks = storage.display();
		Collections.sort(tasks, new TaskSorter());
		
		String output = String.format(DISPLAY_HEADER, "today");
		int index = 1;
		
		int i = 0;
		
		for (i = 0; i < tasks.size(); ++i) {
			Task task = tasks.get(i);
			
			String description = task.getDescription();
			String start = task.getStartDateTimeInString();
			String end = task.getEndDateTimeInString();
			
			output += String.format(DISPLAY_FORMAT, index, description, start, end);			
			++index;
		}
		
		output += "\n";
		return output;

		
//		return "command: display " + "sub: " + subCommand + " date: " + date + "\n";
	}

}
