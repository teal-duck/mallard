package com.superduckinvaders.game.entity;

public abstract class Character extends Entity {

    /**
     * Current health of this Character.
     */
    private int health = 10;

    /**
     * Movement speed of this Character in pixels per second.
     */
    private int speed = 0;

    /**
     * The coordinates of the destination of this Character.
     */
    private int destX = 0, destY = 0;

    /**
     * Whether or not this Character collides with other objects.
     */
    private boolean collides = true;

    /**
     * Begins moving this Character toward the specified destination coordinates.
     * @param destX the X coordinate of the destination
     * @param destY the Y coordinate of the destination
     */
    public void move(int destX, int destY) {
        this.destX = destX;
        this.destY = destY;
    }

    /**
     * Gets whether or not this Character is moving.
     * A Character is considered to be moving when its current coordinates do not match those of its destination and its movement speed is nonzero.
     * @return whether or not this Character is moving
     */
    public boolean isMoving() {
        return (x != destX || y != destY) && speed > 0;
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
        if(health >= 0) {
            this.health = health;
        }
    }

    /**
     * Gets the movement speed of this Character in pixels per tick.
     * @return the movement speed of this Character in pixels per tick
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets the movement speed of this Character in pixels per tick.
     * @param speed the new movement speed of this Character in pixels per tick
     */
    public void setSpeed(int speed) {
        // Can't have a negative speed
        if(speed >= 0) {
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
     * Gets the Y coordinate of the destination tile of this Character.
     * @return the Y coordinate of the destination tile
     */
    public int getDestY() {
        return destY;
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

    /**
     * Gets the current facing of this Character in radians.
     * Facing is calculated based on the Character's current position and that of its destination.
     * @return the current facing of this Character in radians
     */
    public double getDirection() {
        return Math.atan2((double) destY - y, (double) destX - x);
    }

    /**
     * Updates the position of this Character, moving it nearer to its target coordinates.
     * Ensure that super.update() is called in the update method of each subclass.
     */
    @Override
    public void update() {
        // TODO: account for collision detection here?

        // Update X position
        if (destX < x) {
            // Destination is to the left
            // Conditional is to ensure that we do not 'overshoot' the target coordinate
            x -= (x - speed >= destX ? speed : x - destX);
        } else if (destX > x) {
            // Destination is to the right
            x += (x + speed <= destX ? speed : destX - x);
        }

        // Update Y position
        if(destY < y) {
            // Destination is below
            y -= (y - speed >= destY ? speed : y - destY);
        } else if (destY > y) {
            // Destination is above
            y += (y + speed <= destY ? speed : destY - y);
        }
    }
}
