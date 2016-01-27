
package com.superduckinvaders.game.ai;

import com.badlogic.gdx.math.MathUtils;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.entity.Mob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import com.badlogic.gdx.math.Vector2;



/**
 * AI that follows and attacks the player within a certain range.
 */
public class ZombieAI extends AI {

    /**
     * How many seconds between attacks?
     */
    public static final float ATTACK_DELAY = 1;
    /**
     * How many iterations to use in the pathfinding algorithm.
     */
    public final static int PATHFINDING_ITERATION_LIMIT = 20;
    /**
     * How often to update the AI.
     */
    public final static float PATHFINDING_RATE = (float) 2f;
    /**
     * The random offset to be added or taken from the base pathfinding rate.
     */
    public final static float PATHFINDING_RATE_OFFSET = (float) 0.05;
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
    private Vector2 playerPos;
    /**
     * Used to calculate rate of pathfinding.
     */
    private float deltaOffsetLimit = 0;
    /**
     * Used to track when to recalculate AI.
     */
    private float currentOffset = 0;
    /**
     * How far away from the player this ZombieAI can attack.
     */
    private int attackRange;
    /**
     * How long before we can attack again.
     */
    private float attackTimer = 0;
    
    public Coordinate target;
    public Vector2 targetPoint;

    /**
     * Initialises this ZombieAI.
     *
     * @param round       the round the Mob this AI controls is a part of
     * @param attackRange how far away from the player can this ZombieAI attack
     */
    public ZombieAI(Round round, int attackRange) {
        super(round);

        this.tileWidth = round.getTileWidth();
        this.tileHeight = round.getTileHeight();
        this.attackRange = attackRange;
        
    }
    
    public void applyVelocity(Mob mob){
        
        Vector2 dst = new Vector2(targetPoint);
        Vector2 velocity = dst.sub(mob.getCentre())
                               .nor().scl(mob.getSpeed());
        mob.setVelocity(velocity);
    }


    /**
     * Updates this ZombieAI.
     *
     * @param mob   pointer to the Mob using this AI
     * @param delta time since the previous update
     */
    @Override
    public void update(Mob mob, float delta) {
        playerPos = round.getPlayer().getCentre();

        float distanceFromPlayer = mob.distanceTo(playerPos);
        float distanceToTargetTile;
        
        if (target != null){
            distanceToTargetTile = mob.getCentre().sub(targetPoint).len();
        }
        else {
            distanceToTargetTile = 0f;
        }
        
        currentOffset += delta;
        if ((currentOffset >= deltaOffsetLimit || distanceToTargetTile < 2) && (int) distanceFromPlayer < 1280 / 4) {
            deltaOffsetLimit = PATHFINDING_RATE + (MathUtils.random() % PATHFINDING_RATE_OFFSET);
            currentOffset = 0;
            target = FindPath(mob);
            targetPoint = target.vector();
        }
        applyVelocity(mob);
    }

    /**
     * A variation of A* algorithm. Returns a meaningful target coordinate as a pair of integers.
     * Recalculated every tick as player might move and change pathfinding coordinates.
     *
     * @param mob Mob that a path is being generated for
     * @return Returns a Coordinate for the path finding
     */
    private Coordinate FindPath(Mob mob) {
        Vector2 mobPos = mob.getCentre();
        Coordinate startCoord = roundToTile(mobPos);
        Coordinate finalCoord = roundToTile(playerPos);
        boolean finalFound = false;
        
        if (!round.rayCast(mobPos, playerPos)){
            currentOffset = deltaOffsetLimit;
            return finalCoord;
        }
        

        PriorityQueue<Coordinate> fringe = new PriorityQueue<Coordinate>();
        HashMap<Coordinate, SearchNode> visitedStates = new HashMap<Coordinate, SearchNode>();
        fringe.add(startCoord);
        visitedStates.put(startCoord, new SearchNode(null, 0));

        while (!fringe.isEmpty()) {

            Coordinate currentCoord = fringe.poll();
            SearchNode currentState = visitedStates.get(currentCoord);

            if (currentState.iteration >= PATHFINDING_ITERATION_LIMIT) {
                continue;
            }

            //work out N, E, S, W permutations
            Coordinate[] perm = new Coordinate[4];
            perm[0] = new Coordinate(currentCoord.x, currentCoord.y + tileHeight);
            perm[1] = new Coordinate(currentCoord.x + tileWidth, currentCoord.y);
            perm[2] = new Coordinate(currentCoord.x, currentCoord.y - tileHeight);
            perm[3] = new Coordinate(currentCoord.x - tileWidth, currentCoord.y);

            for (Coordinate currentPerm : perm) {
                boolean isEmpty = !round.collidePoint(currentPerm.x, currentPerm.y);
                // if (currentPerm.inSameTile(finalCoord) || !round.rayCast(new Vector2(currentPerm.x, currentPerm.y), playerPos)) {
                if (currentPerm.inSameTile(finalCoord)) {
                    visitedStates.put(currentPerm, new SearchNode(currentState, currentState.iteration + 1));
                    finalCoord = currentPerm;
                    finalFound = true;
                    break;
                }
                if (isEmpty && !visitedStates.containsKey(currentPerm)) {
                    fringe.add(currentPerm);
                    visitedStates.put(currentPerm, new SearchNode(currentState, currentState.iteration + 1));
                }
            }
            if (finalFound) break;
        }
        if (!finalFound) {
            return startCoord;
        } else {
            SearchNode resultNode = null;
            List<SearchNode> path = new ArrayList<SearchNode>();
            path.add(visitedStates.get(finalCoord));
            while (path.get(path.size() - 1) != visitedStates.get(startCoord)) {
                SearchNode pred = path.get(path.size() - 1).predecessor;
                if (pred == null){
                    break;
                }
                path.add(pred);
            }
            switch (path.size()) {
                case 1:
                    resultNode = path.get(path.size() - 1);
                    break;
                default:
                    resultNode = path.get(path.size() - 2);
                    break;
            }
            //for loop below will terminate after at most 4 iterations
            for (Coordinate key : visitedStates.keySet()) {
                if (visitedStates.get(key) == resultNode)
                    return key;
            }
        }
        return startCoord;
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
    public class Coordinate implements Comparable<Coordinate> {
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
         * @param x the x coordinate
         * @param y the y coordinate
         */
        public Coordinate(float x, float y) {
            this((int)x, (int)y);
        }
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Compares this Coordinate to another Coordinate.
         *
         * @param o the coordinate to compare to
         * @return the result of the comparison
         */
        @Override
        public int compareTo(Coordinate o) {
            float playerDistanceA = (float)Math.sqrt(Math.pow((x - playerPos.x), 2) + Math.pow((y - playerPos.y), 2));
            float playerDistanceB = (float)Math.sqrt(Math.pow((o.x - playerPos.x), 2) + Math.pow((o.y - playerPos.y), 2));
            return new Float(playerDistanceA).compareTo(playerDistanceB);
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
    class SearchNode {
        /**
         * The predecessor node in the search tree.
         */
        public SearchNode predecessor;
        /**
         * The iteration this node is a part of.
         */
        public int iteration;

        /**
         * Initialises this SearchNode.
         *
         * @param predecessor the predecessor node
         * @param iteration   the iteration of this node
         */
        public SearchNode(SearchNode predecessor, int iteration) {
            this.predecessor = predecessor;
            this.iteration = iteration;
        }
    }
}
