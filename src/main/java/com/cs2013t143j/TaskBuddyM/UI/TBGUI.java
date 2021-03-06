package com.cs2013t143j.TaskBuddyM.UI;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.alee.laf.WebLookAndFeel;
import com.cs2013t143j.TaskBuddyM.Logic.Logic;


import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

//@@author A0145680A

public class TBGUI {
    public static void main ( String[] args )
    {
    	// new thread for GUI
        SwingUtilities.invokeLater ( new Runnable ()
        {
            public void run ()
            {
                // Install WebLaF as application L&F
                WebLookAndFeel.install ();
                Logic logic = new Logic();
                JFrame myFrame = new JFrame("TaskBuddy");
                loadResources();
                myFrame.setLocationRelativeTo(null);
                myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                myFrame.setSize(800,600);
                //myFrame.setPreferredSize(new Dimension(800,600));
                myFrame.setLayout(new BorderLayout());
                MainPanel menuPanel = new MainPanel(logic);

                myFrame.add(menuPanel,BorderLayout.CENTER);
                myFrame.setVisible(true);
            }
        } );
    }
    
    // load customized font
    private static void loadResources() {
    	try {
    	     GraphicsEnvironment ge = 
    	         GraphicsEnvironment.getLocalGraphicsEnvironment();
    	     URL url = TBGUI.class.getResource("/augie.ttf");
    	     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, TBGUI.class.getResourceAsStream("/augie.ttf")));
    	} catch (IOException|FontFormatException e) {
    	     //Handle exception
    		System.out.println("load error "+e.getMessage());
    	}
	}
}
