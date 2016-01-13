package com.superduckinvaders.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.superduckinvaders.game.graphics.Assets;
import com.superduckinvaders.game.round.Round;

public class Item extends Entity {
	
    public Item(Round parent, double x, double y, int ID) {
        super(parent, x, y);
    }

    @Override
    public int getWidth() {
		return Assets.projectile.getRegionWidth();
    }

    @Override
    public int getHeight() {
		return Assets.projectile.getRegionHeight();
    }

    @Override
    public void update(float delta) {
        if(parent.getPlayer().intersects(x, y, this.getWidth(), this.getHeight())) {
        	parent.getPlayer().setPowerup(Player.Powerup.SUPER_SPEED, 5);
        	removed = true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(Assets.projectile, (int) x, (int) y);
    }
	
}
