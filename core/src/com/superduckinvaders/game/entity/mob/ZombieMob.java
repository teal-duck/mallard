package com.superduckinvaders.game.entity.mob;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.Assets;

/**
 * Chases the player until it gets close enough to cause damage.
 * Ever seen Shawn of the Dead? Good movie, right?
 */
public class ZombieMob extends MeleeMob {
    /**
     * Create a ZombieMob
     * @param parent the round parent
     * @param x      the starting x position
     * @param y      the starting y position
     */
    public ZombieMob (Round parent, float x, float y) {
        super(parent, x, y, 4, Assets.badGuyNormal, 5);
    }
}
