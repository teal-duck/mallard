
package com.superduckinvaders.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.superduckinvaders.game.assets.TextureSet;
import com.superduckinvaders.game.round.Round;
import com.superduckinvaders.game.ai.*;

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
     * set new AI for the current mob
     * @param newAI new AI type to assign
     */
    public void setAI(AI.type newAI){
        switch (newAI){
        case ZOMBIE:
            //this.ai = new ZombieAI(this.parent);
            break;
        case DUMMY:
            //this.ai = new DummyAI(this.parent);
            break;
        }
    }
    
    /**
     * speed of the mob in pixels per second
     */
    private int speed;
    
    public void setSpeed(int newSpeed){
        this.speed = newSpeed;
    }
    
    /**
     * change where the given mob moves to according to its speed and a new direction vector
     * @param dirX x component of the direction vector
     * @param dirY y component of the direction vector
     */
    public void setVelocity(int dirX, int dirY){
    	// Left/right movement.
        if (dirX > 0) {
            velocityX = speed;
        } else if (dirX < 0) {
            velocityX = -speed;
        } else {
            velocityX = 0;
        }

        // Left/right movement.
        if (dirY > 0) {
            velocityY = speed;
        } else if (dirY < 0) {
            velocityY = -speed;
        } else {
            velocityY = 0;
        }

        // If moving diagonally, move slower.
        if (velocityX != 0 && velocityY != 0) {
            velocityX /= Math.sqrt(2);
            velocityY /= Math.sqrt(2);
        }
    }

    public Mob(Round parent, int x, int y, int health, TextureSet textureSet, int speed) {
        super(parent, x, y, health);

        this.textureSet = textureSet;
        this.speed = speed;
    }
    //test
    //test2
    
    

    @Override
    public int getWidth() {
        return textureSet.getTexture(TextureSet.FACING_FRONT, 0).getRegionWidth();
    }

    @Override
    public int getHeight() {
        return textureSet.getTexture(TextureSet.FACING_FRONT, 0).getRegionHeight();
    }

    @Override
    public void update(float delta) {
        //ai.update(this);
    	this.setVelocity(1, 1);
        super.update(delta);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(textureSet.getTexture(facing, stateTime), (int) x, (int) y);
    }
}
