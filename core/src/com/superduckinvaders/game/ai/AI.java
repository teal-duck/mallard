package com.superduckinvaders.game.ai;

import com.superduckinvaders.game.round.Round;
import com.superduckinvaders.game.entity.Mob;

public abstract class AI {
    
    public static enum type {
        DUMMY, ZOMBIE
    }
    /**
     * pointer to the current round
     */
    protected Round roundPointer;
    
    public AI(Round currentRound, int args[]){
        roundPointer = currentRound;
    }
    
    /**
     * checks whether the character is still active
     * left to AI as some AI may still be active off screen 
     * (i.e. zombie once active should never deactivate)
     * 
     * @param mob - pointer to the Mob
     * @return whether character is still active 
     */
    public abstract boolean StillActive(Mob mob);
    
    /**
     * execute the AI on a per-frame basis
     * @param mob pointer to the Mob
     */
    public abstract void update(Mob mob);
}
