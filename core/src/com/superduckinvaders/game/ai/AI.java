package com.superduckinvaders.game.ai;


import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.entity.mob.Mob;


/**
 * Defines movement and attacking behaviour for Mobs.
 */
public abstract class AI {

	/**
	 * The round the Mob this AI controls is a part of.
	 */
	protected Round round;


	/**
	 * Initialises this AI.
	 *
	 * @param round
	 *                the round the Mob this AI controls is a part of
	 */
	public AI(Round round) {
		this.round = round;
	}


	/**
	 * Updates this AI.
	 *
	 * @param mob
	 *                reference to the Mob using this AI
	 * @param delta
	 *                time since the previous update
	 */
	public abstract void update(Mob mob, float delta);
}
