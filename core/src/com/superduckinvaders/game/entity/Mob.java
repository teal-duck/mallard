
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
     * checks whether mob should be updated
     */
    private boolean active = false;
    
    /**
     * set new AI for the current mob
     * @param newAI new AI type to assign
     */
    public void setAI(AI.type newAI, int[] args){
        switch (newAI){
        case ZOMBIE:
            this.ai = new ZombieAI(this.parent, args);
            break;
        case DUMMY:
            this.ai = new DummyAI(this.parent, args);
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
    	if(dirX == 0 && dirY==0){
    		velocityX=0;
    		velocityY=0;
    		return;
    	}
    	double magnitude = Math.sqrt(dirX*dirX + dirY*dirY);
    	velocityX = (dirX*speed)/magnitude;
    	velocityY = (dirY*speed)/magnitude;
    	
    }


    public Mob(Round parent, int x, int y, int health, TextureSet textureSet, int speed, AI.type aitype, int[] args) {
        super(parent, x, y, health);

        this.textureSet = textureSet;
        this.speed = speed;
        
        this.setAI(aitype, args);
    }
    
    public Mob(Round parent, int x, int y, int health, TextureSet textureSet, int speed){
    	this(parent, x, y, health, textureSet, speed, AI.type.DUMMY, new int[] {});
    }
    

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
    	active = ai.active(this);
    	if (active){
            ai.update(this, delta);
            super.update(delta);
    	}
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(textureSet.getTexture(facing, stateTime), (int) x, (int) y);
    }
}
