
package com.superduckinvaders.game.ai;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.entity.mob.Mob;
import com.superduckinvaders.game.entity.Character;


/**
 * AI that follows and attacks the player within a certain range.
 */
public class PathfindingAI extends AI {

	/**
	 * How many iterations to use in the pathfinding algorithm.
	 */
	public final static int PATHFINDING_ITERATION_LIMIT = 20;

	/**
	 * How often to update the AI.
	 */
	public final static float PATHFINDING_RATE = 0.4f;

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
	 * @deprecated
	 */
	private Vector2 playerPos;

	/**
	 * Target's last position.
	 */
	private Vector2 targetPos;

	/**
	 * Used to calculate rate of pathfinding.
	 */
	private float deltaOffsetLimit = 0;

	/**
	 * Used to track when to recalculate AI.
	 */
	private float currentOffset = 0;

	/**
	 * How far away from the player this PathfindingAI can attack.
	 */
	private int targetRange;

	/**
	 * The Coordinate for the AI to find a path for.
	 */
	public Coordinate targetCoord;

	/**
	 * The Character that this AI is pathfinding towards.
	 */
	public Character target;

	/**
	 * Initialises this PathfindingAI.
	 *
	 * @param round
	 *                the round the Mob this AI controls is a part of
	 * @param targetRange
	 *                how close to the player this PathfindingAI tries to get
	 */
	public PathfindingAI(Round round, int targetRange) {
		super(round);

		tileWidth = round.getTileWidth();
		tileHeight = round.getTileHeight();
		this.targetRange = targetRange;
		this.target = round.getPlayer();
	}
	
	
	/**
	 * Sets this AI's target.
	 * 
	 * @param c
	 *                New target for the AI.
	 */
	public void setTarget(Character c) {
		target = c;
	}


	/**
	 * Updates this PathfindingAI.
	 *
	 * @param mob
	 *                the Mob using this AI
	 * @param delta
	 *                time since the previous update
	 */
	@Override
	public void update(Mob mob, float delta) {
		targetPos = target.getCentre();

		float distanceToTarget = mob.distanceTo(targetPos);
		float distanceToTargetTile = (targetCoord != null) ? mob.getCentre().sub(targetCoord.vector()).len() : 0f;

		currentOffset += delta;
		if (((currentOffset >= deltaOffsetLimit) || (distanceToTargetTile < 2))
				&& ((int) distanceToTarget < (1280 / 4))) {
			deltaOffsetLimit = PathfindingAI.PATHFINDING_RATE
					+ (MathUtils.random() % PathfindingAI.PATHFINDING_RATE_OFFSET);
			currentOffset = 0;
			targetCoord = findPath(mob);
		}

		// targetPoint = (target != null) ? target.vector() : new Vector2(playerPos).setLength(1f);
		if (targetCoord != null) {
			mob.applyVelocity(targetCoord.vector());
		}
	}


	/**
	 * A variation of A* algorithm. Returns a meaningful target coordinate as a pair of integers. Recalculated every
	 * tick as player might move and change pathfinding coordinates.
	 *
	 * @param mob
	 *                Mob that a path is being generated for
	 * @return Returns a Coordinate for the path finding
	 */
	private Coordinate findPath(Mob mob) {
		Vector2 mobPos = mob.getCentre();
		Vector2 mobSize = mob.getSize();
		Coordinate startCoord = roundToTile(mobPos);
		Coordinate finalCoord = roundToTile(targetPos);
		boolean finalFound = false;

		if (round.pathIsClear(mobPos, mobSize, targetPos)) {
			if (new Vector2(targetPos).sub(mobPos).len() < targetRange) {
				return null;
			} else {
				currentOffset = deltaOffsetLimit;
				return new Coordinate(targetPos);
			}
		}

		PriorityQueue<Coordinate> fringe = new PriorityQueue<Coordinate>();
		HashMap<Coordinate, SearchNode> visitedStates = new HashMap<Coordinate, SearchNode>();
		fringe.add(startCoord);
		visitedStates.put(startCoord, new SearchNode(null, startCoord, 0));

		while (!fringe.isEmpty()) {

			Coordinate currentCoord = fringe.poll();
			SearchNode currentState = visitedStates.get(currentCoord);

			if (currentState.iteration >= PathfindingAI.PATHFINDING_ITERATION_LIMIT) {
				continue;
			}

			if (currentCoord.inSameTile(finalCoord)) {
				finalCoord = currentCoord;
				finalFound = true;
				break;
			}

			// work out N, E, S, W permutations
			Coordinate[] perm = { new Coordinate(currentCoord.x, currentCoord.y + tileHeight),
					new Coordinate(currentCoord.x + tileWidth, currentCoord.y),
					new Coordinate(currentCoord.x, currentCoord.y - tileHeight),
					new Coordinate(currentCoord.x - tileWidth, currentCoord.y) };

			for (Coordinate currentPerm : perm) {
				if (!visitedStates.containsKey(currentPerm)
						&& !round.collidePoint(currentPerm.vector())) {
					fringe.add(currentPerm);
					visitedStates.put(currentPerm, new SearchNode(currentState, currentPerm,
							currentState.iteration + 1));
				}
			}
		}
		if (!finalFound) {
			return null;
		} else {
			SearchNode resultNode;
			List<SearchNode> path = new ArrayList<SearchNode>();
			path.add(visitedStates.get(finalCoord));
			while (path.get(path.size() - 1) != visitedStates.get(startCoord)) {
				SearchNode pred = path.get(path.size() - 1).predecessor;
				if (pred == null) {
					break;
				}
				path.add(pred);
			}
			int index = path.size() - 1;
			while (index > 0) {
				SearchNode tempNode = path.get(index - 1);
				if (!round.pathIsClear(mobPos, mobSize, tempNode.coord.vector())) {
					break;
				}
				index--;
			}
			resultNode = path.get(index);

			return resultNode.coord;
		}
	}


