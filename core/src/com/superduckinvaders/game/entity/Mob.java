
package com.superduckinvaders.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.ai.*;
import com.superduckinvaders.game.assets.TextureSet;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.*;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Mob extends Character {

    // TODO: finish me
    /**
     * The texture set to use for this Mob.
     */
    private TextureSet textureSet;
    
    /**
     * AI class for the mob
     */
    private AI ai;
    
    /**
     * checks whether mob should be updated
     */
    private boolean active = false;
    /**
     * speed of the mob in pixels per second
     */
    private float speed;
    
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Mob(Round parent, float x, float y, int health, TextureSet textureSet, int speed, AI ai) {
        super(parent, x, y, health);

        this.textureSet = textureSet;
        this.speed = speed;
        this.ai = ai;
        
        createDynamicBody(WORLD_BITS, ALL_BITS, MOB_GROUP, false);
    }

    public Mob(Round parent, int x, int y, int health, TextureSet textureSet, int speed) {
        this(parent, x, y, health, textureSet, speed, new DummyAI(parent));
    }
    
    /**
     * Sets the AI for this Mob.
     * @param ai the new AI to use
     */
    public void setAI(AI ai) {
        this.ai = ai;
    }
    
    /**
     * Sets the speed of the mob
     * @param newSpeed the updated speed
     */
    public void setSpeed(float speed){
        this.speed = speed;
    }
    public float getSpeed(){
        return this.speed;
    }
    
    @Override
    public float getWidth() {
        return textureSet.getTexture(TextureSet.FACING_FRONT, 0).getRegionWidth();
    }

    @Override
    public float getHeight() {
        return textureSet.getTexture(TextureSet.FACING_FRONT, 0).getRegionHeight();
    }
    
    
    /**
     * change where the given mob moves to according to its speed and a new direction vector
     * @param dirX x component of the direction vector
     * @param dirY y component of the direction vector
     */

    @Override
    public void update(float delta) {
        ai.update(this, delta);

        // Chance of spawning a random powerup.
        if (isDead()) {
            float random = MathUtils.random();
            Player.Powerup powerup = null;

            if (random < 0.05) {
                powerup = Player.Powerup.SCORE_MULTIPLIER;
            } else if (random >= 0.05 && random < 0.1) {
                powerup = Player.Powerup.INVULNERABLE;
            } else if (random >= 0.1 && random < 0.15) {
                powerup = Player.Powerup.SUPER_SPEED;
            } else if (random >= 0.15 && random < 0.2) {
                powerup = Player.Powerup.RATE_OF_FIRE;
            }

            if (powerup != null) {
                parent.createPowerup(getX(), getY(), powerup, 10);
            }
        }

        super.update(delta);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(textureSet.getTexture(facing, stateTime), getX(), getY());
        
        if (ai instanceof ZombieAI){
            ZombieAI.Coordinate coord = ((ZombieAI)ai).target;
            if (coord != null){
                spriteBatch.end();
                shapeRenderer.setProjectionMatrix(new Matrix4(spriteBatch.getProjectionMatrix()));
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(1, 1, 0, 1);
                shapeRenderer.x(coord.vector(), 10);
                shapeRenderer.end();
                spriteBatch.begin();
            }            
        }
        
    }
}
