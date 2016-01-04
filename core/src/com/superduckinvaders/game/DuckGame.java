package com.superduckinvaders.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DuckGame extends Game {
	SpriteBatch batch;
	Texture tiles;
	TextureRegion img;
	GameScreen gameScreen;
	@Override
	public void create() {
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	@Override
	public void render() {
		super.render();
	}
	
}
