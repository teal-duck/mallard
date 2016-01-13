package com.superduckinvaders.game.objective;

import com.superduckinvaders.game.entity.Entity;
import com.superduckinvaders.game.round.Round;

/**
 * Created by olivermcclellan on 13/01/2016.
 */
public class CollectObjective extends Objective {

    private Entity target;

    public CollectObjective(Round parent, Entity target) {
        super(parent);

        this.target = target;
    }

    @Override
    public String getObjectiveString() {
        return "Collect the object";
    }

    @Override
    public void update(float delta) {
        if (parent.getPlayer().intersects(target.getX(), target.getY(), target.getWidth(), target.getHeight())) {
            status = OBJECTIVE_COMPLETED;
        }
    }
}
