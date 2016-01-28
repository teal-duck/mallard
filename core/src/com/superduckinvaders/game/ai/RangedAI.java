
package com.superduckinvaders.game.ai;

import com.badlogic.gdx.math.MathUtils;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import com.badlogic.gdx.math.Vector2;



public class RangedAI extends PathfindingAI {
   
    public RangedAI(Round round) {
        super(round, 20);
    }
}
