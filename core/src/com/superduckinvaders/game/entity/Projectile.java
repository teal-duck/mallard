package com.superduckinvaders.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.superduckinvaders.game.graphics.Assets;
import com.superduckinvaders.game.round.Round;

/**
 * Created by Oliver on 11/01/2016.
 */
public class Projectile extends Entity {

    // TODO: deal with rendering projectiles

    /**
     * The owner of this Projectile (i.e. the Entity that fired it).
     */
    private Entity owner;

    /**
     * How much damage this Projectile does to what it hits.
     */
    private int damage;

    public Projectile(Round parent, double x, double y, double targetX, double targetY, double speed, int damage, Entity owner) {
        this(parent, x, y, targetX, targetY, speed, 0, 0, damage, owner);
    }

    public Projectile(Round parent, double x, double y, double targetX, double targetY, double speed, double velocityXOffset, double velocityYOffset, int damage, Entity owner) {
        super(parent, x, y);

        // Angle between initial position and target.
        double angle = Math.atan2(targetY - y, targetX - x);

        velocityX = Math.round(Math.cos(angle) * speed);
        velocityY = Math.round(Math.sin(angle) * speed);

        // Projectile should only move faster if we're moving in the same direction.
        velocityX += (Math.signum(velocityX) == Math.signum(velocityXOffset) ? velocityXOffset : 0);
        velocityY += (Math.signum(velocityY) == Math.signum(velocityYOffset) ? velocityYOffset : 0);

        this.damage = damage;
        this.owner = owner;
    }

    @Override
    public int getWidth() {
		return Assets.projectile.getRegionWidth();
    }

    @Override
    public int getHeight() {
		return Assets.projectile.getRegionHeight();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        // Do manual collision checking in order to remove projectile.
        double deltaX = velocityX * delta;
        double deltaY = velocityY * delta;

        // Check for collisions with blocked tiles and the map boundary.
        if (collidesX(deltaX) || collidesY(deltaY) || x + deltaX < 0 || x + getWidth() + deltaX > parent.getMapWidth() || y + deltaY < 0 || y + getHeight() + deltaY > parent.getMapHeight()) {
            removed = true;
            return;
        }

        // Otherwise check with collisions with every other entity.
        for (Entity entity : parent.getEntities()) {
            // Don't damage my owner.
            if (entity == owner) {
                continue;
            }

            // If entity is character and we have hit it, damage it and then delete myself.
            if (entity instanceof Character && entity.intersects(x, y, getWidth(), getHeight())) {
                ((Character) entity).damage(damage);
                removed = true;
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(Assets.projectile, (int) x, (int) y);
    }
}
