abstract class Logic {

	abstract public String welcomeMessage();
	abstract protected String toDoToday();
	abstract public String executeCommand(String command);

}

class TBLogic extends Logic {
	
	private String output;
	
	public TBLogic() {
		output = new String();
	}
	
	public String welcomeMessage() {
		output = "Welcome to TaskBuddy!!!\n";
		return output;
	}
	
	protected String toDoToday() {
		return "Nothing to do today :D";
	}
	
	public String executeCommand(String command) {
		return command;
	}
	
}
