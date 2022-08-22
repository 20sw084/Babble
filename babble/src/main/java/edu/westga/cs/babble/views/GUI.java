package edu.westga.cs.babble.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.westga.cs.babble.controllers.BabbleController;

//Newly Created file by Hira Rani

/**
 * Graphical user interface for the application.
 * 
 * @author April Parmer
 * @version December 10, 2014
 */
public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JLabel message;
	private String playedWord;
	private JButton playWordButton;
	private JTextField textField;
	private JLabel lastWordPlayed;
	private JLabel lastWordPoints;
	private JLabel totalScore;
	private JLabel tilesInBag;
	private JLabel tileRack;
	private BabbleController game;
	
	public GUI() {
	}
	
	/**
	 * Directs the flow of the application.
	 */
	public void run() {		
		this.game = new BabbleController();
		this.game.startGame();
		this.game.refreshTileRack();
		
		this.frame = new JFrame();
		this.frame.setPreferredSize(new Dimension(500,200));
		
		this.frame.setTitle("Babble: A Super-Fun Word Game");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.frame.setLayout(new BorderLayout());
		
		//Status Message
		this.message = new JLabel("Welcome to Babble!", SwingConstants.CENTER);
		this.frame.add(this.message, BorderLayout.SOUTH);
		
		//North Panel (Tile Rack)
		JPanel northPanel = new JPanel();
		this.tileRack = new JLabel(this.game.getTilesAsString(), SwingConstants.CENTER);		
		this.tileRack.setFont(new Font("Arial", Font.BOLD, 30));
		northPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		northPanel.add(this.tileRack);
		this.frame.add(northPanel, BorderLayout.NORTH);
		
		//West Panel (Score and Tile Bag Update)
		JPanel westPanel = new JPanel();
		this.totalScore = new JLabel("Total Score: " + this.game.getScore());
		this.tilesInBag = new JLabel("Tiles Left In Bag: " + this.game.getTileBag().getNumberOfTilesLeft());
		westPanel.setLayout(new BorderLayout());
		westPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		westPanel.add(this.totalScore, BorderLayout.NORTH);
		westPanel.add(this.tilesInBag, BorderLayout.CENTER);
		this.frame.add(westPanel, BorderLayout.WEST);
		
		//East Panel (Last Word and Last Points)
		JPanel eastPanel = new JPanel();
		this.lastWordPlayed = new JLabel("Last Word Played: ", SwingConstants.RIGHT);
		this.lastWordPoints = new JLabel("Last Word Points: ", SwingConstants.RIGHT);
		eastPanel.setLayout(new BorderLayout());
		eastPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		eastPanel.add(this.lastWordPlayed, BorderLayout.NORTH);
		eastPanel.add(this.lastWordPoints, BorderLayout.CENTER);
		this.frame.add(eastPanel, BorderLayout.EAST);
		
		//Center Panel (TextField, Play Button, and Status Message)
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		this.textField = new JTextField(5);
		centerPanel.add(new JLabel("Word: "));
		centerPanel.add(this.textField);
		
		this.playWordButton = new JButton("Play");
		this.playWordButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				GUI.this.playedWord = GUI.this.textField.getText();
				int guessResult = GUI.this.game.checkCandidateWord(GUI.this.playedWord);
				switch(guessResult) {
				case BabbleController.VALID_WORD:
					GUI.this.game.removeWord(GUI.this.playedWord);
					GUI.this.game.refreshTileRack();
					GUI.this.tileRack.setText(GUI.this.game.getTilesAsString());
					GUI.this.textField.setText("");
					GUI.this.totalScore.setText("Total Score: " + GUI.this.game.getScore());
					GUI.this.tilesInBag.setText("Tiles Left In Bag: " + GUI.this.game.getTileBag().getNumberOfTilesLeft());
					GUI.this.lastWordPlayed.setText("Last Word Played: " + GUI.this.playedWord.toUpperCase());
					GUI.this.lastWordPoints.setText("Last Word Points: " + GUI.this.game.getLastWordScore());
					
					boolean empty = GUI.this.game.emptyRack();
					if (empty) {
						GUI.this.message.setText("Game over! Final Score: " + GUI.this.game.getScore());
						GUI.this.textField.disable();
						GUI.this.playWordButton.disable();
					} else {
						GUI.this.message.setText("Great! Word Accepted!");
					}
					break;
				case BabbleController.NOT_ENOUGH_TILES:
					GUI.this.message.setText("Not enough tiles. Please try again.");
					break;
				case BabbleController.MISSPELLED_WORD:
					GUI.this.message.setText("Misspelled word. Please try again.");
					break;	
				}
			}
			
		});
		centerPanel.add(this.playWordButton);
		this.frame.add(centerPanel, BorderLayout.CENTER);
		this.frame.pack();
		this.frame.setVisible(true);
		this.frame.getRootPane().setDefaultButton(this.playWordButton);
	}

}
