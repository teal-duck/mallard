package com.superduckinvaders.game.entity.item;

import com.badlogic.gdx.physics.box2d.Contact;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.entity.PhysicsEntity;
import com.superduckinvaders.game.entity.Player;

/**
 * Represents a powerup on the floor.
 */
public class PickupItem extends Item {

    /**
     * The powerup that this Powerup gives to the player.
     */
    private Player.Pickup pickup;

    /**
     * How long the powerup will last for.
     */
    private float time;

    public PickupItem(Round parent, float x, float y, Player.Pickup pickup, float time) {
        super(parent, x, y, pickup.getTexture());

        this.pickup = pickup;
        this.time = time;
    }
    
    @Override
    public void beginSensorContact(PhysicsEntity other, Contact contact){
        if (other instanceof Player) {
            ((Player)other).givePickup(pickup, time);
            removed = true;
        }
    }

    @Override
    public void update(float delta) {
    }
}
