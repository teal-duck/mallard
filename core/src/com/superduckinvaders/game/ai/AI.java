package com.superduckinvaders.game.ai;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.entity.Mob;
import com.superduckinvaders.game.entity.Entity;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

/**
 * Defines movement and attacking behaviour for Mobs.
 */
public abstract class AI {
    
    /**
     * The round the Mob this AI controls is a part of.
     */
    protected Round round;
    protected World world;

    /**
     * Initialises this AI.
     *
     * @param round the round the Mob this AI controls is a part of
     */
    public AI(Round round) {
        this.round = round;
        this.world = round.world;
    }
    
    /**
     * Updates this AI.
     * @param mob pointer to the Mob using this AI
     * @param delta time since the previous update
     */
    public abstract void update(Mob mob, float delta);
}
