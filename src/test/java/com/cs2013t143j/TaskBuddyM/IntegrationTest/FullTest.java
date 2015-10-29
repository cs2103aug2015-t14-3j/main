package com.cs2013t143j.TaskBuddyM.IntegrationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Logic.Logic;

public class FullTest {

	Logic logic = new Logic();
	String output = new String();
	
	@Test
	public void a() {
		logic.executeCommand("add edit test");
		logic.executeCommand("add delete test");
		logic.executeCommand("add ddue1 20/10/2015");
		logic.executeCommand("add ddue2 20/10/2015");
		logic.executeCommand("add dfrom1 18/10/2015 23/10/2015");
		logic.executeCommand("add dfrom2 18/10/2015 22/10/2015");
		logic.executeCommand("add don1 20/10/2015 30/10/2015");
		logic.executeCommand("add don2 20/10/2015 28/10/2015");
		logic.executeCommand("add hellow world");
		logic.executeCommand("add hello");
		logic.executeCommand("add he-man");
		logic.executeCommand("add bye world");
		logic.executeCommand("add learn piano");
		logic.executeCommand("add go running");
		logic.executeCommand("add submit report 15/10/2015");
		logic.executeCommand("add hand in homework 25/10/2015");
		logic.executeCommand("add recess week 10/10/2015 15/10/2015");
		logic.executeCommand("add hell week 23/11/2015 04/12/2015");
		logic.executeCommand("add abc");
		output = logic.executeCommand("add holidays 06/12/2015 10/01/2016");
		
		assertEquals(" ",output);
	}
	
	@Test
	public void displayOn() {
		output = logic.executeCommand("d on 20/10/2015");
		
		assertEquals(" ",output);
	}
	
	@Test
	public void displayFrom() {
		output = logic.executeCommand("d from 18/10/2015");
		
		assertEquals(" ",output);
	}
	
	@Test
	public void displayAfter() {
		output = logic.executeCommand("d after 20/10/2015");
		
		assertEquals(" ",output);
	}
	
	@Test
	public void displayDue() {
		output = logic.executeCommand("d due 20/10/2015");;
		
		assertEquals("  ",output);
	}
	
	@Test
	public void displayFloat() {
		output = logic.executeCommand("d floating");
		
		assertEquals(" ",output);
	}
	
	@Test
	public void editDescripton() {
		output = logic.executeCommand("edit 12 description test2");
		
		assertEquals(" ",output);
	}
	
	@Test
	public void editStart() {
		output = logic.executeCommand("edit 12 start date 01/01/2015");
		
		assertEquals(" ",output);
	}
	
	@Test
	public void editEnd() {
		output = logic.executeCommand("edit 12 end date 02/01/2015");
		
		assertEquals(" ",output);
	}
	
	@Test
	public void removeDate() {
		output = logic.executeCommand("edit 12 start date");
		
		assertEquals(" ",output);
	}
	
	@Test
	public void searchTest() {
		output = logic.executeCommand("search he");
		
		assertEquals(" ",output);
	}

	@Test
	public void deleteTest() {
		output = logic.executeCommand("delete 5");
		
		assertEquals(" ",output);
	}
}
