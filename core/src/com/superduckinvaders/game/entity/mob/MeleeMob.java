package com.superduckinvaders.game.entity.mob;


import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.ai.AI;
import com.superduckinvaders.game.ai.PathfindingAI;
import com.superduckinvaders.game.assets.TextureSet;
import com.superduckinvaders.game.entity.PhysicsEntity;


/**
 * A mob that prefers close range combat (we don't live in the USA, so guns aren't exactly commonplace here!)
 */
public class MeleeMob extends Mob {
	/**
	 * Create a new MeleeMob.
	 *
	 * @param parent
	 *                the round parent.
	 * @param x
	 *                the initial x position.
	 * @param y
	 *                the initial y position.
	 * @param health
	 *                the starting health.
	 * @param textureSet
	 *                a TextureSet to use for displaying.
	 * @param speed
	 *                the speed to approach the player.
	 * @param ai
	 *                the AI type to use.
	 * @param demented
	 *                true if the mob is created in a poor mental state.
	 */
	public MeleeMob(Round parent, float x, float y, int health, TextureSet textureSet, int speed, AI ai,
			boolean demented) {
		super(parent, x, y, health, textureSet, speed, ai, demented);
	}


	/**
	 * Create a new MeleeMob. Creates a default AI.
	 *
	 * @param parent
	 *                the round parent.
	 * @param x
	 *                the initial x position.
	 * @param y
	 *                the initial y position.
	 * @param health
	 *                the starting health.
	 * @param textureSet
	 *                a TextureSet to use for displaying.
	 * @param speed
	 *                the speed to approach the player.
	 * @param demented
	 *                true if the mob is created in a poor mental state.
	 */
	public MeleeMob(Round parent, float x, float y, int health, TextureSet textureSet, int speed,
			boolean demented) {
		this(parent, x, y, health, textureSet, speed, new PathfindingAI(parent, 0), demented);
	}


	/**
	 * Create a new MeleeMob. Defaults demented to false.
	 *
	 * @param parent
	 *                the round parent.
	 * @param x
	 *                the initial x position.
	 * @param y
	 *                the initial y position.
	 * @param health
	 *                the starting health.
	 * @param textureSet
	 *                a TextureSet to use for displaying.
	 * @param speed
	 *                the speed to approach the player.
	 * @param ai
	 *                the AI type to use.
	 * @param isDemented
	 *                true if the mob is created in a poor mental state.
	 */
	public MeleeMob(Round parent, float x, float y, int health, TextureSet textureSet, int speed, AI ai) {
		this(parent, x, y, health, textureSet, speed, ai, false);
	}


	/**
	 * Create a new MeleeMob. Creates a default AI, and defaults demented to false.
	 *
	 * @param parent
	 *                the round parent.
	 * @param x
	 *                the initial x position.
	 * @param y
	 *                the initial y position.
	 * @param health
	 *                the starting health.
	 * @param textureSet
	 *                a TextureSet to use for displaying.
	 * @param speed
	 *                the speed to approach the player.
	 * @param ai
	 *                the AI type to use.
	 * @param isDemented
	 *                true if the mob is created in a poor mental state.
	 */
	public MeleeMob(Round parent, float x, float y, int health, TextureSet textureSet, int speed) {
		this(parent, x, y, health, textureSet, speed, false);
	}


	@Override
	public void update(float delta) {
		super.update(delta);
		if (!enemiesInRange.isEmpty()) {
			for (PhysicsEntity entity : enemiesInRange) {
				meleeAttack(vectorTo(entity.getCentre()), 1);
			}
		}
	}
}
