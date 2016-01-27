package com.superduckinvaders.game.entity;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.TextureSet;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

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
     * for use when determining player movement direction
     */
    private final Vector2 reference = new Vector2(0f, -1f);
    private final Vector2 bias = new Vector2(1.5f, 1);

    /**
     * Initialises this Character.
     *
     * @param parent        the round this Character belongs to
     * @param x             the initial x coordinate
     * @param y             the initial y coordinate
     * @param maximumHealth the maximum (and initial) health of this Character
     */
    public Character(Round parent, float x, float y, int maximumHealth) {
        super(parent, x, y);
        this.maximumHealth = this.currentHealth = maximumHealth;
    }

    /**
     * Gets the direction the character is facing
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
     * Returns if the character is dead
     * @return whether this Character is dead (i.e. its health is 0)
     */
    public boolean isDead() {
        return currentHealth <= 0;
    }

    /**
     * Causes this Character to fire a projectile at the specified coordinates.
     *
     * @param x      the target x coordinate
     * @param y      the target y coordinate
     * @param speed  how fast the projectile moves
     * @param damage how much damage the projectile deals
     */
    protected void fireAt(float x, float y, int speed, int damage) {
        parent.createProjectile(getX() + getWidth() / 2, getY() + getHeight() / 2, x, y, speed, velocityX, velocityY, damage, this);
    }

    /**
     * Causes this Character to use a melee attack.
     *
     * @param range  how far the attack reaches in pixels
     * @param damage how much damage the attack deals
     */
    protected void melee(float range, int damage) {
        // Don't let mobs melee other mobs (for now).
        if (this instanceof Mob) {
            Player player = parent.getPlayer();

            if (distanceTo(player.getX(), player.getY()) <= range && directionTo(player.getX(), player.getY()) == facing) {
                player.damage(damage);
            }
        } else {
            // Attack the closest Character within the range.
            Character closest = null;

            for (Entity entity : parent.getEntities()) {
                // Disregard entity if it's me or it isn't a Character.
                if (this == entity || !(entity instanceof Character)) {
                    continue;
                }

                float x = entity.getX(), y = entity.getY();
                if (distanceTo(x, y) <= range && directionTo(x, y) == facing && (closest == null || distanceTo(x, y) < distanceTo(closest.getX(), closest.getY()))) {
                    closest = (Character) entity;
                }
            }

            // Can't attack if nothing in range.
            if (closest != null) {
                closest.damage(damage);
            }
        }
    }

    /**
     * Updates the state of this Character.
     *
     * @param delta how much time has passed since the last update
     */
    @Override
    public void update(float delta) {
        
        Vector2 velocity = getVelocity();
        
        if (!velocity.isZero()){
            float angle = velocity.scl(bias).angle(reference);
            int index = (2 + (int)Math.rint(angle/90f)) % 4;
            
            // Update Character facing.
            switch (index){
                case 0: facing = TextureSet.FACING_BACK;
                        break;
                case 1: facing = TextureSet.FACING_RIGHT;
                        break;
                case 2: facing = TextureSet.FACING_FRONT;
                        break;
                case 3: facing = TextureSet.FACING_LEFT;
                        break;
            }
            
        }

        // Update animation state time.
        if (!velocity.isZero()) {
            stateTime += delta;
        } else {
            stateTime = 0;
        }

        if (isDead()) {
            removed = true;
        }

        super.update(delta);
    }
}
