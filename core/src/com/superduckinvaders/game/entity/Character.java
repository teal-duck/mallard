package com.superduckinvaders.game.entity;

public abstract class Character extends Entity {

    /**
     * Current health of this Character.
     */
    private int health = 10;

    /**
     * Current speed of this Character in pixels per second.
     */
    private int speed = 0;

    /**
     * The coordinates of the destination tile of this Character.
     */
    private int destX = 0, destY = 0;

    /**
     * Whether or not this Character collides with other objects.
     */
    private boolean collides = true;

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
     * Gets the speed of this Character in pixels per tick.
     * @return the speed of this Character in pixels per tick
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of this Character in pixels per tick.
     * @param speed the new speed of this Character in pixels per tick
     */
    public void setSpeed(int speed) {
        if(this.speed >= 0) {
            this.speed = speed;
        }
    }

    /**
     * Gets the X coordinate of the destination tile of this Character.
     * @return the X coordinate of the destination tile
     */
    public int getDestX() {
        return destX;
    }

    /**
     * Sets the X coordinate of the destination tile of this Character.
     * @param destX the X coordinate of the destination tile
     */
    public void setDestX(int destX) {
        this.destX = destX;
    }

    /**
     * Gets the Y coordinate of the destination tile of this Character.
     * @return the Y coordinate of the destination tile
     */
    public int getDestY() {
        return destY;
    }

    /**
     * Sets the Y coordinate of the destination tile of this Character.
     * @param destY the Y coordinate of the destination tile
     */
    public void setDestY(int destY) {
        this.destY = destY;
    }

    /**
     * Gets whether or not this Character collides with other entities.
     * @return whether or not this Character collides with other entities
     */
    public boolean doesCollide() {
        return collides;
    }

    /**
     * Sets whether or not this Character collides with other entities.
     * @param collides whether or not this Character collides with other entities
     */
    public void setCollides(boolean collides) {
        this.collides = collides;
    }

    public int getDirection() {
        return (int) Math.atan((double) (destX - x) / (destY - y));
        //TODO: finish implementing this.
    }

    public void move() {
        //TODO: finish implementing this.
    }
}
