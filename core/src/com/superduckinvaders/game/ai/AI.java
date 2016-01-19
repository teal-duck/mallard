package com.superduckinvaders.game.ai;

import com.superduckinvaders.game.entity.Mob;
import com.superduckinvaders.game.round.Round;

/**
 * Defines movement and attacking behaviour for Mobs.
 */
public abstract class AI {
    
    /**
     * pointer to the current round
     */
    protected Round round;

    /**
     * Initialises this AI.
     *
     * @param round the round the Mob this AI controls is a part of
     */
    public AI(Round round) {
        this.round = round;
    }

    /**
     * Updates this AI.
     * @param mob pointer to the Mob using this AI
     * @param delta time since the previous update
     */
    public abstract void update(Mob mob, float delta);
}
