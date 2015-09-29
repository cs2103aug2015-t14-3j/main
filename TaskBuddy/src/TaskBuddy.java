import java.util.Scanner;

public class TaskBuddy {
	
	public static void main(String[] args){
		UI TB = new UI();
		TB.run();
	}

}

class UI {
	
	Scanner sc = new Scanner(System.in);
	private Logic logic;
	private String output;
	private String input;
	private String userName;
	
	private final String REQUEST_NAME = "Please enter your Name: ";
	private final String REQUEST_COMMAND = "Command: ";
	
	public UI() {
		logic = new TBLogic();
		output = new String();
		output = logic.welcomeMessage1();
		display(output);
		
		String userName = logic.getUserName();
		if (userName == null) {
			requestUserName();
		}
		
		output = logic.welcomeMessage2();
		display(output);
		
		output = logic.toDoToday();
		display(output);
	}
	
	private void requestUserName() {
		output = REQUEST_NAME;
		display(output);
		userName = sc.nextLine();
		output = "\n";
		display(output);
		logic.setUserName(userName);
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
	