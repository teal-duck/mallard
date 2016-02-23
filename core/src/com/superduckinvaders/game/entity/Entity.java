package com.superduckinvaders.game.entity;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.TextureSet;


/**
 * Represents an object in the game. TODO(avinash): Switch to use vectors.
 */
public abstract class Entity {
	/**
	 * The round that this Entity is in.
	 */
	protected Round parent;

	/**
	 * The x and y coordinates of this Entity.
	 */
	protected float x, y;
	protected float width, height;
	public static final float METRES_PER_PIXEL = 1 / 16f;
	public static final float PIXELS_PER_METRE = 1 / Entity.METRES_PER_PIXEL;

	/**
	 * Whether or not to remove this Entity on the next frame.
	 */
	protected boolean removed = false;


	/**
	 * Create a new Entity.
	 *
	 * @param parent
	 *                the parent round.
	 * @param x
	 *                the initial x position.
	 * @param y
	 *                the initial y position.
	 */
	public Entity(Round parent, float x, float y) {
		this.parent = parent;
		this.x = x;
		this.y = y;
	}


	/**
	 * Returns the x coordinate of the entity
	 *
	 * @return the x coordinate of this Entity
	 */
	public float getX() {
		return x;
	}


	/**
	 * Returns the y coordinate of the entity
	 *
	 * @return the y coordinate of this Entity
	 */
	public float getY() {
		return y;
	}


	public Vector2 getPosition() {
		return new Vector2(x, y);
	}


	public Vector2 getCentre() {
		return getPosition().add(getWidth() / 2f, getHeight() / 2f);
	}


	/**
	 * Returns the distance between this Entity and the specified coordinates.
	 *
	 * @param dest
	 *                the vector to compare with
	 * @return the distance between this Entity and the coordinates, in pixels
	 */
	public float distanceTo(Vector2 dest) {
		return vectorTo(dest).len();
	}


	public float distanceTo(float x, float y) {
		return distanceTo(new Vector2(x, y));
	}


	public Vector2 vectorTo(Vector2 dest) {
		return dest.cpy().sub(getCentre());
	}


	/**
	 * Returns the angle between this Entity and the specified coordinates.
	 *
	 * @param dest
	 *                the vector to compare with
	 * @return the angle between this Entity and the coordinates, in radians
	 */
	public float angleTo(Vector2 dest) {
		return vectorTo(dest).angleRad();
	}


	public float angleTo(float x, float y) {
		return angleTo(new Vector2(x, y));
	}


	/**
	 * Returns the direction to the specified coordinates from this Entity.
	 *
	 * @param x
	 *                the x coordinate to compare with
	 * @param y
	 *                the y coordinate to compare with
	 * @return the direction the coordinates are in relative to this Entity
	 */
	public TextureSet.FaceDirection directionTo(float x, float y) {
		float angle = angleTo(x, y);

		if ((angle < ((Math.PI * 3) / 4)) && (angle >= (Math.PI / 4))) {
			return TextureSet.FaceDirection.BACK;
		} else if ((angle < (Math.PI / 4)) && (angle >= (-Math.PI / 4))) {
			return TextureSet.FaceDirection.RIGHT;
		} else if ((angle < (-Math.PI / 4)) && (angle >= ((-Math.PI * 3) / 4))) {
			return TextureSet.FaceDirection.FRONT;
		} else {
			return TextureSet.FaceDirection.LEFT;
		}
	}


	/**
	 * Returns the width of the entity
	 *
	 * @return the width of this Entity
	 */
	public float getWidth() {
		return width;
	}


	/**
	 * Returns the height of the entity
	 *
	 * @return the height of this Entity
	 */
	public float getHeight() {
		return height;
	}


	public Vector2 getSize() {
		return new Vector2(getWidth(), getHeight());
	}


	/**
	 * Returns if this entity should be removed
	 *
	 * @return whether this Entity has been removed
	 */
	public boolean isRemoved() {
		return removed;
	}


	/**
	 * Updates the state of this Entity.
	 *
	 * @param delta
	 *                how much time has passed since the last update
	 */
	public void update(float delta) {
	}


	/**
	 * Disposes any disposable objects. TODO(avinash): Make this abstract?
	 */
	public void dispose() {
	}


	/**
	 * Renders this Entity.
	 *
	 * @param spriteBatch
	 *                the sprite batch on which to render
	 */
	public abstract void render(SpriteBatch spriteBatch);
}
