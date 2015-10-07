package com.cs2013t143j.TaskBuddyM;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextField;

public class MainPanel extends JPanel implements ActionListener {

    protected WebLabel actionLabel;
    protected JTextArea textArea;

    MainPanel(){

        setLayout(new BorderLayout());

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        main.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        initDisplayScreen(main);
        
        initCommandLinePanel(main);
//        main.setBorder(
//                BorderFactory.createCompoundBorder(
//                        BorderFactory.createTitledBorder("Your tt"),
//                        BorderFactory.createEmptyBorder(5,5,5,5)));
        add(main);
        
    }
	

	private void initDisplayScreen(JPanel main) {
        JPanel textControlsPane = new JPanel();
        textControlsPane.setLayout(new BorderLayout());
        textControlsPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        //textControlsPane.setLayout(new BoxLayout(textControlsPane, BoxLayout.Y_AXIS));
        textArea = new JTextArea ("Welcome to TaskBuddy!\n you could:\n  add\n  display\n  delete\n  .......");
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setFont(new Font("Brush Script MT",Font.PLAIN, 20));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

//        JScrollPane scroll = new JScrollPane (textArea, 
//        		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        scroll.setOpaque(false);
//        actionLabel = new WebLabel("...Welcome to TaskBuddy....well, I'm not ready yet~\n check later");
//        actionLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        textControlsPane.add(textArea,BorderLayout.CENTER);
        textControlsPane.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Your Screen"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        
        main.add(textControlsPane,BorderLayout.CENTER);
	}
	
	private void initCommandLinePanel(JPanel main) {
		JPanel commandLineTextField = new JPanel();
        //commandLineTextField.setLayout(new BoxLayout(commandLineTextField, BoxLayout.Y_AXIS));
        
		WebLabel commandPop = new WebLabel("command line: ");
		commandLineTextField.add(commandPop);
        
        WebTextField commandLine = new WebTextField(53);
        //commandLine.setText("test");
        commandLine.setActionCommand("commandLine");
        commandLine.addActionListener(this);
        
        commandLineTextField.add(commandLine);
        commandLineTextField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Your Input"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        main.add(commandLineTextField,BorderLayout.PAGE_END);
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = ((WebTextField)e.getSource()).getText();
        if ("commandLine".equals(e.getActionCommand())) {
        	textArea.setText("Renew");
        	WebTextField inputField = (WebTextField)e.getSource();
        	inputField.setText("");
        } 
    }
}
