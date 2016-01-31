package com.superduckinvaders.game.entity.item;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.entity.PhysicsEntity;
import com.superduckinvaders.game.entity.Player;


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

