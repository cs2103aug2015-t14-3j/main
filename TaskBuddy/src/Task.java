import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Task {
	
	public static final DateTimeFormatter FORMAT_STORAGE_DATETIME = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	private String description;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private boolean done;
	

public Task()  { 
	description = null;
	startDateTime = null;
	endDateTime = null;
	done = false;
}

public Task(String description, LocalDateTime startDateTime,LocalDateTime endDateTime, boolean done) {
	setDescription(description);
	setStartDateTime(startDateTime);
	setEndDateTime(endDateTime);
	setIsDone(done);
}


public Task(String description, LocalDateTime endDateTime) {
	setDescription(description);
	setStartDateTime(null);
	setEndDateTime(null);
	setIsDone(false);
}

public Task(String description, LocalDateTime startDateTime,LocalDateTime endDateTime) {
	setDescription(description);
	setStartDateTime(startDateTime);
	setEndDateTime(endDateTime);
	setIsDone(false);
}

public Task(String description)
{
	setDescription(description);
	setStartDateTime(null);
	setEndDateTime(null);
	setIsDone(false);
}


public String getDescription() {
	return description;
}

public LocalDateTime getStartDateTime() {
	return startDateTime;
}

public LocalDateTime getEndDateTime() {
	return endDateTime;
}

public boolean isDone() {
	return done;
}

public void setDescription(String _description) {
	description = _description;
}

public void setStartDateTime(LocalDateTime _startDateTime) {
   startDateTime = _startDateTime;
}

public void setEndDateTime(LocalDateTime _endDateTime) {
	endDateTime = _endDateTime;
}

public void setIsDone(boolean _done) {
	done = _done;
}

private static String getDateTimeInString(LocalDateTime dateTime) {
    String dateTimeString = "";
    if (dateTime != null) {
        dateTimeString = dateTime.format(FORMAT_STORAGE_DATETIME);
    }
    return dateTimeString;
}

public String getStartDateTimeInString() {
	return getDateTimeInString(getStartDateTime());
}

public String getEndDateTimeInString() {
	return getDateTimeInString(getEndDateTime());
}

}







