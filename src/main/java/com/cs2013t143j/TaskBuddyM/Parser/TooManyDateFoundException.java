package com.cs2013t143j.TaskBuddyM.Parser;

public class TooManyDateFoundException extends Exception
{
	//Parameterless Constructor
      public TooManyDateFoundException() {}

      //Constructor that accepts a message
      public TooManyDateFoundException(String message)
      {
         super(message);
      }
 }
