package com.superduckinvaders.game.entity.mob;

import com.badlogic.gdx.math.Vector2;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.ai.AI;
import com.superduckinvaders.game.ai.PathfindingAI;
import com.superduckinvaders.game.assets.TextureSet;
import com.superduckinvaders.game.entity.PhysicsEntity;
import com.superduckinvaders.game.entity.Player;
import com.superduckinvaders.game.entity.mob.Mob;

public class MeleeMob extends Mob {
    public MeleeMob(Round parent, float x, float y, int health, TextureSet textureSet, int speed, AI ai) {
        super(parent, x, y, health, textureSet, speed, ai);
    }

    public MeleeMob(Round parent, float x, float y, int health, TextureSet textureSet, int speed) {
        super(parent, x, y, health, textureSet, speed, new PathfindingAI(parent, 0));
    }
    
    @Override
    public void onCollision(PhysicsEntity other) {
        if (other instanceof Player) {
            Player player = (Player) other;
            meleeAttack(player, 1);
            
            Vector2 knockback = player.getPosition().sub(getPosition()).setLength(30f);
            player.setVelocity(knockback);
            setVelocity(knockback.scl(-1f));
        }
    }
}
