
package com.superduckinvaders.game.ai;

import com.superduckinvaders.game.entity.Mob;
import com.superduckinvaders.game.round.Round;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Random;

public class ZombieAI extends AI {

    /**
	 * How many seconds between attacks?
	 */
	public static final double ATTACK_DELAY = 1;

    public final static int PATHFINDING_ITERATION_LIMIT = 20;
    public final static float PATHFINDING_RATE = (float) 0.2;
    public final static float PATHFINDING_RATE_OFFSET = (float) 0.05;
    //code shortcuts
    final int tileWidth = roundPointer.getTileWidth();
    final int tileHeight = roundPointer.getTileHeight();
    private int playerX;
    private int playerY;
    /**
     * used to calculate rate of pathfinding
     */
    private float deltaOffsetLimit = 0;
    /**
     * used to track when to recalculate AI
     */
    private float currentOffset = 0;
    
    private Random randGen = new Random();
    
    /**
     * range of zombie melee attacks in pixels
     */
    private int attackRange;

	/**
	 * How long before we can attack again.
	 */
	private double attackTimer = 0;
    
    
    
    /**
     * used in pathfinding algorithm, implements state in statespace
     */
	class Coord implements Comparable<Coord>{
		public int x;
		public int y;
		public Coord(int x, int y){
			this.x = x;
			this.y = y;
		}
		@Override
		public int compareTo(Coord o) {
			Double playerDistanceA = Math.sqrt(Math.pow((x-playerX), 2) + Math.pow((y-playerY), 2));
			Double playerDistanceB = Math.sqrt(Math.pow((o.x-playerX), 2) + Math.pow((o.y-playerY), 2));
			return playerDistanceA.compareTo(playerDistanceB);
		}
		@Override
		public boolean equals(Object o){
			if (o==null) return false;
		    if (getClass() != o.getClass()) return false;
		    final Coord other = (Coord) o;
			return (this.x==other.x && this.y==other.y);
		}
		
		@Override
		public int hashCode(){
			int hash = 17;
			hash = hash * 31 + this.x;
		    hash = hash * 31 + this.y;
		    return hash;
		}
		
		public boolean inSameTile(Coord B){
			return (this.x/tileWidth==B.x/tileWidth && this.y/tileHeight==B.y/tileHeight);
		}
		
		public String toString(){
			return ("("+Integer.toString(this.x)+", "+Integer.toString(this.y)+")");
		}
	}
	/**
	 * used in pathFinding algorithm, implements node of the search tree
	 */
	class SearchNode{
		public SearchNode predecessor;
		public int iteration;
		public SearchNode (SearchNode predecessor, int iteration){
			this.predecessor = predecessor;
			this.iteration = iteration;
		}
	}
    

    
    /**
     * constructor for zombie AI
     * @param currentRound round pointer
     * @param range range of zombie attacks
     * @param args optional arguments for the given AI -  args[0] is zombie attack range
     */
    public ZombieAI(Round currentRound, int[] args){
        super(currentRound, args);
        attackRange = args[0];
    }
    
    private void updatePlayerCoords(){
    	playerX = (int)roundPointer.getPlayer().getX();
    	playerY = (int)roundPointer.getPlayer().getY();
    }
    
    @Override
    public void update(Mob mob, float delta) {
    	updatePlayerCoords();

        double distanceX = mob.getX() - playerX;
        double distanceY = mob.getY() - playerY;
        double distanceFromPlayer = Math.sqrt( Math.pow(distanceX, 2) + Math.pow(distanceY,2));
        
    	currentOffset += delta;
    	if (currentOffset >= deltaOffsetLimit && (int)distanceFromPlayer < 1280/4){
    		System.out.println(distanceFromPlayer);
    		deltaOffsetLimit = PATHFINDING_RATE + (randGen.nextFloat() % PATHFINDING_RATE_OFFSET);
    		currentOffset=0;
    		Coord targetCoord = FindPath(mob);
            Coord targetDir = new Coord ((int) (targetCoord.x-mob.getX()), (int) (targetCoord.y-mob.getY()));
            mob.setVelocity(targetDir.x, targetDir.y);
    	}
        //damage part
        if ((int)distanceFromPlayer < attackRange && attackTimer <= 0) {
			roundPointer.getPlayer().damage(1);
			attackTimer = ATTACK_DELAY;
		} else if(attackTimer > 0) {
			attackTimer -= delta;
		}
    }
    
