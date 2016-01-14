
package com.superduckinvaders.game.ai;

import com.superduckinvaders.game.entity.Mob;
import com.superduckinvaders.game.round.Round;

import java.util.ArrayList;

public class ZombieAI extends AI {
	
	public final static int PATHFINDING_ITERATION_LIMIT = 20;
	
	public ZombieAI(Round currentRound){
		super(currentRound);
	}
	
	@Override
	public void update(Mob mob) {
		int[] coord = FindPath(mob);
		mob.setVelocity(coord[0], coord[1]);
	}
	
	@Override
	public boolean StillActive(Mob mob){
		return true;
	}
	

	
	/**
	 * A variation of A* algorithm. Returns a meaningful target coordinate as a pair of integers.
	 * Recalculated every tick as player might move and change pathfinding coordinates.
	 */
	private int[] FindPath(Mob mob){
		int startCoord[] = {(int)mob.getX(), (int)mob.getY()};
		int finalCoord[] = {(int)roundPointer.getPlayer().getX(), (int)roundPointer.getPlayer().getY(), -1};
		
		int tileWidth = roundPointer.getTileWidth();
		int tileHeight = roundPointer.getTileHeight();
		
		int finalCoordTile[] = {finalCoord[0]/tileWidth, finalCoord[1]/tileHeight};
		

		
		ArrayList<int[]> queue = new ArrayList<int[]>();
		queue.add(new int[]{startCoord[0], startCoord[1], 0});
		
		//block below tries to find a valid path in a limited amount of steps
		for(int i = 0; i<PATHFINDING_ITERATION_LIMIT; i++)
		{
			//generate N, E, S, W tile coordinates
			for (int j = 0; j<queue.size(); j++)
			{
				int[][]perm = new int[4][];
				perm[0] = new int[]{queue.get(j)[0], queue.get(j)[1]+tileHeight, queue.get(j)[2]+1};
				perm[1] = new int[]{queue.get(j)[0]+tileWidth, queue.get(j)[1], queue.get(j)[2]+1};
				perm[2] = new int[]{queue.get(j)[0], queue.get(j)[1]-tileHeight, queue.get(j)[2]+1};
				perm[3] = new int[]{queue.get(j)[0]-tileWidth, queue.get(j)[1], queue.get(j)[2]+1};
				for(int[] currentPerm : perm)
				{
					if(!(roundPointer.isTileBlocked(currentPerm[0], currentPerm[1]) || CoordInQueue(currentPerm, queue)))
						queue.add(currentPerm);
					if(currentPerm[0]/tileWidth==finalCoordTile[0] && currentPerm[1]/tileHeight==finalCoordTile[1])
					{
						finalCoord = new int[]{finalCoord[0], finalCoord[1], i};
						break;
					}
				}
			}
		}
		
		if(finalCoord[2]==-1) //if solution not found
			return(startCoord);
		
		//block below finds the path from the queue generated
		ArrayList<int[]> path = new ArrayList<int[]>();
		for(int i = 0; i<PATHFINDING_ITERATION_LIMIT; i++)
		{
			int[][]perm = new int[4][];
			perm[0] = new int[]{finalCoord[0], finalCoord[1]+1, finalCoord[2]-1};
			perm[1] = new int[]{finalCoord[0]+1, finalCoord[1], finalCoord[2]-1};
			perm[2] = new int[]{finalCoord[0], finalCoord[1]-1, finalCoord[2]-1};
			perm[3] = new int[]{finalCoord[0]-1, finalCoord[1], finalCoord[2]-1};
			
			for(int[] currentPerm : perm)
			{
				//assert -  one of the above permutations will exist in the queue 
				if(queue.contains(currentPerm))
				{
					path.add(currentPerm);
					finalCoord=currentPerm;
					if(finalCoord[0]/tileWidth==startCoord[0]/tileWidth && finalCoord[1]/tileHeight==startCoord[1]/tileHeight)
						break;
				}
			}
		}
		
		//path is in reverse order, hence pull from the end
		int[] resultCoord = path.get(path.size()-2);
		return new int[]{resultCoord[0], resultCoord[1]};
		
		
	}
	
	/**
	 * Strictly private function, part of FindPath algorithm.
	 * Checks if some coordinate already exists in coordinate Queue
	 * Used instead of 'contains' built-in method, as third item in coordinate is irrelevant 
	 */
	private boolean CoordInQueue(int[] coord, ArrayList<int[]> queue)
	{
		for (int[] queueCoord : queue)
		{
			if (queueCoord[0]==coord[0] && queueCoord[1]==coord[1])
				return true;
		}
		return false;
	}
	
}