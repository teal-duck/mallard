package com.superduckinvaders.game.ai;

import com.superduckinvaders.game.entity.Mob;
import com.superduckinvaders.game.round.Round;

public abstract class AI {
    
    /**
     * pointer to the current round
     */
    protected Round round;

    public AI(Round round) {
        this.round = round;
    }
    
    /**
     * execute the AI on a per-frame basis
     * @param mob pointer to the Mob
     * @param delta time since the previous update
     */
    public abstract void update(Mob mob, float delta);

    public enum Type {
        DUMMY, ZOMBIE
    }
}