	/**
	 * Round an exact position to the nearest tile coordinate.
	 *
	 * @param pos
	 *                the position vector
	 * @return the Coordinate of the tile
	 */
	public Coordinate roundToTile(Vector2 pos) {
		return roundToTile(pos.x, pos.y);

	}


	/**
	 * Round an exact position to the nearest tile coordinate.
	 *
	 * @param x
	 *                the x position
	 * @param y
	 *                the y position
	 * @return the Coordinate of the tile
	 */
	public Coordinate roundToTile(float x, float y) {
		int nx = (int) (((int) (x / tileWidth) + 0.5f) * tileWidth);
		int ny = (int) (((int) (y / tileHeight) + 0.5f) * tileHeight);
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
		 * @param point
		 *                The point vector.
		 */
		public Coordinate(Vector2 point) {
			this((int) point.x, (int) point.y);

		}


		/**
		 * Initialises this Coordinate.
		 *
		 * @param x
		 *                the x position.
		 * @param y
		 *                the y position.
		 */
		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}


		/**
		 * Compares this Coordinate to another Coordinate.
		 *
		 * @param o
		 *                the coordinate to compare to
		 * @return the result of the comparison
		 */
		@Override
		public int compareTo(Coordinate o) {
			float playerDistanceA = vector().sub(targetPos).len();
			float playerDistanceB = o.vector().sub(targetPos).len();
			return new Float(playerDistanceA).compareTo(playerDistanceB);
		}


		/**
		 * Tests this Coordinate with another object for equality.
		 *
		 * @param o
		 *                the object to compare to
		 * @return true of the objects are equal, false if not
		 */
		@Override
		public boolean equals(Object o) {
			if (o == null) {
				return false;
			}
			if (getClass() != o.getClass()) {
				return false;
			}
			final Coordinate other = (Coordinate) o;
			return ((x == other.x) && (y == other.y));
		}


		/**
		 * Gets a unique hash code for this coordinate.
		 *
		 * @return the hash code
		 */
		@Override
		public int hashCode() {
			int hash = 17;
			hash = (hash * 31) + x;
			hash = (hash * 31) + y;
			return hash;
		}


		/**
		 * Gets whether this Coordinate is in the same map tile as another.
		 *
		 * @param b
		 *                the coordinate to compare with
		 * @return true if the coordinates are in the same map tile, false if not
		 */
		public boolean inSameTile(Coordinate b) {
			return (((x / tileWidth) == (b.x / tileWidth)) && ((y / tileHeight) == (b.y / tileHeight)));
		}


		/**
		 * Returns a string representation of this Coordinate.
		 *
		 * @return a string representation of this Coordinate
		 */
		@Override
		public String toString() {
			return ("(" + Integer.toString(x) + ", " + Integer.toString(y) + ")");
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
		public Coordinate coord;
		/**
		 * The iteration this node is a part of.
		 */
		public int iteration;


		/**
		 * Initialises this SearchNode.
		 *
		 * @param predecessor
		 *                the predecessor node
		 * @param iteration
		 *                the iteration of this node
		 */
		public SearchNode(SearchNode predecessor, Coordinate coord, int iteration) {
			this.predecessor = predecessor;
			this.coord = coord;
			this.iteration = iteration;
		}
	}
}
