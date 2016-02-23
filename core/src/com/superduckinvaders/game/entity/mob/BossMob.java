package com.superduckinvaders.game.entity.mob;


import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.ai.PathfindingAI;
import com.superduckinvaders.game.assets.Assets;


/**
 * Ah yes, the final mecha-swan boss. Have you met him yet? Very bulky, but he doesn't move much.
 */
public class BossMob extends RangedMob {
	/**
	 * Create a new BossMob.
	 *
	 * @param parent
	 *                the round parent.
	 * @param x
	 *                the x position.
	 * @param y
	 *                the y position.
	 */
	public BossMob(Round parent, float x, float y) {
		super(parent, x, y, 20, Assets.boss, 0, new PathfindingAI(parent, 200));
		RangedMob.range = Float.POSITIVE_INFINITY; // Hacky, but this way he's definitely not gonna move.
	}
}
