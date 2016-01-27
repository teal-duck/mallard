package com.superduckinvaders.game.entity.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.entity.Entity;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

public class Item extends Entity {

    /**
     * The texture for this Item.
     */
    protected TextureRegion texture;

    // TODO: finish me
    public Item(Round parent, float x, float y, TextureRegion texture) {
        super(parent, x, y);
        this.texture = texture;
        createStaticBody(WORLD_BITS, ALL_BITS, NO_GROUP, true);
    }

    @Override
    public float getWidth() {
        return texture.getRegionWidth();
    }

    @Override
    public float getHeight() {
        return texture.getRegionHeight();
    }

    @Override
    public void update(float delta) {
        // Don't do anything...yet.
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, getX(), getY());
    }

}
