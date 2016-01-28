package com.superduckinvaders.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.Assets;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents a projectile.
 */
public class Projectile extends Entity {

    /**
     * The owner of this Projectile (i.e. the Entity that fired it).
     */
    private Entity owner;

    /**
     * How much damage this Projectile does to what it hits.
     */
    private int damage;

    /**
     * Initialises this Projectile.
     *
     * @param parent  the round this Projectile belongs to
     * @param x       the initial x coordinate
     * @param y       the initial y coordinate
     * @param targetX the target x coordinate
     * @param targetY the target y coordinate
     * @param speed   how fast the projectile moves
     * @param damage  how much damage the projectile deals
     * @param owner   the owner of the projectile (i.e. the one who fired it)
     */
    public Projectile(Round parent, Vector2 pos, Vector2 velocity, int damage, Entity owner) {
        super(parent, pos);
        
        this.width = Assets.projectile.getRegionWidth();
        this.height = Assets.projectile.getRegionHeight();
        
        this.damage = damage;
        this.owner = owner;
        
        createDynamicBody(WORLD_BITS, (short) (WORLD_BITS | MOB_BITS), NO_GROUP, false);
        body.setBullet(true);
        setVelocity(velocity);
    }

    /**
     * Updates the state of this Projectile.
     *
     * @param delta how much time has passed since the last update
     */
    @Override
    public void update(float delta) {
        super.update(delta);

        // Check for collisions with blocked tiles and the map boundary.
        // if (collidesX(deltaX) || collidesY(deltaY) || x + deltaX < 0 || x + getWidth() + deltaX > parent.getMapWidth() || y + deltaY < 0 || y + getHeight() + deltaY > parent.getMapHeight()) {
            // // Create explosion particle effect.
            // parent.createParticle(x, y, 0.6f, Assets.explosionAnimation);

            // removed = true;
            // return;
        // }

        // Otherwise check with collisions with every other entity.
        for (Entity entity : parent.getEntities()) {
            // Don't damage my owner.
            if (entity == owner) {
                continue;
            }
            /*
            // If entity is character and we have hit it, damage it and then delete myself.
            if (entity instanceof Character && entity.intersects(x, y, getWidth(), getHeight())) {
                ((Character) entity).damage(damage);
                removed = true;
            }
            */
        }
    }

    /**
     * Renders this Projectile.
     *
     * @param spriteBatch the sprite batch on which to render
     */
    @Override
    public void render(SpriteBatch spriteBatch) {
        Vector2 pos = getPosition();
        spriteBatch.draw(Assets.projectile, pos.x, pos.y);
    }
}
