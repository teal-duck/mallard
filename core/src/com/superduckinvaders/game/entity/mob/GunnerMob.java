package com.superduckinvaders.game.entity.mob;


import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.Assets;


/**
 * A guy with the gun. Initializes the RangedMob with a gunner texture.
 */
public class GunnerMob extends RangedMob {
	/**
	 * Create a new GunnerMob.
	 *
	 * @param parent
	 *                the round parent.
	 * @param x
	 *                the initial x position.
	 * @param y
	 *                the initial y position.
	 */
	public GunnerMob(Round parent, float x, float y) {
		super(parent, x, y, 2, Assets.rangedBadGuyNormal, 3);
	}
}
