package edu.westga.cs.babble.model;

import java.util.*;

/**
 * Container for Tiles, allows drawing random Tiles.  When created, it self-populates with a Scrabble-like set of tiles.
 * @author lewisb
 *
 */
import java.util.ArrayList;
import java.util.Random;

/**
 * Defines a bag of Tiles to be used in a game of Babble.
 * 
 * @author April Parmer
 * @version November 7, 2014
 *
 */
public class TileBag 
{
	private ArrayList<Tile> tiles;
	
	/**
	 * Constructs a bag of Tiles using the standard Scrabble tile amounts and points.
	 */
	public TileBag() {
		this.tiles = new ArrayList<Tile>();
		int i = 0;
		
		for (i = 0; i < 12; i++) {
			this.tiles.add(new Tile('E', 1));
		}
		
		for (i = 0; i < 9; i++) {
			this.tiles.add(new Tile('A', 1));
			this.tiles.add(new Tile('I', 1));
		}
		
		for (i = 0; i < 8; i++) {
			this.tiles.add(new Tile('O', 1));
		}
		
		for (i = 0; i < 6; i++) {
			this.tiles.add(new Tile('N', 1));
			this.tiles.add(new Tile('R', 1));
			this.tiles.add(new Tile('T', 1));
		}
		
		for (i = 0; i < 4; i++) {
			this.tiles.add(new Tile('L', 1));
			this.tiles.add(new Tile('S', 1));
			this.tiles.add(new Tile('U', 1));
			this.tiles.add(new Tile('D', 2));
		}
		
		for (i = 0; i < 3; i ++) {
			this.tiles.add(new Tile('G', 2));
		}
		
		for (i = 0; i < 2; i++) {
			this.tiles.add(new Tile('B', 3));
			this.tiles.add(new Tile('C', 3));
			this.tiles.add(new Tile('M', 3));
			this.tiles.add(new Tile('P', 3));
			this.tiles.add(new Tile('F', 4));
			this.tiles.add(new Tile('H', 4));
			this.tiles.add(new Tile('V', 4));
			this.tiles.add(new Tile('W', 4));
			this.tiles.add(new Tile('Y', 4));
		}
		
		this.tiles.add(new Tile('K', 5));
		this.tiles.add(new Tile('J', 8));
		this.tiles.add(new Tile('X', 8));
		this.tiles.add(new Tile('Q', 10));
		this.tiles.add(new Tile('Z', 10));
	}
	
	
	/**
	 * Draws a random Tile from the bag.
	 * @return tile from the bag
	 */
	public Tile drawTile() {
		if (isEmpty()) {
			throw new IllegalStateException("Not enough tiles in bag");
		}
		Random rand = new Random();
		int index = rand.nextInt(this.tiles.size());
		Tile tile = tiles.get(index);
		this.tiles.remove(index);
		return tile;
	}
	
	/**
	 * Tests if the bag is empty.
	 * @return true if bag is empty
	 * @return false if bag is not empty
	 */
	public boolean isEmpty() {
		if (this.tiles.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Gets the number of Tiles left in the bag.
	 */
	public int getNumberOfTilesLeft() {
		return this.tiles.size();
	}
}
