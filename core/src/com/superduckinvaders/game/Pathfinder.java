
package com.superduckinvaders.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.entity.Mob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;



/**
 * AI that follows and attacks the player within a certain range.
 */
public class Pathfinder {
    /**
     * How many iterations to use in the pathfinding algorithm.
     */
    public final static int PATHFINDING_ITERATION_LIMIT = 20;

    /**
     * Width of one tile in the map.
     */
    private int tileWidth;
    /**
     * Height of one tile in the map.
     */
    private int tileHeight;
    /**
     * Player's last position.
     */
    private Coordinate targetCoord;
    private Vector2 targetPos;
    
    private Round round;
    
    private static HashMap<Coordinate, SearchNode> pathMap;

    /**
     * Initialises this PathfindingAI.
     *
     * @param round       the round the Mob this AI controls is a part of
     * @param targetRange how close to the player this PathfindingAI tries to get
     */
    public Pathfinder(Round round) {
        this.round = round;
        this.tileWidth = round.getTileWidth();
        this.tileHeight = round.getTileHeight();
        
    }
    
    public void update() {
        Vector2 playerPos = round.getPlayer().getCentre();
        if (roundToTile(playerPos) != targetCoord){
            targetCoord = roundToTile(playerPos);
            targetPos = targetCoord.vector();
            buildMap();
        }
    }
    
    public SearchNode routeFrom(Vector2 position){
        Coordinate coord = roundToTile(position);
        if (pathMap.containsKey(coord)){
            SearchNode node = pathMap.get(coord);            
            if (node.predecessor != null){
                node = node.predecessor;
            }
            return node;
        }
        else {
            return null;
        }
    }
    
    private void buildMap(){
        PriorityQueue<SearchNode> fringe = new PriorityQueue<SearchNode>();
        pathMap = new HashMap<Coordinate, SearchNode>();
        
        Coordinate startCoord = targetCoord;
        SearchNode startNode = new SearchNode(null, startCoord, 0);
        
        fringe.add(startNode);
        pathMap.put(startCoord, startNode);
        
        while (!fringe.isEmpty()) {
            
            SearchNode currentState = fringe.poll();
            Coordinate currentCoord = currentState.coord;
            
            if (currentState.iteration >= PATHFINDING_ITERATION_LIMIT) {
                continue;
            }
            
            //work out N, E, S, W permutations
            Coordinate[] perm = {
                new Coordinate(currentCoord.x,             currentCoord.y + tileHeight),
                new Coordinate(currentCoord.x + tileWidth, currentCoord.y             ),
                new Coordinate(currentCoord.x,             currentCoord.y - tileHeight),
                new Coordinate(currentCoord.x - tileWidth, currentCoord.y             )
            };

            for (Coordinate currentPerm : perm) {
                if (!pathMap.containsKey(currentPerm) && !round.collidePoint(currentPerm.vector())) {
                    SearchNode nextNode = new SearchNode(currentState, currentPerm, currentState.iteration + 1);
                    fringe.add(nextNode);
                    pathMap.put(currentPerm, nextNode);
                }
            }
        }
    }
    
    public float heuristic(Coordinate coord){
        return coord.vector().sub(targetPos).len();
    }
    
    public Coordinate roundToTile(Vector2 pos){
        return roundToTile(pos.x, pos.y);
        
    }
    public Coordinate roundToTile(float x, float y){
        int nx = (int)(((int)(x/tileWidth)+0.5f) * tileWidth);
        int ny = (int)(((int)(y/tileHeight)+0.5f) * tileHeight);
        return new Coordinate(nx, ny);
    }

    /**
     * Represents a pair of coordinates.
     */
    public class Coordinate {
        /**
         * The X coordinate.
         */
        public int x;
        /**
         * The Y coordinate.
         */
        public int y;

        /**
         * Initialises this Coordinate.
         *
         * @param point The point vector.
         */
        public Coordinate(Vector2 point) {
            this(point.x, point.y);
            
        }
        public Coordinate(float x, float y) {
            this((int)x, (int)y);
        }
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Tests this Coordinate with another object for equality.
         *
         * @param o the object to compare to
         * @return true of the objects are equal, false if not
         */
        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            if (getClass() != o.getClass()) return false;
            final Coordinate other = (Coordinate) o;
            return (this.x == other.x && this.y == other.y);
        }

        /**
         * Gets a unique hash code for this coordinate.
         *
         * @return the hash code
         */
        @Override
        public int hashCode() {
            int hash = 17;
            hash = hash * 31 + this.x;
            hash = hash * 31 + this.y;
            return hash;
        }

        /**
         * Gets whether this Coordinate is in the same map tile as another.
         *
         * @param b the coordinate to compare with
         * @return true if the coordinates are in the same map tile, false if not
         */
        public boolean inSameTile(Coordinate b) {
            return (this.x / tileWidth == b.x / tileWidth && this.y / tileHeight == b.y / tileHeight);
        }

        /**
         * Returns a string representation of this Coordinate.
         *
         * @return a string representation of this Coordinate
         */
        public String toString() {
            return ("(" + Integer.toString(this.x) + ", " + Integer.toString(this.y) + ")");
        }
        
        public Vector2 vector() {
            return new Vector2(x, y);
        }
    }

    /**
     * Represents a node in the A* search tree.
     */
    public class SearchNode implements Comparable<SearchNode>{

        public SearchNode predecessor;
        public Coordinate coord;
        private float gcost;
        private float hcost;
        public float cost;
        public boolean calculated = false;
        public int iteration;

        public SearchNode(SearchNode predecessor, Coordinate coord, int iteration) {
            this.predecessor = predecessor;
            this.coord = coord;
            this.iteration = iteration;
        }
        
        
        public float getCost(){
            if (!calculated){
                if (predecessor == null){
                    gcost = 0f;
                    hcost = heuristic(coord);
                }
                else {
                    gcost = predecessor.gcost + tileWidth;
                    hcost = heuristic(coord);
                }
                cost = gcost + hcost;
                calculated = true;
            }
            return cost;
        }
        
        @Override
        public int compareTo(SearchNode o) {
            return new Float(this.cost).compareTo(o.cost);
        }
    }
}
