package com.superduckinvaders.game.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sprite {

	private static Texture tiles = new Texture("Tiles.png");
	public static final Sprite VOID = new Sprite(tiles,32,32,32,32);
	public static final Sprite GRASS = new Sprite(tiles,0,32,32,32);
	public static final Sprite GRASS2 = new Sprite(tiles,32,0,32,32);
	
	private TextureRegion texture;
	
	public Sprite(Texture spritesheet, int x, int y, int dx, int dy) {
		this.texture = new TextureRegion(spritesheet,x,y,dx,dy);
	}
	
	public TextureRegion getTexture() {
		return texture;
	}

}
