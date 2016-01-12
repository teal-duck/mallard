package com.superduckinvaders.game.entity;

import com.superduckinvaders.game.graphics.TextureSet;
import com.superduckinvaders.game.round.Round;

public abstract class Character extends Entity {

    /**
     * The direction the Character is facing.
     */
    protected int facing = TextureSet.FACING_FRONT;

    /**
     * The state time for the animation. Set to 0 for not moving.
     */
    protected float stateTime = 0;

    /**
     * Current health and the maximum health of this Character.
     */
    protected int health, maximumHealth;

    public Character(Round parent, double x, double y, int maximumHealth) {
        super(parent, x, y);

        this.maximumHealth = this.health = maximumHealth;
    }

    /**
     * @return the direction this Character is facing (one of the FACING_ constants in TextureSet)
     */
    public int getFacing() {
        return facing;
    }

    /**
     * Gets the health of this Character.
     * @return the health of this Character
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets the maximum health of this Character.
     *
     * @return the maximum health of this Character
     */
    public int getMaximumHealth() {
        return maximumHealth;
    }

    /**
     * Heals this Entity's health by the specified number of points.
     * @param health the number of health points to heal
     */
    public void heal(int health) {
        this.health += health;

        if (this.health > this.maximumHealth) {
            this.health = this.maximumHealth;
        }
    }

    /**
     * Damages this Entity's health by the specified number of points.
     *
     * @param health the number of points to damage
     */
    public void damage(int health) {
        this.health -= health;

        // Makes no sense for health to drop below zero, i.e. dead.
        if (this.health < 0) {
            this.health = 0;
        }
    }

    /**
     * @return whether this Entity is dead (i.e. its health is 0)
     */
    public boolean isDead() {
        return health == 0;
    }

    @Override
    public void update(float delta) {
        // Update Character facing.
        if (velocityX < 0) {
            facing = TextureSet.FACING_LEFT;
        } else if (velocityX > 0) {
            facing = TextureSet.FACING_RIGHT;
        }

        if (velocityY < 0) {
            facing = TextureSet.FACING_FRONT;
        } else if (velocityY > 0) {
            facing = TextureSet.FACING_BACK;
        }

        // Update animation state time.
        if (velocityX != 0 || velocityY != 0) {
            stateTime += delta;
        } else {
            stateTime = 0;
        }

        super.update(delta);
    }
}
