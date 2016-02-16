package com.superduckinvaders.game.objective;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.entity.mob.Mob;

import java.util.ArrayList;

/**
 * Created by james on 07/02/16.
 */

/**
 * Represents an enemy defeating objective.
 */

public class KillObjective extends Objective {

    /**
     * Stores the amount of time the player has survived for.
     */
    private float timer = 0;

    private ArrayList<Mob> targets;
    private String description;

    protected KillObjective(Round parent, String description) {
        super(parent);
        this.description = description;
    }
    public KillObjective(Round parent, Mob target, String description) {
        this(parent, description);
        targets = new ArrayList<Mob>();
        targets.add(target);

    }
    public KillObjective(Round parent, ArrayList<Mob> targets, String description) {
        this(parent, description);
        this.targets = targets;

    }


    @Override
    public void update(float delta) {
        for (int i = 0; i<targets.size();) {
            Mob mob = targets.get(i);
            if (mob.isRemoved()){
                targets.remove(mob);
            }
            else {
                i++;
            }
        }

        if (targets.size() == 0) {
            status = ObjectiveStatus.COMPLETED;
        }
    }

    @Override
    public String getObjectiveString() {
        return description;
    }
}

