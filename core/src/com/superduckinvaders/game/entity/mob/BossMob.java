package com.superduckinvaders.game.entity.mob;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.ai.PathfindingAI;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.assets.TextureSet;

/**
 * Created by james on 16/02/16.
 */
public class BossMob extends RangedMob {
    public BossMob(Round parent, float x, float y) {
        super(parent, x, y, 20, Assets.boss, 0, new PathfindingAI(parent, 200));
        range = Float.POSITIVE_INFINITY;
    }
}
