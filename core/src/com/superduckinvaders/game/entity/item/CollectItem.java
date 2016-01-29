package com.superduckinvaders.game.entity.item;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.entity.*;
import com.superduckinvaders.game.objective.*;
import com.superduckinvaders.game.assets.Assets;


public class CollectItem extends Item {

    public CollectItem(Round parent, float x, float y) {
        super(parent, x, y, Assets.flag);
    }
    
    @Override
    public void onCollision(PhysicsEntity other){
        if (other instanceof Player) {
            removed = true;
        }
    }

    @Override
    public void update(float delta) {
    }
}

