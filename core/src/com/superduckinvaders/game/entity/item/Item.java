package com.superduckinvaders.game.entity.item;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.entity.PhysicsEntity;


public class Item extends PhysicsEntity {

	/**
	 * The texture for this Item.
	 */
	protected TextureRegion texture;


	/**
	 * Create a new Item.
	 *
	 * @param parent
	 *                the parent round
	 * @param x
	 *                the x position.
	 * @param y
	 *                the y position.
	 * @param texture
	 *                the texture to initialize the item with.
	 */
	public Item(Round parent, float x, float y, TextureRegion texture) {
		super(parent, x, y);
		this.texture = texture;
		createStaticBody(PhysicsEntity.ITEM_BITS, PhysicsEntity.PLAYER_BITS, PhysicsEntity.NO_GROUP, true);
	}


	@Override
	public float getWidth() {
		return texture.getRegionWidth();
	}


	@Override
	public float getHeight() {
		return texture.getRegionHeight();
	}


	@Override
	public void update(float delta) {
		// Don't do anything...yet.
	}


	@Override
	public void render(SpriteBatch spriteBatch) {
		spriteBatch.draw(texture, getX(), getY());
	}

}
