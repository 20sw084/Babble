package edu.westga.cs.babble.model;

/**
 * The tiles removed from a tile rack when the player tries to play a word.
 * 
 * @author April Parmer
 * @version November 7, 2014
 *
 */
public class Word extends TileGroup {
	private int score;
	
	/**
	 * Set score to 0.
	 */
	public Word() {
		this.score = 0;
	}
	
	/**
	 * Gets the score.
	 * @return score
	 */
	public int getScore() {
		for (Tile tile : this.tiles) {
			this.score += tile.getPointValue();
		}
		return this.score;
	}
}