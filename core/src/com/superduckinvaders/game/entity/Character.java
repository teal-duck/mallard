package com.superduckinvaders.game.entity;

import com.superduckinvaders.game.assets.TextureSet;
import com.superduckinvaders.game.round.Round;

/**
 * Represents a character in the game.
 */
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
    protected int maximumHealth, currentHealth;

    /**
     * Initialises this Character.
     *
     * @param parent        the round this Character belongs to
     * @param x             the initial x coordinate
     * @param y             the initial y coordinate
     * @param maximumHealth the maximum (and initial) health of this Character
     */
    public Character(Round parent, double x, double y, int maximumHealth) {
        super(parent, x, y);

        this.maximumHealth = this.currentHealth = maximumHealth;
    }

    /**
     * @return the direction this Character is facing (one of the FACING_ constants in TextureSet)
     */
    public int getFacing() {
        return facing;
    }

    /**
     * Gets the current health of this Character.
     *
     * @return the current health of this Character
     */
    public int getCurrentHealth() {
        return currentHealth;
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
     * Heals this Character's current health by the specified number of points.
     *
     * @param health the number of health points to heal
     */
    public void heal(int health) {
        this.currentHealth += health;

        if (currentHealth > maximumHealth) {
            currentHealth = maximumHealth;
        }
    }

    /**
     * Damages this Character's health by the specified number of points.
     *
     * @param health the number of points to damage
     */
    public void damage(int health) {
        this.currentHealth -= health;
    }

    /**
     * @return whether this Character is dead (i.e. its health is 0)
     */
    public boolean isDead() {
        return currentHealth <= 0;
    }

    /**
     * Updates the state of this Character.
     *
     * @param delta how much time has passed since the last update
     */
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
