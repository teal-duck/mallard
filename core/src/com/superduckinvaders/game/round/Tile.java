package com.superduckinvaders.game.round;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.superduckinvaders.game.GameScreen;
import com.superduckinvaders.game.graphics.Sprite;

public class Tile {

	public int x, y;
	public boolean solid;
	public TextureRegion sprite;

	public static Tile voidTile = new Tile(true, Sprite.VOID);
	public static Tile grass = new Tile(false, Sprite.GRASS);
	public static Tile grass2 = new Tile(false, Sprite.GRASS2);
	
	public Tile(boolean solid, Sprite sprite) {
		this.solid = solid;
		this.sprite = sprite.getTexture();
	}

	public void render(int x, int y, GameScreen gameScreen) {
		gameScreen.renderTile(x << 5, y << 5, this.getSprite());
	}

	public TextureRegion getSprite() {
		return sprite;
	}


}
