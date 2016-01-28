package com.superduckinvaders.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.ai.*;
import com.superduckinvaders.game.assets.TextureSet;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.*;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;



public class MeleeMob extends Mob {
    public MeleeMob(Round parent, float x, float y, int health, TextureSet textureSet, int speed, AI ai) {
        super(parent, x, y, health, textureSet, speed, ai);
    }
    public MeleeMob(Round parent, float x, float y, int health, TextureSet textureSet, int speed) {
        super(parent, x, y, health, textureSet, speed, new PathfindingAI(parent, 0));
    }
    
    @Override
    public void onCollision(Entity other){
        if (other instanceof Player){
            Player player = (Player)other;
            meleeAttack(player, 1);
            
            Vector2 knockback = player.getPosition().sub(getPosition()).setLength(30f);
            player.setVelocity(knockback);
            setVelocity(knockback.scl(-1f));
        }
    }
}
