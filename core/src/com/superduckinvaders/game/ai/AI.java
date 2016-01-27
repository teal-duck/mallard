package com.superduckinvaders.game.ai;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.entity.Mob;
import com.superduckinvaders.game.entity.Entity;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

/**
 * Defines movement and attacking behaviour for Mobs.
 */
public abstract class AI {
    
    /**
     * The round the Mob this AI controls is a part of.
     */
    protected Round round;
    protected World world;

    /**
     * Initialises this AI.
     *
     * @param round the round the Mob this AI controls is a part of
     */
    public AI(Round round) {
        this.round = round;
        this.world = round.world;
    }
    
    /**
     * Tests if a point resides inside a body
     * @param x x
     * @param y y 
     */
    public boolean collidePoint(float x, float y) {
        return collidePoint(new Vector2(x, y));
    }
    public boolean collidePoint(Vector2 p) {
        p.scl(Entity.METRES_PER_PIXEL);
        QueryCB q = new QueryCB(p);
        world.QueryAABB(q, p.x, p.y, p.x+1, p.y+1);
        return q.result;
    }
    
    

    /**
     * Updates this AI.
     * @param mob pointer to the Mob using this AI
     * @param delta time since the previous update
     */
    public abstract void update(Mob mob, float delta);
    
    
    
    public static class QueryCB implements QueryCallback {
        public boolean result = false;
        public Vector2 p;
        
        public QueryCB(Vector2 p){
            this.p = p;
        }
        
        public boolean reportFixture(Fixture fixture){
            // if ((fixture.getFilterData().categoryBits | Entity.WORLD_BITS) != 0 && 
            if (fixture.testPoint(p)) {
                 // fixture.testPoint(p)) {
                result = true; // we collided
                return false; // ends the query
            }
            return true; // keep searching
        }
    }
    
}
