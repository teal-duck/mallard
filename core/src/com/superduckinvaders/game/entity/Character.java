package com.superduckinvaders.game.entity;

public class Character {

    /**
     * Character is facing tile above.
     */
    public static final int FACING_NORTH = 0;

    /**
     * Character is facing tile to the right.
     */
    public static final int FACING_EAST = 1;

    /**
     * Character is facing tile below.
     */
    public static final int FACING_SOUTH = 2;

    /**
     * Character is facing tile to the left.
     */
    public static final int FACING_WEST = 3;

    /**
     * Current health of this character.
     */
    private int health = 10;

    /**
     * Current speed of this character.
     */
    private int speed = 0;

    /**
     * Current facing of this character.
     */
    private int direction = 0;

    /**
     * Whether or not this Character collides with other objects.
     */
    private boolean collides = true;Initial implementation.

}
