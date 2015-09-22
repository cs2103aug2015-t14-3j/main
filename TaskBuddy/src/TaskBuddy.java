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
	
	public UI() {
		logic = new TBLogic();
		output = new String();
		output = logic.welcomeMessage();
		display(output);
	}
	
	public void run() {
		while (true) {
			input = sc.nextLine();
			output = logic.executeCommand(input);
			display(output);
		}
	}
	
	private void display(String output) {
		System.out.print(output);
	}
	
}
	