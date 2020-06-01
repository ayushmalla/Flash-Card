package com.ayush.flashcards.app.com;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class FlashCardPlayer {
	
	private JTextArea display;
	private JTextArea Answer;
	private ArrayList<FlashCard> cardList;
	private Iterator cardIterator;
	private JButton showAnswer;
	private int CurrentCardIndex;
	private JFrame frame;
	private boolean isShowAnswer;
	private FlashCard currentCard;
	
	public void FlashCardPlayer() {
		
		//Build UI
		frame = new JFrame("FlashCardPlayer");
		JPanel mainpane = new JPanel();
		Font mFont = new Font("Helvetica Neue",Font.BOLD,21);
		
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		
		display = new JTextArea(10,20);
		display.setFont(mFont);
		
		JScrollPane qScrollPane = new JScrollPane(display);
		qScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		showAnswer = new JButton("Show answer");
		
		
		mainpane.add(qScrollPane);
		mainpane.add(showAnswer);
		showAnswer.addActionListener(new NextCardListener());
		
		//Add MenuBar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem LoadMenuItem = new JMenuItem("Load Card set");
		LoadMenuItem.addActionListener(new OpenMenuListener());
		
		fileMenu.add(LoadMenuItem);
		menuBar.add(fileMenu);
		
		//Add frame
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainpane);
		frame.setSize(640,500);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new FlashCardPlayer();
			}
		});

	}
	//to show that answer of the cards
	class NextCardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(isShowAnswer) {
				display.setText(currentCard.getAnswer());
				showAnswer.setText("Next Card");
				isShowAnswer =false;
				
			}else{
				//show next question
				if(cardIterator.hasNext()) {
					showNextCard();
				
				}else {
					display.setText("that was the last card");
					showAnswer.setEnabled(false);
					}
				}
			
		}
		
	}
	
	//to choose the file to open 
	class OpenMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fileOpen = new JFileChooser();
			fileOpen.showOpenDialog(frame);
			loadFile(fileOpen.getSelectedFile());
			
		}

		
	}
	
	private void loadFile(File selectedFile) {
		cardList = new ArrayList<FlashCard>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
			String line = null;
			while((line = reader.readLine()) != null){
				makeCard(line);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Show the first card
		cardIterator = cardList.iterator();
		showNextCard();
		};
	

	private void makeCard(String lineToParse) {
		StringTokenizer result = new StringTokenizer(lineToParse,"/");
		if(result.hasMoreTokens()) {
			FlashCard card = new FlashCard(result.nextToken(), result.nextToken());
			cardList.add(card);
			
			
		}
		
		
//		String[] result = lineToParse.split("/");// [question,answer]
//		
//		FlashCard card = new FlashCard(result[0], result[1]);
//		cardList.add(card);
		
	}
	private void showNextCard() {
		currentCard =  (FlashCard) cardIterator.next();
		
		display.setText(currentCard.getQuestion());
		showAnswer.setText("show Answer");
		isShowAnswer = true;
	}

}
