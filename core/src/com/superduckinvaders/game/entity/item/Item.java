package com.superduckinvaders.game.entity.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.superduckinvaders.game.entity.Entity;
import com.superduckinvaders.game.round.Round;

public class Item extends Entity {

    /**
     * The texture for this Item.
     */
    protected TextureRegion texture;

    // TODO: finish me
    public Item(Round parent, double x, double y, TextureRegion texture) {
        super(parent, x, y);

        this.texture = texture;
    }

    @Override
    public int getWidth() {
        return texture.getRegionWidth();
    }

    @Override
    public int getHeight() {
        return texture.getRegionHeight();
    }

    @Override
    public void update(float delta) {
        // Don't do anything...yet.
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, (int) x, (int) y);
    }

}
