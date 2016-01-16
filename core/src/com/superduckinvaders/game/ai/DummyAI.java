package com.superduckinvaders.game.ai;

import com.superduckinvaders.game.entity.Mob;
import com.superduckinvaders.game.round.Round;

public class DummyAI extends AI {

    public DummyAI(Round currentRound, int[]args){
        super(currentRound, args);
    }
    
    @Override
    public void update(Mob mob) {
        return;
    }
    
    @Override
    public boolean StillActive(Mob mob){
        return true;
    }

}
