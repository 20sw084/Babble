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


public class Gui extends GuiWindowBuilderLayout 
{
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
	private GuiController game;
	
	public Gui() 
	{
		
	}
	
	/**
	 * Directs the flow of the application.
	 */
	public void run() 
	{		
		this.game = new GuiController();
		this.game.startGame();
		this.game.refreshTileRack();
		
		this.frame = new JFrame();
		this.frame.setPreferredSize(new Dimension(500,200)); 
		
		this.frame.setTitle("Babble: A Super-Fun Word Game");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.frame.setLayout(new BorderLayout());
		
		//Status Message
		this.message = new JLabel("Welcome to Babble!!!", SwingConstants.CENTER);
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
		this.playWordButton.addActionListener(new ActionListener() 
		{
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				Gui.this.playedWord = Gui.this.textField.getText();
				int guessResult = Gui.this.game.checkCandidateWord(Gui.this.playedWord);
				switch(guessResult) {
				case GuiController.VALID_WORD:
					Gui.this.game.removeWord(Gui.this.playedWord);
					Gui.this.game.refreshTileRack();
					Gui.this.tileRack.setText(Gui.this.game.getTilesAsString());
					Gui.this.textField.setText("");
					Gui.this.totalScore.setText("Total Score: " + Gui.this.game.getScore());
					Gui.this.tilesInBag.setText("Tiles Left In Bag: " + Gui.this.game.getTileBag().getNumberOfTilesLeft());
					Gui.this.lastWordPlayed.setText("Last Word Played: " + Gui.this.playedWord.toUpperCase());
					Gui.this.lastWordPoints.setText("Last Word Points: " + Gui.this.game.getLastWordScore());
					
					boolean empty = Gui.this.game.emptyRack();
					if (empty) {
						Gui.this.message.setText("Game over! Final Score: " + Gui.this.game.getScore());
						Gui.this.textField.disable();
						Gui.this.playWordButton.disable();
					} else {
						Gui.this.message.setText("Great! Word Accepted!");
					}
					break;
				case GuiController.NOT_ENOUGH_TILES:
					Gui.this.message.setText("Not enough tiles. Please try again.");
					break;
				case GuiController.MISSPELLED_WORD:
					Gui.this.message.setText("Misspelled word. Please try again.");
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
