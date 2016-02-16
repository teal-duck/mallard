package com.superduckinvaders.game.entity.mob;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.assets.TextureSet;

public class ZombieMob extends MeleeMob {
    public ZombieMob (Round parent, float x, float y) {
        super(parent, x, y, 4, Assets.badGuyNormal, 5);
    }

}
