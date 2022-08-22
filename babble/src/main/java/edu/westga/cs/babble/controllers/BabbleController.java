package edu.westga.cs.babble.controllers;

import edu.westga.cs.babble.model.Tile;
import edu.westga.cs.babble.model.TileBag;
import edu.westga.cs.babble.model.TileRack;
import edu.westga.cs.babble.model.EmptyTileBagException;
import edu.westga.cs.babble.model.PlayedWord;

//Newly Created file by Hira Rani

public class BabbleController {

	public static final int VALID_WORD = 0;
	public static final int MISSPELLED_WORD = 1;
	public static final int NOT_ENOUGH_TILES = 2;
	
	private TileBag bag;
	private WordDictionary dictionary;
	private TileRack rack;
	private int score;
	private PlayedWord playedWord;
	
	/**
	 * Creates a new BabbleController.
	 */
	public BabbleController()
	{
		this.bag = null;
		this.rack = null;
		this.score = 0;
		this.dictionary = new WordDictionary();
	}
	
	/**
	 * Initializes a new game.  This must be called before any other methods.
	 */
	public void startGame()
	{
		this.bag = new TileBag();
		this.rack = new TileRack(7);
		this.score = 0;
	}
	
	/**
	 * Fills (or tops off) the tile rack up to its maximum (if possible)
	 */
	public void refreshTileRack()
	{
		this.checkState();
		int numberOfTilesToDraw = this.rack.getNumberOfTilesNeeded();
		for(int i=0; i<numberOfTilesToDraw; i++)
		{
			if(this.bag.isEmpty()) break;
			Tile tile = null;
			try {
				tile = this.bag.drawTile();
			} catch (EmptyTileBagException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.rack.append(tile);
		}
	}
	
	/**
	 * Called at the beginning of the action methods (other than startGame()) to insure the rack and bag are not null.
	 */
	private void checkState()
	{
		if(this.bag == null || this.rack == null)
		{
			throw new IllegalStateException("Must call startGame() before any other methods");
		}
	}
	
	/**
	 * Gets all the Tiles on this rack as a string
	 * 
	 * @return the tiles' values, in order
	 */
	public String getTilesAsString()
	{
		this.checkState();
		return this.rack.toString();
	}
	
	/**
	 * Gets the total score earned so far
	 * 
	 * @return the score
	 */
	public int getScore()
	{
		return this.score;
	}
	
	/**
	 * Checks if a candidate word is good or not.
	 * 
	 * @param candidateText the word to check
	 * @return BabbleController.VALID_WORD if text is a usable word, BabbleController.MISSPELLED_WORD if cannot find text in the dictionary, BabbleController.NOT_ENOUGH_TILES if the current tile rack does not have the tiles to make the given word 
	 */
	public int checkCandidateWord(String candidateText)
	{	this.checkState();
		String normalizedText = candidateText.toUpperCase();
		if(!rack.canMakeWordFrom(normalizedText))
		{
			return NOT_ENOUGH_TILES;
		}
		else if(!this.dictionary.isValidWord(normalizedText))
		{
			return MISSPELLED_WORD;
		}
		else 
		{
			return VALID_WORD;
		}
	}
	
	/**
	 * Removes and returns the Word made from the tiles in text.
	 * @param text the tiles to remove
	 */
	public void removeWord(String text)
	{
		this.checkState();
		String normalizedText = text.toUpperCase();
		if(this.checkCandidateWord(normalizedText) != VALID_WORD)
		{
			throw new IllegalArgumentException("Cannot remove an invalid word / word requiring too many tiles");
		}
		
		this.playedWord = this.rack.removeWord(normalizedText);
		this.score += this.playedWord.getScore();
	}
	
	/**
	 * Game is over if the bag is empty
	 * 
	 * @return true if the game is over, false otherwise
	 */
	public boolean checkForEndGame()
	{
		this.checkState();
		return this.bag.isEmpty();
	}
	
	/**
	 * Gets the score of the last played word.
	 * 
	 * @return score of last word
	 */
	public int getLastWordScore() {
		return this.playedWord.getScore();
	}
	
	/**
	 * Gets the Tile bag.
	 * 
	 * @return tile bag
	 */
	public TileBag getTileBag() {
		return this.bag;
	}
	
	/**
	 * Returns if tile rack is empty.
	 * 
	 * @return true if empty
	 */
	public boolean emptyRack() {
		if (this.rack.getTiles().size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
