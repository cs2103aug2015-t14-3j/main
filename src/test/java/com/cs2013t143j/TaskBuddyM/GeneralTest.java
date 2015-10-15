package com.cs2013t143j.TaskBuddyM;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs2013t143j.TaskBuddyM.Parser.TBParserStub;

public class GeneralTest {

	@Test
	public void testExecuteCommand() {
		TBParserStub stub = new TBParserStub();
		String t1 = stub.testDatePraser("lalala");
		String t2 = stub.testDatePraser("3 day after today");
		String[] testArray = {t1,t2};
		String[] expectArray = {"","12 18/10/2015"};
		assertArrayEquals(testArray,expectArray);
	}
	

}

