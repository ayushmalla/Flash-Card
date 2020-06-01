package com.ayush.flashcards.app.com;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class FlashCardBuilder {
	private JFrame frame;
	private JTextArea question;
	private JTextArea answer;
	private ArrayList<FlashCard> cardList;
	

	public void FlashCardBuilder() {
		//Build the user interface
		frame = new JFrame("FlashCard");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	//Create jpanel to hold everything
		JPanel mainpanel = new JPanel();
		
		//Create Font for question
		Font greatFont = new Font("Helvetica Neue",Font.BOLD,21);
		question = new JTextArea(6,20);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(greatFont);
		
		//JScrollPane For Question
		JScrollPane qJScrollPane = new JScrollPane(question);
		qJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		//Answer Area for font 
		answer = new JTextArea(6,20);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(greatFont);
		
		
		//JscrollPane for Answer
		JScrollPane aJScrollPane = new JScrollPane(answer);
		aJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		//JButton
		JButton nextButton = new JButton("Next Card");
		
		
		cardList = new ArrayList<FlashCard>();
		
		
		//JLabels
		JLabel qJLable = new JLabel("Question");
		JLabel aJLable = new JLabel("Answer");
 		
		
		//Add components to mainPanel
		mainpanel.add(qJLable);
		mainpanel.add(qJScrollPane);
		mainpanel.add(aJLable);
		mainpanel.add(aJScrollPane);
		mainpanel.add(nextButton);
		
		nextButton.addActionListener(new NextCardListener());
		
		//Menu Bar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem newMenuItem = new JMenuItem("New");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		
		menuBar.add(fileMenu);
		
		frame.setJMenuBar(menuBar);
		
		//Add eventListener
		newMenuItem.addActionListener(new NewMenuItemListener());
		saveMenuItem.addActionListener(new SaveMenuListener());
		
		
		//Add to the frame
	frame.getContentPane().add(BorderLayout.CENTER, mainpanel);
		frame.setSize(500,600);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					new FlashCardBuilder();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

		});

	}
	
	class NextCardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			FlashCard card = new FlashCard(question.getText(), answer.getText());
			cardList.add(card);
			clearCard();
		}

		
		
	}
	 
	class NewMenuItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//create a flashcard
			
		}
		
		
	}
	class SaveMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			FlashCard card = new FlashCard(question.getText(), answer.getText());
			cardList.add(card);
			
			//Create a file dialog with filechooser
			JFileChooser fileSave = new JFileChooser();
			fileSave.showSaveDialog(frame);
			saveFile(fileSave.getSelectedFile());
		}

		
	}
	private void clearCard() {
		question.setText("");
		answer.setText("");
		question.requestFocus(); 
		
	}
	private void saveFile(File selectedFile) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
			
			Iterator<FlashCard> cardIterator = cardList.iterator();
			while(cardIterator.hasNext()) {
				FlashCard card = (FlashCard) cardIterator.next();
				writer.write(card.getQuestion() + "/");
				writer.write(card.getAnswer() + "\n");
				
				//Format to be like this: what is your name/Ayush
			}
//			
//			for(FlashCard card : cardList) {
//				writer.write(card.getQuestion() + "/");
//				writer.write(card.getAnswer() + "\n");
//			}
//			
			
		} catch(Exception e){
		System.out.println("couldnot write to the file");
		e.printStackTrace();
	}
	 
	}
}	
