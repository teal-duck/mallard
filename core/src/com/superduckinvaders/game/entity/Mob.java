
package com.superduckinvaders.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.superduckinvaders.game.graphics.TextureSet;
import com.superduckinvaders.game.round.Round;
import com.superduckinvaders.game.ai.*;

public class Mob extends Character {

	// TODO: finish this

	/**
	 * The texture set to use for this Mob.
	 */
	private TextureSet textureSet;
	
	/**
	 * AI class for the mob
	 */
	private AI ai;
	
	/**
	 * set new AI for the current mob
	 * @param newAI new AI type to assign
	 */
	public void setAI(AI.type newAI){
		switch (newAI){
		case ZOMBIE:
			this.ai = new ZombieAI();
			break;
		case DUMMY:
			this.ai = new DummyAI();
			break;
		}
	}
	
	/**
	 * speed of the mob in pixels per second
	 */
	private int speed;
	
	public void setSpeed(int newSpeed){
		this.speed = newSpeed;
	}
	
	/**
	 * change where the given mob moves to according to its speed and a new direction vector
	 * @param dirX x component of the direction vector
	 * @param dirY y component of the direction vector
	 */
	public void setVelocity(int dirX, int dirY){
		double magnitude = Math.sqrt(dirX^2 + dirY^2);
		this.velocityX = (dirX/magnitude)*speed;
		this.velocityY = (dirY/magnitude)*speed;
	}

	public Mob(Round parent, int x, int y, int health, TextureSet textureSet, int speed) {
		super(parent, x, y, health);

		this.textureSet = textureSet;
		this.speed = speed;
	}
	//test
	
	

	@Override
	public int getWidth() {
		return textureSet.getTexture(TextureSet.FACING_FRONT, 0).getRegionWidth();
	}

	@Override
	public int getHeight() {
		return textureSet.getTexture(TextureSet.FACING_FRONT, 0).getRegionHeight();
	}

	@Override
	public void update(float delta) {
		ai.update(this);
		super.update(delta);
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		spriteBatch.draw(textureSet.getTexture(facing, stateTime), (int) x, (int) y);
	}
}
