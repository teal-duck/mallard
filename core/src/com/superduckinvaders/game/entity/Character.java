package com.superduckinvaders.game.entity;

import com.superduckinvaders.game.round.Round;

public abstract class Character extends Entity {

    /**
     * Current health of this Character.
     */
    protected int health;

    public Character(Round parent, double x, double y, int health) {
        super(parent, x, y);

        this.health = health;
    }

    /**
     * Gets the health of this Character in hearts.
     * @return the health of this Character in hearts
     */
    public int getHealth() {
        return health;
    }

    /**
     * Heals this Entity's health by the specified number of points.
     * @param health the number of health points to heal
     */
    public void heal(int health) {
        this.health += health;
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
}
