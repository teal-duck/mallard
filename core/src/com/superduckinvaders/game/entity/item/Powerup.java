package com.superduckinvaders.game.entity.item;

import com.superduckinvaders.game.Round;
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
    private double time;

    public Powerup(Round parent, double x, double y, Player.Powerup powerup, double time) {
        super(parent, x, y, Player.Powerup.getTextureForPowerup(powerup));

        this.powerup = powerup;
        this.time = time;
    }

    @Override
    public void update(float delta) {
        Player player = parent.getPlayer();

        if (this.intersects(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
            player.setPowerup(powerup, time);
            removed = true;
        }
    }
}
