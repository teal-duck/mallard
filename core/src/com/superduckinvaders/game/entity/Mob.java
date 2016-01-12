
package com.superduckinvaders.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.superduckinvaders.game.graphics.TextureSet;
import com.superduckinvaders.game.round.Round;

public class Mob extends Character {

	// TODO: finish this

	/**
	 * The texture set to use for this Mob.
	 */
	private TextureSet textureSet;


	public Mob(Round parent, int x, int y, int health, TextureSet textureSet) {
		super(parent, x, y, health);

		this.textureSet = textureSet;
	}

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
		// TODO: implement mob update code
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		spriteBatch.draw(textureSet.getTexture(facing, stateTime), (int) x, (int) y);
	}
}
