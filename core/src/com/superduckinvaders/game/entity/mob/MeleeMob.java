package com.superduckinvaders.game.entity.mob;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.ai.AI;
import com.superduckinvaders.game.ai.PathfindingAI;
import com.superduckinvaders.game.assets.TextureSet;
import com.superduckinvaders.game.entity.PhysicsEntity;

public class MeleeMob extends Mob {
    public MeleeMob(Round parent, float x, float y, int health, TextureSet textureSet, int speed, AI ai) {
        super(parent, x, y, health, textureSet, speed, ai);
    }

    public MeleeMob(Round parent, float x, float y, int health, TextureSet textureSet, int speed) {
        this(parent, x, y, health, textureSet, speed, new PathfindingAI(parent, 0));
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
