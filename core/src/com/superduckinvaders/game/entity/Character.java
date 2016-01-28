package com.superduckinvaders.game.entity;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.TextureSet;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

import java.lang.Math;

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
    
    
    public static float ATTACK_COOLDOWN=2f;
    public float attackTimer;
    
    public float projectileSpeed = 20f;
    
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
     
    // protected void fireAt(Vector2 target, float speed, int damage) {
    protected void fireAt(Vector2 target, int damage) {
        Vector2 pos = getCentre();
        Vector2 velocity = new Vector2(target).sub(pos).setLength(projectileSpeed).add(getPhysicsVelocity());
        velocity.setLength(Math.max(projectileSpeed, velocity.len()));
        parent.createProjectile(pos,
                velocity,
                damage, this);
    }

    /**
     * Causes this Character to use a melee attack.
     *
     * @param other --
     * @param damage how much damage the attack deals
     */
    protected void meleeAttack(Character other, int damage) {
        if (attackTimer > ATTACK_COOLDOWN){
            other.damage(damage);
            attackTimer = 0f;
        }
    }
    
    protected void rangedAttack(Character other, int damage) {
        if (attackTimer > ATTACK_COOLDOWN && !parent.rayCast(getCentre(), other.getCentre())){
                attackTimer = 0f;
                fireAt(other.getCentre(), damage);
        }
    }

    /**
     * Updates the state of this Character.
     *
     * @param delta how much time has passed since the last update
     */
    @Override
    public void update(float delta) {
        attackTimer += delta;
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
