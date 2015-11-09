# A0121327Uunused
###### src\main\java\com\cs2013t143j\TaskBuddyM\Command\SortDisplay.java
``` java
public class SortDisplay implements Command {
	
	protected final String DISPLAY_FORMAT = "%d.%s\t%s\t%s\t%s\n";
	private final String SORTED_HEADER = "Here are your tasks sorted by start date\n";
	protected final String FREE_DAY = "Looks like there is nothing on your schedule. Enjoy your day!!!\n\n";
	
	private final String INFO = "Sort Display by start date";

	public String execute(ArrayList<Task> lastDisplay, StorageAccess sAccess) {
		
		String output = SORTED_HEADER;
		
		if (lastDisplay.size() == 0) {
			output = FREE_DAY;
			return output;
		}
		
		Collections.sort(lastDisplay, new StartSorter());
	
		int index = 1;
		
		int i = 0;
		
		for (i = 0; i < lastDisplay.size(); ++i) {
			Task task = lastDisplay.get(i);
			
			String description = task.getDescription();
			String start = task.getStartDateTimeInString();
			String end = task.getEndDateTimeInString();
			
			if (start == "") {
				start = "-";
			}
			
			if (end == "") {
				end = "-";
			}
			
			boolean done = task.isDone();
			String isDone = "No";
			
			if (done == true) {
				isDone = "Yes";
			}else {
				isDone = "No";
			}
			
			output += String.format(DISPLAY_FORMAT, index, description, start, end, isDone);			
			++index;
		}
		
		return output;
	}
	
	public boolean isValid() {
		return true;
	}
	
	public void undo(StorageAccess sAccess) {
		return;
	}
	
	public String info() {
		String output = INFO;
		
		return output;
	}
	
	public void redo(StorageAccess sAccess) {
		return;
	}
}
```
###### src\main\java\com\cs2013t143j\TaskBuddyM\Command\StartSorter.java
``` java
class StartSorter implements Comparator<Task> {
	
	public int compare(Task t1, Task t2) {
		LocalDateTime t1StartDateTime = t1.getStartDateTime();
		LocalDateTime t2StartDateTime = t2.getStartDateTime();
		
		if (t1StartDateTime == null) {
			return 1;
		} else if (t2StartDateTime == null) {
			return -1;
		}

		return t1StartDateTime.compareTo(t2StartDateTime);
		
	}

}
```
