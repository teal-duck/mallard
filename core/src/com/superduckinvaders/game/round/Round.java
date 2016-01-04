package com.superduckinvaders.game.round;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.superduckinvaders.game.GameScreen;
import com.superduckinvaders.game.entity.Entity;

public class Round {

	private int WIDTH, HEIGHT;
	private int[] tiles;
	
	private List<Entity> entities = new ArrayList<Entity>();
	
	public Round() {
		WIDTH = 100;
		HEIGHT = 100;
		tiles = new int[WIDTH * HEIGHT];
		Random random = new Random();
		for(int i = 0; i < tiles.length; i++) {
			tiles[i] = random.nextInt(2) + 1;
		}
	}

	public void update() {
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
	}	
	
	public void add(Entity e) {
		entities.add(e);
	}
	
	public void render(int xOffset, int yOffset, GameScreen gameScreen) {
		
		int xMin = xOffset >> 5;
		int xMax = ((xOffset + gameScreen.WIDTH) >> 5) + 1;
		int yMin = yOffset >> 5;
		int yMax = ((yOffset + gameScreen.HEIGHT) >> 5) + 1;

		for (int y = yMin; y < yMax; y++) {
			for (int x = xMin; x < xMax; x++) {
				getTile(x, y).render(x, y, gameScreen);
			}
		}
		
		for(int i = 0; i < entities.size(); i++) {
			//entities.get(i).render(gameScreen);
		}
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT)
			return Tile.voidTile;
		if (tiles[x + y * WIDTH] == 1)
			return Tile.grass;
		if (tiles[x + y * WIDTH] == 2)
			return Tile.grass2;
		return Tile.voidTile;
	}
}
