package com.superduckinvaders.game.entity.item;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.entity.PhysicsEntity;
import com.superduckinvaders.game.entity.Player;

/**
 * Represents a powerup on the floor.
 */
public class Powerup extends Item {

    /**
     * The powerup that this Powerup gives to the player.
     */
    private Player.Powerup powerup;

    /**
     * How long the powerup will last for.
     */
    private float time;

    public Powerup(Round parent, float x, float y, Player.Powerup powerup, float time) {
        super(parent, x, y, Player.Powerup.getTextureForPowerup(powerup));

        this.powerup = powerup;
        this.time = time;
    }
    
    @Override
    public void onCollision(PhysicsEntity other){
        if (other instanceof Player) {
            ((Player)other).setPowerup(powerup, time);
            removed = true;
        }
    }

    @Override
    public void update(float delta) {
    }
}
