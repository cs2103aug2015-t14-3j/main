import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

class UI {
	
	Scanner sc = new Scanner(System.in);
	private String output;
	private String input;
	private String userName;
	private String date;
	private String usernameFile = "UserName.txt";
	
	private Logic logic;
	private userNameAccess name;

	
	private final String REQUEST_NAME = "Please enter your Name: ";
	private final String REQUEST_COMMAND = "Command: ";
	private final String WELCOME_MESSAGE1 = "Date: %s\nTime: %s\n-------------------------\n";
	private final String WELCOME_MESSAGE2 = "Welcome back, %s!\n";
	private final int DATE_POSITION = 0;
	private final int TIME_POSITION = 1;
	
	public UI() {
		logic = new TBLogic();
		name = new userNameAccess(usernameFile);
		output = new String();
		userName = name.getUserName();
		
		welcomeUser();
		
		output = logic.toDoToday();
		display(output);
	}
	
	private void welcomeUser() {
		String[] dateTime = getDateTime();
		
		output = String.format(WELCOME_MESSAGE1, dateTime[DATE_POSITION], dateTime[TIME_POSITION]);
		display(output);
		
		if (userName == null) {
			userName = requestUserName();
		}
		
		output = String.format(WELCOME_MESSAGE2, userName);
		display(output);
	}
	
	private String[] getDateTime() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy, EEEE");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		
		String date = dateFormat.format(now);
		String time = timeFormat.format(now);
		
		String[] dateTime = new String[2];
		dateTime[DATE_POSITION] = date;
		dateTime[TIME_POSITION] = time;
		
		return dateTime;
	}
	
	private String requestUserName() {
		output = REQUEST_NAME;
		display(output);
		userName = sc.nextLine();
		output = "\n";
		display(output);
		name.setUserName(userName);
		
		return userName;
	}
	
	public void run() {
		while (true) {
			output = REQUEST_COMMAND;
			display(output);
			input = sc.nextLine();
			output = logic.executeCommand(input);
			display(output);
		}
	}
	
	private void display(String output) {
		System.out.print(output);
	}
}
