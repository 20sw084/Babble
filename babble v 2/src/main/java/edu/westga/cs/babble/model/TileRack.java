package edu.westga.cs.babble.model;


/**
 * A small collection of Tiles that holds the currently-drawn set of tiles.
 * @author lewisb
 *
 */
import java.util.ArrayList;


/**
 * The tiles on the rack.
 * 
 * @author April Parmer
 * @version November 7, 2014
 * 
 */
public class TileRack extends TileGroup {
	private int maxSize;

	/**
	 * Constructs a TileRack of a particular size.
	 * @param maxSize of the TileRack
	 */
	public TileRack(int maxSize) {
		this.maxSize = maxSize;
	}
	
	/**
	 * Tests if a word can be made using the Tiles on the TileRack.
	 * @param text - word to use
	 * @return true if word can be made from tiles on rack
	 * @return false if word cannot be made from tiles on rack
	 */
	public boolean canMakeWordFrom(String text) {
		ArrayList<Tile> tilesCopy = new ArrayList<Tile>(this.tiles);
		String textCopy = text;
		
		for (int i = 0; i < textCopy.length(); i++) 
		{
			char c = textCopy.charAt(i);
			boolean foundChar = false;
			for (int j = 0; j < tilesCopy.size() && !foundChar; j++) 
			{
				if (c == tilesCopy.get(j).getLetter())
				{
					tilesCopy.remove(j);
					foundChar = true;
				}
			}
			if (!foundChar)
				return false;
		}
		return true;
	}
	
	/**
	 * Removes a word from the TileRack
	 * @param text - word to be removed
	 * @return word - for scoring purposes
	 */
	public Word removeWord(String text) {
		Word word = new Word();
		String textCopy = text;
		
		for (int i = 0; i < textCopy.length(); i++) 
		{
			boolean foundChar = false;
			char c = textCopy.charAt(i);
			for (int j = 0; j < this.tiles.size() && !foundChar; j++) 
			{
				if (c == this.tiles.get(j).getLetter())
				{
					word.append(this.tiles.get(j));
					this.tiles.remove(j);
					foundChar = true;
				}
			}
		}
		return word;		
	}
	
	/**
	 * Gets the number of tiles needed to reach the max number.
	 * @return numberNeeded
	 */
	public int getNumberOfTilesNeeded() {
		int i = getTiles().size();
		int numberNeeded = this.maxSize - i;
		return numberNeeded;
	}
}
