package com.cs2013t143j.TaskBuddyM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class History {

	private String fileName = null;
	private List<List<String>> history;
	
	History(String fileString){
		fileName = fileString;
		history = new ArrayList();
	}
	
	public void pushHistory(){

			List<String> newData;
			try {
				newData = extractTodoFromFile();
				history.add(newData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

	}
	
	public void popHistory(){
		int lastElementIndex = history.size()-1;
		if(lastElementIndex < 0){
			return;
		}
		List<String> lastHistory = history.get(lastElementIndex);
		try {
			overwriteFile(lastHistory);
			history.remove(lastElementIndex);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private PrintWriter getWriter(){
		PrintWriter writer = null;
		try {
			File file = new File(fileName);
			if(!file.exists()){
			}else{
				FileWriter fw = new FileWriter(file.getAbsolutePath(),true);
				writer = new PrintWriter(fw);			
			}						
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer;
	}
	
	private void overwriteFile(List<String> temp) 
			throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		for(String newLine : temp){
			// rewrite all the lines except the deleted one
			writer.println(newLine);
		}
		
		writer.close();
	}
	
	private List<String> extractTodoFromFile()
			throws FileNotFoundException, IOException {
		List<String> result = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = null;
		while((line = br.readLine()) != null){
				result.add(line);
		}
		br.close();
		return result;
	}
	
}
