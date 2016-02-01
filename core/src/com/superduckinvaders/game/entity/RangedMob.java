package com.superduckinvaders.game.entity;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.ai.AI;
import com.superduckinvaders.game.ai.PathfindingAI;
import com.superduckinvaders.game.assets.TextureSet;



/**
 * Convenience. can be removed if not needed
 */
public class RangedMob extends MeleeMob {
    
    public RangedMob(Round parent, float x, float y, int health, TextureSet textureSet, int speed, AI ai) {
        super(parent, x, y, health, textureSet, speed, ai);
    }
    public RangedMob(Round parent, float x, float y, int health, TextureSet textureSet, int speed) {
        super(parent, x, y, health, textureSet, speed, new PathfindingAI(parent, 200));
    }
    
    @Override
    public void update(float delta){
        super.update(delta);
        //rangedAttack(parent.getPlayer(), 1);
    }
}
