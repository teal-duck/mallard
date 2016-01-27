package com.superduckinvaders.game.entity;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.TextureSet;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Obstacle extends Entity {

    public Obstacle(Round parent, float x, float y, float width, float height) {
        super(parent, x, y);
        this.width = width;
        this.height = height;
        createBody(BodyDef.BodyType.StaticBody, WORLD_BITS);
    }
    
    @Override
    public void render(SpriteBatch spriteBatch) {
        // spriteBatch.draw(textureSet.getTexture(facing, stateTime), getX(), getY());
    }

}
