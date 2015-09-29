import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

abstract class Logic {

	abstract public String welcomeMessage1();
	abstract public String welcomeMessage2();
	abstract public String toDoToday();
	abstract public String executeCommand(String command);
	abstract public boolean isFirstRun();
	abstract public void setUserName(String userName);

}

class TBLogic extends Logic {
	
	private String output;
	private String date;
	private Storage storage;
	private String userName;
	private Parser parser;
	
	private final String WELCOME_MESSAGE1 = "Date: %s\nTime: %s\n--------------------\n";
	private final String WELCOME_MESSAGE2 = "Welcome back, %s!\n";
	private final String INVALID_COMMAND = "Invalid Command\n";
	private final String DISPLAY_HEADER = "Here is your schedule for %s:\n\n  Description       Start Date          End Date\n";
	private final String DISPLAY_FORMAT = "%d.%-18s%-20s%s\n";
	
	public TBLogic() {
		output = new String();
		parser = new Parser();
		storage = new Storage();
	}
	
	public boolean isFirstRun() {
		return storage.isFirstRun();
	}
	
	public void setUserName(String userName) {
		storage.setUserName(userName);
	}
	
	public String welcomeMessage2() {
		userName = storage.getUserName();
		output = String.format(WELCOME_MESSAGE2, userName);
		return output;
	}
	
	public String welcomeMessage1() {
		Date dateTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy, EEEE");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		
		date = dateFormat.format(dateTime);
		String time = timeFormat.format(dateTime);
		
		output = String.format(WELCOME_MESSAGE1, date, time);
		return output;
	}
	
	public String toDoToday() {
		output = String.format(DISPLAY_HEADER, "today");
		int index = 1;
		
		output += String.format(DISPLAY_FORMAT, index, "submit report", "20/9/2015", "21/9/2015");

		output += "\n";
		return output;
	}
	
	public String executeCommand(String command) {
		Map<String, String> parsedCommand = parser.parseCommand(command);
		
		String commandType = parsedCommand.get("command");
		
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
			return " ";
//		case INVALID:
//			output = INVALID_COMMAND;
//			return output;
		}
	}
	
	private String add(Map<String, String> parsedCommand) {
		String description = parsedCommand.get("description");
		String startDate = parsedCommand.get("startDate");
		String endDate = parsedCommand.get("endDate");
		
		if (endDate == null) {
			//Floating Task
		} else if (startDate == null) {
			//DeadLine
		} else {
			//Event
		}
		
		return "add\n";
	}
	
	private String display(Map<String, String> parsedCommand) {
		String subCommand = parsedCommand.get("subCommand");
		String date = parsedCommand.get("date");
		
		if (subCommand == null) {
			//Display All
		} else {
			switch (subCommand) {
			case "on":
				
			case "from":
				
			case "after":
				
			case "due":
				
			case "incomplete":
				
			case "floating":
				
			default:
		
			}
		}
		
		return "display\n";
	}
	
	private String delete(Map<String,String> parsedCommand) {
		String index = parsedCommand.get("index");
		
		return "delete\n";
	}
	
	private String edit(Map<String, String> parsedCommand) {
		String index = parsedCommand.get("index");
		String field = parsedCommand.get("field");
		String value = parsedCommand.get("value");
		
		return "edit\n";
	}
	
	private String search(Map<String, String> parsedCommand) {
		String text = parsedCommand.get("text");
		
		return "search\n";
	}
	
	private String undo(Map<String, String> parsedCommand) {
		return "undo\n";
	}
	
	private String help(Map<String, String> parsedCommand) {
		String target = parsedCommand.get("target");

		switch (target) {
		case "add":
			
		case "delete":
			
		case "display":
			
		case "search":
			
		case "edit":
			
		case "undo":
			
		}
		return "help\n";
	}

}