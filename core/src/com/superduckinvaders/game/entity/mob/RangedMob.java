package com.superduckinvaders.game.entity.mob;

import com.badlogic.gdx.math.Vector2;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.ai.AI;
import com.superduckinvaders.game.ai.PathfindingAI;
import com.superduckinvaders.game.assets.TextureSet;


/**
 * Convenience. can be removed if not needed
 */
public class RangedMob extends MeleeMob {

    public static float range = 1280 / 4f;
    
    public RangedMob(Round parent, float x, float y, int health, TextureSet textureSet, int speed, AI ai) {
        super(parent, x, y, health, textureSet, speed, ai);
    }
    public RangedMob(Round parent, float x, float y, int health, TextureSet textureSet, int speed) {
        super(parent, x, y, health, textureSet, speed, new PathfindingAI(parent, 200));
    }
    
    @Override
    public void update(float delta){
        super.update(delta);
        Vector2 playerPos = parent.getPlayer().getCentre();
        if (distanceTo(playerPos) < range && parent.rayCast(getCentre(), playerPos)) {
            rangedAttack(vectorTo(playerPos), 1);
        }
    }
}
