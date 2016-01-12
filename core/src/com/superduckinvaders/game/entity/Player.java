package com.superduckinvaders.game.entity;

import java.util.ArrayList;
import java.util.List;

import com.superduckinvaders.game.round.Round;

public class Player extends Character {
    // TODO: determine what powerups we have
    /**
     * No powerup.
     */
    public static final int POWERUP_NONE = 0;

    /**
     * Super speed powerup.
     */
    public static final int POWERUP_SUPER_SPEED = 1;

    /**
     * Flight powerup (enables player to go over obstacles).
     */
    public static final int POWERUP_FLIGHT = 2;

    /**
     * Invulnerability powerup.
     */
    public static final int POWERUP_INVULNERABLE = 3;

    /**
     * Player's current score.
     */
    private int points = 0;

    /**
     * Player's current inventory of items.
     */
    private List<Item> items = new ArrayList<Item>(16);

    /**
     * Player's current powerup.
     */
    private int powerup = POWERUP_NONE;

    /**
     * How much time is remaining on the Player's powerup.
     */
    private int powerupTimer = 0;

    public Player(Round round) {
		// TODO Auto-generated constructor stub
	}

	/**
     * Increases the Player's score by the specified amount.
     *
     * @param amount the amount to increase the score by
     */
    public void addScore(int amount) {
        points += amount;
    }

    /**
     * Gets the Player's current score.
     *
     * @return the current score
     */
    public int getScore() {
        return points;
    }

    /**
     * Adds the specified Item to the Player's inventory.
     *
     * @param item the item to add
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Gets whether or not the Player has the specified Item in their inventory.
     *
     * @param item the item to test for
     * @return true if the item is in the player's inventory, false if not
     */
    public boolean hasItem(Item item) {
        return items.contains(item);
    }

    /**
     * Removes the specified item from the Player's inventory. Does nothing if the Item is not in the inventory.
     *
     * @param item the item to remove
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Gets the Player's current powerup (one of the POWERUP_ constants).
     *
     * @return the current powerup
     */
    public int getPowerup() {
        return powerup;
    }

    /**
     * Gets the time remaining on the Player's powerup.
     *
     * @return the time remaining on the powerup
     */
    public int getPowerupTime() {
        return powerupTimer;
    }

    /**
     * Clears the Player's current powerup.
     */
    public void clearPowerup() {
        powerup = POWERUP_NONE;
        powerupTimer = 0;
    }

    /**
     * Sets the Player's current powerup and how long it should last. Use -1 for time for an infinite powerup.
     *
     * @param powerup the powerup to set (one of the POWERUP_ constants)
     * @param time    how long the powerup should last, in game ticks
     */
    public void setPowerup(int powerup, int time) {
        this.powerup = powerup;
        this.powerupTimer = time;
    }

    @Override
    public void update() {
        super.update();
        y += 1;
        x += 1;

        // Decrement powerup timer if powerup is not infinite (i.e. powerupTimer = -1).
        if (powerupTimer > 0) {
            powerupTimer--;
        } else if (powerupTimer == 0) {
            clearPowerup();
        }
    }

    @Override
    public void render() {
        // TODO: rendering code for Player
        super.render();
    }
}
