package com.cs2013t143j.TaskBuddyM.UI;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

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

public class TBGUI {
    public static void main ( String[] args )
    {
        // You should work with UI (including installing L&F) inside Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater ( new Runnable ()
        {
            public void run ()
            {
                // Install WebLaF as application L&F
                WebLookAndFeel.install ();
                Logic logic = new Logic();
                JFrame myFrame = new JFrame("TaskBuddy");
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
}
