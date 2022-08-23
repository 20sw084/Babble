package edu.westga.cs.babble.views;

import java.util.Scanner;

import javax.swing.JFrame;


public class GuiWindowBuilderLayout extends JFrame
{

	public void run() 
	{
		GuiController game = new GuiController();
		Scanner scanner = new Scanner(System.in);
		game.startGame();
		
		while(!game.checkForEndGame())
		{
			game.refreshTileRack();
			System.out.println("Current score: " + game.getScore());
			System.out.println("Current hand: " + game.getTilesAsString());
			System.out.print("Word?> ");
			String myGuess = scanner.next();
			int guessResult = game.checkCandidateWord(myGuess);
			switch(guessResult)
			{
			case GuiController.VALID_WORD:
				System.out.println("Good job!");
				game.removeWord(myGuess);
				break;
			case GuiController.NOT_ENOUGH_TILES:
				System.out.println("Not enough tiles to make that word. Please try again.");
				break;
			case GuiController.MISSPELLED_WORD:
				System.out.println("Misspelled word.  Please try again.");
				break;
			}
		}
		System.out.println("Game over!");
		System.out.println("Final score: " + game.getScore());
	}

}
