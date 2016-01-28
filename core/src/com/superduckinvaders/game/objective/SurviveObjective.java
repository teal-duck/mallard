package com.superduckinvaders.game.objective;

import com.superduckinvaders.game.Round;

public class SurviveObjective extends Objective {

private float timer = 0;

public SurviveObjective(Round parent){
    super(parent);
}

@Override
public String getObjectiveString(){
  return "Survive 3:00 minutes";
}

@Override
public void update(float delta){
    if (timer >= (10)){
         status = ObjectiveStatus.COMPLETED;
    } else{
      this.timer += delta;

    }
  }


}
