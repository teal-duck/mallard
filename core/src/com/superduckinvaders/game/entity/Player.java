package com.superduckinvaders.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.superduckinvaders.game.graphics.Assets;
import com.superduckinvaders.game.graphics.TextureSet;
import com.superduckinvaders.game.round.Round;

public class Player extends Character {

    /**
     * Player's maximum health.
     */
    public static final int PLAYER_HEALTH = 6;
    /**
     * Player's standard movement speed in pixels per second.
     */
    public static final int PLAYER_SPEED = 200;
    /**
     * Player's standard rate of fire
     */
    public static final int PLAYER_RATE_OF_FIRE = 1;
    /**
     * How much the Player's speed should be multiplied by if they have the super speed powerup.
     */
    public static final double PLAYER_SUPER_SPEED_MULTIPLIER = 3;
    /**
     * How much the Player's speed should me multiplied by if they are flying.
     */
    public static final double PLAYER_FLIGHT_SPEED_MULTIPLIER = 2;
    /**
     * How much the Player's rate of fire should be multiplied by if they have the rate of fire powerup.
     */
    public static final double PLAYER_ROF_MULTIPLIER = 5;
    /**
     * How long the Player can fly for, in seconds.
     */
    public static final double PLAYER_FLIGHT_TIME = 1;
    /**
     * How long after flying before the Player can fly again, in seconds.
     */
    public static final double PLAYER_FLIGHT_COOLDOWN = 5;
    /**
     * Player's current score.
     */
    private int points = 0;
    /**
     * Player's current powerup.
     */
    private Powerup powerup = Powerup.NONE;
    /**
     * Player's upgrade.
     */
    private Upgrade upgrade = Upgrade.NONE;
    /**
     * How much time is remaining on the Player's powerup.
     */
    private double powerupTimer = 0;
    /**
     * Shows if a player is flying. If < 0, player is flying for -flyingTimer seconds. If < PLAYER_FLIGHT_COOLDOWN, flying is on cooldown.
     */
    private double flyingTimer = 5;
    /**
     * How long it has been since the Player last fired the gun.
     */
    private double fireTimer = 0;

    /**
     * Initialises this Player at the specified coordinates and with the specified initial health.
     *
     * @param parent the round this Player belongs to
     * @param x      the initial x coordinate
     * @param y      the initial y coordinate
     */
    public Player(Round parent, double x, double y) {
        super(parent, x, y, PLAYER_HEALTH);
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
     * Gets the Player's current powerup (in the Powerup enum).
     *
     * @return the current powerup
     */
    public Powerup getPowerup() {
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
        powerup = Powerup.NONE;
        powerupTimer = 0;
    }

    /**
     * Sets the Player's current powerup and how long it should last.
     *
     * @param powerup the powerup to set (in the Powerup enum)
     * @param time    how long the powerup should last, in seconds
     */
    public void setPowerup(Powerup powerup, double time) {
        this.powerup = powerup;
        this.powerupTimer = time;
    }

    @Override
    public int getWidth() {
        return Assets.playerNormal.getWidth();
    }

    @Override
    public int getHeight() {
        return Assets.playerNormal.getHeight();
    }

    @Override
    public void update(float delta) {
        // Decrement powerup timer.
        if (powerupTimer > 0) {
            powerupTimer -= delta;
        } else if (powerupTimer <= 0) {
            clearPowerup();
        }

        // Update flying timer.
        if (flyingTimer < PLAYER_FLIGHT_COOLDOWN) {
            flyingTimer += delta;
        }

        // Update firing timer.
        fireTimer += delta;

        // Left mouse to fire.
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (fireTimer >= PLAYER_RATE_OF_FIRE * (1 / PLAYER_ROF_MULTIPLIER)) {
                fireTimer = 0;
                Vector3 target = parent.unproject(Gdx.input.getX(), Gdx.input.getY());
                parent.createProjectile(x + getWidth() / 2, y + getHeight() / 2, target.x, target.y, 300, velocityX, velocityY, 100, this);
            }
        }

        // Press space to start flying, but only if flying isn't cooling down and we're moving.
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && flyingTimer >= PLAYER_FLIGHT_COOLDOWN && (velocityX != 0 || velocityY != 0)) {
            flyingTimer = -PLAYER_FLIGHT_TIME;
            velocityX *= PLAYER_FLIGHT_SPEED_MULTIPLIER;
            velocityY *= PLAYER_FLIGHT_SPEED_MULTIPLIER;
        }

        // Only allow movement via keys if not flying.
        if (!isFlying()) {
            // Calculate speed at which to move the player.
            double speed = PLAYER_SPEED * (powerup == Powerup.SUPER_SPEED ? PLAYER_SUPER_SPEED_MULTIPLIER : 1);

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

            // If moving diagonally, move slower.
            // This must not be done while flying otherwise the player will slow down and stop.
            if (velocityX != 0 && velocityY != 0) {
                velocityX /= Math.sqrt(2);
                velocityY /= Math.sqrt(2);
            }
        }

        // Update movement.
        super.update(delta);
    }

    public boolean isFlying() {
        return flyingTimer <= 0;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        // Use the right texture set.
        TextureSet textureSet = isFlying() ? Assets.playerFlying : Assets.playerNormal;

        spriteBatch.draw(textureSet.getTexture(facing, stateTime), (int) x, (int) y);
    }

    @Override
    public void damage(int health) {
        // Only apply damage if we don't have the invulnerability powerup.
        if (powerup != Powerup.INVULNERABLE) {
            super.damage(health);
        }
    }

    /**
     * Available powerups (only lst for
     */
    public enum Powerup {
        NONE,
        SUPER_SPEED,
        RATE_OF_FIRE,
        INVULNERABLE
    }

    /**
     * Available upgrades (upgrades are persistent).
     */
    public enum Upgrade {
        NONE,
        GUN
    }
}
