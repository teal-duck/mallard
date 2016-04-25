package com.superduckinvaders.game.entity.mob;


import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.Assets;


/**
 * A guy with the gun. Initializes the RangedMob with a gunner texture.
 */
public class GunnerMob extends RangedMob {

	public static final int MOVE_SPEED = 3;


	/**
	 * Create a new GunnerMob.
	 *
	 * @param parent
	 *                the round parent.
	 * @param x
	 *                the initial x position.
	 * @param y
	 *                the initial y position.
	 * @param demented
	 *                true if the mob is created in a poor mental state.
	 */
	public GunnerMob(Round parent, float x, float y, boolean demented) {
		super(parent, x, y, 2, Assets.rangedBadGuyNormal, GunnerMob.MOVE_SPEED, demented);
	}


	/**
	 * Create a new GunnerMob. Defaults demented to false.
	 *
	 * @param parent
	 *                the round parent.
	 * @param x
	 *                the initial x position.
	 * @param y
	 *                the initial y position.
	 */
	public GunnerMob(Round parent, float x, float y) {
		this(parent, x, y, false);
	}
}
