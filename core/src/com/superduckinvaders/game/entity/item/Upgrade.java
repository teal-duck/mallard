package com.superduckinvaders.game.entity.item;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.entity.*;

/**
 * Created by olivermcclellan on 16/01/2016.
 */
public class Upgrade extends Item {

    /**
     * The upgrade that this Upgrade gives to the player.
     */
    private Player.Upgrade upgrade;


    public Upgrade(Round parent, float x, float y, Player.Upgrade upgrade) {
        super(parent, x, y, Player.Upgrade.getTextureForUpgrade(upgrade));

        this.upgrade = upgrade;
    }
    
    
    @Override
    public void onCollision(Entity other){
        if (other instanceof Player) {
            ((Player)other).setUpgrade(upgrade);
            removed = true;
        }
    }

    @Override
    public void update(float delta) {
    }
}
