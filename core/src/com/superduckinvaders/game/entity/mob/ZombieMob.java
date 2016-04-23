package com.superduckinvaders.game.entity.mob;


import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.Assets;

/**
 * Chases the player until it gets close enough to cause damage. Ever seen Shawn of the Dead? Good movie, right?
 */
public class ZombieMob extends MeleeMob {

	public static final int MOVE_SPEED = 5;

	/**
	 * Create a ZombieMob
	 *
	 * @param parent
	 *                the round parent
	 * @param x
	 *                the starting x position
	 * @param y
	 *                the starting y position
	 * @param demented
	 *                the mob's initial mental state
	 */
	public ZombieMob(Round parent, float x, float y, boolean demented) {
		super(parent, x, y, 4, Assets.badGuyNormal, MOVE_SPEED, demented);
	}


	/**
	 * Create a ZombieMob, defaulting demented to false.
	 *
	 * @param parent
	 *                the round parent
	 * @param x
	 *                the starting x position
	 * @param y
	 *                the starting y position
	 */
	public ZombieMob(Round parent, float x, float y) {
		this(parent, x, y, false);
	}
}
