package com.superduckinvaders.game.entity;

public abstract class Character extends Entity {

    /**
     * Character is facing tile above.
     */
    public static final int DIRECTION_NORTH = 0;

    /**
     * Character is facing tile to the right.
     */
    public static final int DIRECTION_EAST = 1;

    /**
     * Character is facing tile below.
     */
    public static final int DIRECTION_SOUTH = 2;

    /**
     * Character is facing tile to the left.
     */
    public static final int DIRECTION_WEST = 3;

    /**
     * Current health of this Character.
     */
    private int health = 10;

    /**
     * Current speed of this Character.
     */
    private int speed = 0;

    /**
     * Current facing of this Character.
     */
    private int direction = 0;

    /**
     * Whether or not this Character collides with other objects.
     */
    private boolean collides = true;

    public void move() {
        //TODO implement me
    }

    /**
     * Gets the health of this Character in hearts.
     * @return the health of this Character in hearts
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of this Character in hearts.
     * @param health the new health of this Character in hearts
     */
    public void setHealth(int health) {
        if(this.health >= 0) {
            this.health = health;
        }
    }

    /**
     * Gets the speed of this Character.
     * @return the speed of this Character
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of this Character.
     * @param speed the new speed of this Character.
     */
    public void setSpeed(int speed) {
        if(this.speed >= 0) {
            this.speed = speed;
        }
    }

    /**
     * Gets the direction that this Character is facing.
     * @return the direction that this Character is facing (one of the DIRECTION_ constants in this class)
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Sets the direction that this Character is facing.
     * @param direction the direction that this Character is facing (one of the DIRECTION_ constants in this class)
     */
    public void setDirection(int direction) {
        if(direction >= DIRECTION_NORTH && direction <= DIRECTION_WEST) {
            this.direction = direction;
        }
    }

    /**
     * Gets whether or not this Character collides with other entities.
     * @return whether or not this Character collides with other entities
     */
    public boolean doesCollide() {
        return collides;
    }
}
