package com.superduckinvaders.game.objective;

import com.superduckinvaders.game.Round;

public class SurviveObjective extends Objective {

private int timer = 0;

@Override
public String getObjectiveString(){
  return "Survive 3:00 minutes"
}

@Override
public update(float delta){
    if (timer == (3*60*1000)){
        status = ObjectiveStatus.COMPLETED;
    } else{
      timer = timer + delta;
    }
  }


}
