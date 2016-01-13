package com.superduckinvaders.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.superduckinvaders.game.graphics.Assets;
import com.superduckinvaders.game.graphics.TextureSet;
import com.superduckinvaders.game.round.Round;

public class Player extends Character {

    /**
     * Player's maximum health.
     */
    public static final int PLAYER_HEALTH = 100;

    /**
     * Player's standard movement speed in pixels per second.
     */
    public static final int PLAYER_SPEED = 200;

    /**
     * How much the Player's speed should be multiplied by if they have POWERUP_SUPER_SPEED;
     */
    public static final double PLAYER_SUPER_SPEED_MULTIPLIER = 1;

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
     * Player's current powerup.
     */
    private int powerup = POWERUP_NONE;

    /**
     * How much time is remaining on the Player's powerup.
     */
    private double powerupTimer = 0;

    /**
     * Player's current score.
     */
    private int points = 0;

    /**
     * Initialises this Player at the specified coordinates and with the specified initial health.
     *
     * @param parent the round this Player belongs to
     * @param x      the initial x coordinate
     * @param y      the initial y coordinate
     */
    public Player(Round parent, double x, double y) {
        super(parent, 66, 66, PLAYER_HEALTH);
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
    public double getPowerupTime() {
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
     * @param time    how long the powerup should last, in seconds
     */
    public void setPowerup(int powerup, double time) {
        this.powerup = powerup;
        this.powerupTimer = time;
    }

    @Override
    public int getWidth() {
    	return 32;
        // TODO: implement me
    }

    @Override
    public int getHeight() {
    	return 32;
        // TODO: implement me
    }

    @Override
    public void update(float delta) {
        // Decrement powerup timer.
        if (powerupTimer > 0) {
            powerupTimer -= delta;
        } else if (powerupTimer <= 0) {
            clearPowerup();
        }

        // Calculate speed at which to move the player.
        double speed = PLAYER_SPEED * (powerup == POWERUP_SUPER_SPEED ? PLAYER_SUPER_SPEED_MULTIPLIER : 1);

        // Left/right movement.
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocityX = -speed;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocityX = speed;
        } else {
        	velocityX = 0;
        }
        
        // Left/right movement.
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocityY = speed;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocityY = -speed;
        } else {
        	velocityY = 0;
        }

        // Update movement.
        super.update(delta);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(Assets.playerNormal.getTexture(TextureSet.FACING_FRONT, 0), (int) x, (int) y);
    }
}
