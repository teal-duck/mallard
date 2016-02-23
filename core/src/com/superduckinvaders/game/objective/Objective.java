package com.superduckinvaders.game.objective;


import com.superduckinvaders.game.Round;


/**
 * Represents an objective that needs to be completed in order to advance.
 */
public abstract class Objective {
	/**
	 * Stores the possible objective statuses.
	 */
	public enum ObjectiveStatus {
		ONGOING, COMPLETED, FAILED
	}


	public ObjectiveStatus status = ObjectiveStatus.ONGOING;

	/**
	 * The round that this Objective belongs to.
	 */
	protected Round parent;


	/**
	 * Initialises this Objective in the specified round.
	 *
	 * @param parent
	 *                the round that this Objective belongs to
	 */
	public Objective(Round parent) {
		this.parent = parent;
	}


	/**
	 * Gets the current status of the Objective.
	 *
	 * @return the current status of the Objective.
	 * @see ObjectiveStatus
	 */
	public ObjectiveStatus getStatus() {
		return status;
	}


	/**
	 * Gets a string describing this Objective to be printed on screen.
	 *
	 * @return a string describing this Objective
	 */
	public abstract String getObjectiveString();


	/**
	 * Updates the status towards this Objective.
	 *
	 * @param delta
	 *                how much time has passed since the last update
	 */
	public abstract void update(float delta);
}
