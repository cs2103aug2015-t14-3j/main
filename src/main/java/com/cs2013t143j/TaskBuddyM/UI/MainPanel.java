package com.cs2013t143j.TaskBuddyM.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextField;
import com.cs2013t143j.TaskBuddyM.Logic.Logic;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;


public class MainPanel extends JPanel implements ActionListener {

    protected WebLabel actionLabel;
    protected JTextArea textArea;
    private Logic logic;
    private JTable table;
    private JPanel main_1;
    
    private String[] columns = {"Description", "Start Date", "End Date"};

    MainPanel(Logic l){
        setLayout(new BorderLayout());
        logic = l;
        main_1 = new JPanel();
        main_1.setOpaque(false);
        main_1.setLayout(new BorderLayout());
        main_1.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        initDisplayScreen(main_1);
        
        initCommandLinePanel(main_1);
//        main.setBorder(
//                BorderFactory.createCompoundBorder(
//                        BorderFactory.createTitledBorder("Your tt"),
//                        BorderFactory.createEmptyBorder(5,5,5,5)));
        add(main_1);
        
    }
	

	private void initDisplayScreen(JPanel main) {
        JPanel textControlsPane = new JPanel();
        textControlsPane.setOpaque(false);
        textControlsPane.setLayout(new BorderLayout());
        textControlsPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        //textControlsPane.setLayout(new BoxLayout(textControlsPane, BoxLayout.Y_AXIS));
        textArea = new JTextArea ("Welcome to TaskBuddy!\n you could:\n  add\n  display\n  delete\n  .......");
        textArea.setBorder(new EmptyBorder(10, 20, 20, 20));
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setFont(new Font("augie",Font.PLAIN, 20));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

//        JScrollPane scroll = new JScrollPane (textArea, 
//        		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        scroll.setOpaque(false);
//        actionLabel = new WebLabel("...Welcome to TaskBuddy....well, I'm not ready yet~\n check later");
//        actionLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        textControlsPane.add(textArea,BorderLayout.NORTH);
        textControlsPane.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Your Screen"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        
        main.add(textControlsPane,BorderLayout.CENTER);
               
        String[][] data = null;
        table = new JTable(new DefaultTableModel(data, columns)){
            {
            	setOpaque(false);
            	setShowGrid(false);
            	//setShowHorizontalLines(true);
            	setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{
                    setOpaque(false);
                }});
            }
//          @Override
//          public boolean isCellEditable(int data, int columns)
//          {
//              return true;
//          }
//          @Override
//          public Component prepareRenderer(TableCellRenderer r, int data, int columns)
//          {
//              Component c = super.prepareRenderer(r, data, columns);
//
//              if (data % 2 == 0)
//                  c.setBackground(Color.WHITE);
//
//              else
//                  c.setBackground(Color.LIGHT_GRAY);
//
//              return c;
//          }
        };
        table.setFont(new Font("augie", Font.PLAIN, 20));
        table.setRowHeight(28);
        
		JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		textControlsPane.add(scroll, BorderLayout.CENTER);
	}
	
	private void initCommandLinePanel(JPanel main) {
		JPanel commandLineTextField = new JPanel();
		commandLineTextField.setOpaque(false);
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
	
	private void updateMsgAndTable(String output) {
		String[] lines = output.split("\\r?\\n");
		int lineNumber = lines.length;
		textArea.setText(lines[0]);
		String[][] data = null;
		DefaultTableModel defaultTableModel = new DefaultTableModel(data,columns);
		for(int i = 1; i < lineNumber; i++){
			String line = lines[i];
			String[] fields = line.split("\\t");
			defaultTableModel.addRow(fields);
		}
		table.setModel(defaultTableModel);
	}
	
	@Override
    public void paintComponent(Graphics g) {
		URL url = getClass().getResource("/papertexture14.jpg");
		Image bg = new ImageIcon(url).getImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = ((WebTextField)e.getSource()).getText();
        if ("commandLine".equals(e.getActionCommand())) {
        	String output = logic.executeCommand(input);
        	updateMsgAndTable(output);
        	WebTextField inputField = (WebTextField)e.getSource();
        	inputField.setText("");
        } 
    }
}
