import java.util.ArrayList;
import java.util.Map;

abstract class Logic {

	abstract public String executeCommand(String command);

}

class TBLogic extends Logic {
	
	private String output;
	ArrayList<Map<String, String>> tasks;

	
//	private Storage storage;
//	private History      ;
	private Parser parser;
	private Helper helper = new Helper();
	
	private final String INVALID_COMMAND = "Invalid Command\n";
	private final String INVALID_DISPLAY_SUB = "Invalid display subcommand specified\n";
	private final String DISPLAY_HEADER = "Here is your schedule for %s:\n\nDescription         Start Date          End Date\n";
	private final String DISPLAY_FORMAT = "%d.%-18s%-20s%s\n";

	
	public TBLogic() {
		output = new String();
		parser = new Parser();
//		storage = new Storage();
	}
	
	public String executeCommand(String command) {
		Map<String, String> parsedCommand = parser.getDictionary(command);
		
		String commandType = parsedCommand.get("command");
		
		//Edit this out
		System.out.println(parsedCommand.toString());
		
		switch (commandType) {
		case "add":
			output = add(parsedCommand);
			return output;
		case "display":
			output = display(parsedCommand);
			return output;
		case "delete":
			output = delete(parsedCommand);
			return output;			
		case "edit":
			output = edit(parsedCommand);
			return output;
		case "search":
			output = search(parsedCommand);
			return output;
		case "undo":
			output = undo(parsedCommand);
			return output;
		case "help":
			output = help(parsedCommand);
			return output;
		default:
			return INVALID_COMMAND;
		}
	}
	
	private String add(Map<String, String> parsedCommand) {
		String description = parsedCommand.get("description");
		String startDate = parsedCommand.get("startDate");
		String endDate = parsedCommand.get("endDate");
		
		if (endDate == null) {
			System.out.println("Floating Task");
			//Floating Task
		} else if (startDate == null) {
			System.out.println("DeadLine");
			//DeadLine
		} else {
			System.out.println("Event");
			//Event
		}
		
		return "commmand: add " + "description: " + description + " start: " + startDate + " end: " + endDate + "\n";
	}
	
	private String display(Map<String, String> parsedCommand) {
		String subCommand = parsedCommand.get("subCommand");
		String date = parsedCommand.get("date");
		 
		
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
		
//		output = String.format(DISPLAY_HEADER, "today");
//		int index = 1;
		
//		int i = 0;
		
//		for (i = 0; i < tasks.size(); ++i) {
//			Map<String,String> task = tasks.get(i);
			
//			String description = task.get("description");
//			String start = task.get("start");
//			String end = task.get("end");
			
//			output += String.format(DISPLAY_FORMAT, index, description, start, end);			
//		}
		
//		output += "\n";
//		return output;

		
		return "command: display " + "sub: " + subCommand + " date: " + date + "\n";
	}
	
	private String delete(Map<String,String> parsedCommand) {
		String index = parsedCommand.get("index");
		
		return "command: delete " + "index: " + index + "\n";
	}
	
	private String edit(Map<String, String> parsedCommand) {
		String index = parsedCommand.get("index");
		String field = parsedCommand.get("field");
		String value = parsedCommand.get("value");
		
		return "command: edit " + "index: " + index + " field: " + field + " value: " + value + "\n";
	}
	
	private String search(Map<String, String> parsedCommand) {
		String searchKey = parsedCommand.get("searchKey");
		
		return "command: search " + " searchKey: " + searchKey + "\n";
	}
	
	private String undo(Map<String, String> parsedCommand) {
		return "command: undo\n";
	}
	
	private String help(Map<String, String> parsedCommand) {
		String target = parsedCommand.get("target");

		return helper.help(target);
		
//		return "help " + "target: " + target + "\n";
	}

}