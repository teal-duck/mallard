
package com.superduckinvaders.game.ai;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.Pathfinder;
import com.superduckinvaders.game.Pathfinder.*;
import com.superduckinvaders.game.entity.Mob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;



/**
 * AI that follows and attacks the player within a certain range.
 */
public class PathfindingAI extends AI {
    
    public Vector2 nodePos;
    public static float MOVEMENT_TIMEOUT = 0.2f;
    public float movementTimer = 0f;
    
    /**
     * Initialises this PathfindingAI.
     *
     * @param round       the round the Mob this AI controls is a part of
     * @param targetRange how close to the player this PathfindingAI tries to get
     */
    public PathfindingAI(Round round, int targetRange) {
        super(round);
        
    }
    
    public void doMovement(Mob mob, Vector2 target){
        Vector2 dst = new Vector2(target);
        Vector2 velocity = dst.sub(mob.getCentre())
                               .nor().scl(mob.getSpeed());
        mob.setVelocityClamped(velocity);
    }
    public void updateNode(Mob mob){
        SearchNode node = round.pathfinder.routeFrom(mob.getCentre());
        
        if (node != null){
            Vector2 mobPos = mob.getCentre();
            Vector2 mobSize = mob.getSize();
            nodePos = node.coord.vector();
            while (node.predecessor != null){
                Vector2 predNodePos = node.predecessor.coord.vector();
                if (round.pathIsClear(mobPos, mobSize, predNodePos)){
                    node = node.predecessor;
                    nodePos = predNodePos;
                }
                else {
                    break;
                }
            }
        }
    }


    /**
     * Updates this PathfindingAI.
     *
     * @param mob   pointer to the Mob using this AI
     * @param delta time since the previous update
     */
    @Override
    public void update(Mob mob, float delta) {
        float distanceToPlayer = mob.distanceTo(round.getPlayer().getCentre());
        float distanceToTargetTile = (nodePos != null) ? mob.getCentre().sub(nodePos).len() : 0f;
        
        movementTimer += delta;
        if ((movementTimer >= MOVEMENT_TIMEOUT || distanceToTargetTile < 2) && (int) distanceToPlayer < 1280 / 4) {
            movementTimer = 0;
            updateNode(mob);
        }
        
        if (nodePos != null){
            doMovement(mob, nodePos);
        }
        
        
    }
}