    @Override
    public boolean active(Mob mob){
        return ((mob.getX()>roundPointer.getPlayer().getX()-1280/4 ||
        		mob.getX()<roundPointer.getPlayer().getX()+1280/4) &&
        		(mob.getY()<roundPointer.getPlayer().getY()-1280/4 ||
        		mob.getY()<roundPointer.getPlayer().getY()+1280/4));
    }
    

    
    /**
     * A variation of A* algorithm. Returns a meaningful target coordinate as a pair of integers.
     * Recalculated every tick as player might move and change pathfinding coordinates.
     */
    private Coord FindPath(Mob mob){
    	
        Coord startCoord = new Coord((int)mob.getX(), (int)mob.getY());
        Coord finalCoord = new Coord(playerX, playerY);
        boolean finalFound = false;
        
        PriorityQueue<Coord> fringe = new PriorityQueue<Coord>();
        HashMap<Coord, SearchNode> visitedStates = new HashMap<Coord, SearchNode>();
        fringe.add(startCoord);
        visitedStates.put(startCoord, new SearchNode(null, 0));
        
        while (!fringe.isEmpty()) {
        	
            	Coord currentCoord = fringe.poll();
            	SearchNode currentState = visitedStates.get(currentCoord);
            	
            	if (currentState.iteration>=PATHFINDING_ITERATION_LIMIT)
            		continue;
            		
            	
            	//work out N, E, S, W permutations
                Coord[] perm = new Coord[4];
                perm[0] = new Coord(currentCoord.x, currentCoord.y+tileHeight);
                perm[1] = new Coord(currentCoord.x+tileWidth, currentCoord.y);
                perm[2] = new Coord(currentCoord.x, currentCoord.y-tileHeight);
                perm[3] = new Coord(currentCoord.x-tileWidth, currentCoord.y);
                
                for(Coord currentPerm : perm)
                {
                    if(!(mob.collidesXfrom(currentPerm.x-currentCoord.x, currentCoord.x, currentCoord.y) ||
                    		mob.collidesYfrom(currentPerm.y-currentCoord.y, currentCoord.x, currentCoord.y) ||
                    		visitedStates.containsKey(currentPerm))){
                        fringe.add(currentPerm);
                    	visitedStates.put(currentPerm, new SearchNode(currentState, currentState.iteration+1));
                    }
                	if(currentPerm.inSameTile(finalCoord))
                    {
                		visitedStates.put(currentPerm, new SearchNode(currentState, currentState.iteration+1));
                		finalCoord = currentPerm;
                        finalFound = true;
                        break;
                    }
                }
                if (finalFound) break;
            }
            if (!finalFound){
            	return startCoord;
            }
            else{
            	SearchNode resultNode = null;
            	List<SearchNode> path = new ArrayList<SearchNode>();
            	path.add(visitedStates.get(finalCoord));
            	while(path.get(path.size()-1)!=visitedStates.get(startCoord)){
            		path.add(path.get(path.size()-1).predecessor);
            	}
            	switch (path.size()){
            	case 1:
            		resultNode = path.get(path.size()-1);
            		break;
            	default:
            		resultNode = path.get(path.size()-2);
            		break;
            	}
            	//for loop below will terminate after at most 4 iterations
            	for (Coord key: visitedStates.keySet()){
            		if (visitedStates.get(key)==resultNode)
            			return key;
            	}
            }
        return startCoord;//you shouldn't be here
    }
}