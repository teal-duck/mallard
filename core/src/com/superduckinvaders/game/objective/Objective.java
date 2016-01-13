package com.superduckinvaders.game.objective;

import com.superduckinvaders.game.round.Round;

/**
 * Created by olivermcclellan on 13/01/2016.
 */
public abstract class Objective {

    /**
     * Indicates that the objective hasn't yet been completed.
     */
    public static final int OBJECTIVE_ONGOING = 0;

    /**
     * Indicates that the objective has been completed.
     */
    public static final int OBJECTIVE_COMPLETED = 1;

    /**
     * Indicates that the objective has been failed.
     */
    public static final int OBJECTIVE_FAILED = 2;

    /**
     * The round that this Objective belongs to.
     */
    protected Round parent;

    /**
     * The current status of this Objective.
     */
    protected int status = OBJECTIVE_ONGOING;

    /**
     * Initialises this Objective in the specified round.
     * @param parent the round that this Objective belongs to
     */
    public Objective(Round parent) {
        this.parent = parent;
    }

    /**
     * Gets the current status of the Objective.
     * @return the current status of the Objective (one of the OBJECTIVE_ constants);
     */
    public int getStatus() {
        return status;
    }

    /**
     * Gets a string describing this Objective to be printed on screen.
     * @return a string describing this Objective
     */
    public abstract String getObjectiveString();

    /**
     * Updates the status towards this Objective.
     * @param delta the time since the last tick
     */
    public abstract void update(float delta);
}
