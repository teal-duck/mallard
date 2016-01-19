
package com.superduckinvaders.game.ai;

import com.badlogic.gdx.math.MathUtils;
import com.superduckinvaders.game.entity.Mob;
import com.superduckinvaders.game.round.Round;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class ZombieAI extends AI {

    /**
     * How many seconds between attacks?
     */
    public static final double ATTACK_DELAY = 1;
    /**
     * How many iterations to use in the pathfinding algorithm.
     */
    public final static int PATHFINDING_ITERATION_LIMIT = 20;
    /**
     * How often to update the AI.
     */
    public final static float PATHFINDING_RATE = (float) 0.2;
    /**
     * The random offset to be added or taken from the base pathfinding rate.
     */
    public final static float PATHFINDING_RATE_OFFSET = (float) 0.05;
    //code shortcuts
    private int tileWidth;
    private int tileHeight;
    private int playerX;
    private int playerY;
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
    private double attackTimer = 0;


    /**
     * Initialises this ZombieAI.
     *
     * @param round       the round the player is in
     * @param attackRange how far away from the player can this ZombieAI attack
     */
    public ZombieAI(Round round, int attackRange) {
        super(round);

        this.tileWidth = round.getTileWidth();
        this.tileHeight = round.getTileHeight();
        this.attackRange = attackRange;
    }

    private void updatePlayerCoords() {
        playerX = (int) round.getPlayer().getX();
        playerY = (int) round.getPlayer().getY();
    }

    @Override
    public void update(Mob mob, float delta) {
        updatePlayerCoords();

        double distanceX = mob.getX() - playerX;
        double distanceY = mob.getY() - playerY;
        double distanceFromPlayer = mob.distanceTo(playerX, playerY);

        currentOffset += delta;
        if (currentOffset >= deltaOffsetLimit && (int) distanceFromPlayer < 1280 / 4) {
            deltaOffsetLimit = PATHFINDING_RATE + (MathUtils.random() % PATHFINDING_RATE_OFFSET);
            currentOffset = 0;
            Coordinate targetCoord = FindPath(mob);
            Coordinate targetDir = new Coordinate((int) (targetCoord.x - mob.getX()), (int) (targetCoord.y - mob.getY()));
            mob.setVelocity(targetDir.x, targetDir.y);
        }

        //damage part
        if ((int) distanceFromPlayer < attackRange && attackTimer <= 0) {
            round.getPlayer().damage(1);
            attackTimer = ATTACK_DELAY;
        } else if (attackTimer > 0) {
            attackTimer -= delta;
        }
    }

    /**
     * A variation of A* algorithm. Returns a meaningful target coordinate as a pair of integers.
     * Recalculated every tick as player might move and change pathfinding coordinates.
     *
     * @param mob Mob that a path is being generated for
     * @return Returns a Coordinate for the path finding
     */
    private Coordinate FindPath(Mob mob) {
        Coordinate startCoord = new Coordinate((int) mob.getX(), (int) mob.getY());
        Coordinate finalCoord = new Coordinate(playerX, playerY);
        boolean finalFound = false;

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
                if (!(mob.collidesXfrom(currentPerm.x - currentCoord.x, currentCoord.x, currentCoord.y) ||
                        mob.collidesYfrom(currentPerm.y - currentCoord.y, currentCoord.x, currentCoord.y) ||
                        visitedStates.containsKey(currentPerm))) {
                    fringe.add(currentPerm);
                    visitedStates.put(currentPerm, new SearchNode(currentState, currentState.iteration + 1));
                }
                if (currentPerm.inSameTile(finalCoord)) {
                    visitedStates.put(currentPerm, new SearchNode(currentState, currentState.iteration + 1));
                    finalCoord = currentPerm;
                    finalFound = true;
                    break;
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
                path.add(path.get(path.size() - 1).predecessor);
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

    /**
     * used in pathfinding algorithm, implements state in statespace
     */
    class Coordinate implements Comparable<Coordinate> {
        public int x;
        public int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Coordinate o) {
            Double playerDistanceA = Math.sqrt(Math.pow((x - playerX), 2) + Math.pow((y - playerY), 2));
            Double playerDistanceB = Math.sqrt(Math.pow((o.x - playerX), 2) + Math.pow((o.y - playerY), 2));
            return playerDistanceA.compareTo(playerDistanceB);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            if (getClass() != o.getClass()) return false;
            final Coordinate other = (Coordinate) o;
            return (this.x == other.x && this.y == other.y);
        }

        @Override
        public int hashCode() {
            int hash = 17;
            hash = hash * 31 + this.x;
            hash = hash * 31 + this.y;
            return hash;
        }

        public boolean inSameTile(Coordinate B) {
            return (this.x / tileWidth == B.x / tileWidth && this.y / tileHeight == B.y / tileHeight);
        }

        public String toString() {
            return ("(" + Integer.toString(this.x) + ", " + Integer.toString(this.y) + ")");
        }
    }

    /**
     * used in pathFinding algorithm, implements node of the search tree
     */
    class SearchNode {
        public SearchNode predecessor;
        public int iteration;

        public SearchNode(SearchNode predecessor, int iteration) {
            this.predecessor = predecessor;
            this.iteration = iteration;
        }
    }
}