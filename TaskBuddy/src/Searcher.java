import java.util.Map;

public class Searcher {

	private Storage storage;
	
	private final String SEARCH_KEY = "searchKey";
	
	public Searcher(Storage storage) {
		this.storage = storage;
	}
	
	public String search(Map<String, String> parsedCommand) {
		String searchKey = parsedCommand.get(SEARCH_KEY);
		
		return "command: search " + " searchKey: " + searchKey + "\n";
	}
}
