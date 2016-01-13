package com.superduckinvaders.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;

/**
 * Responsible for loading game assets.
 */
public class Assets {

    // Player idle textures.
    public static Texture playerIdle;
    public static TextureRegion playerFront;
    public static TextureRegion playerBack;
    public static TextureRegion playerLeft;
    public static TextureRegion playerRight;

    // Player walking animations.
    public static Animation playerWalkingFront;
    public static Animation playerWalkingBack;
    public static Animation playerWalkingLeft;
    public static Animation playerWalkingRight;

    public static TextureSet playerNormal;

    // Texture for Projectile.
    public static TextureRegion projectile;

    // Tile map for level one.
    public static TiledMap levelOneMap;
    
    public static BitmapFont font;

    /**
     * Responsible for loading maps.
     */
    private static TmxMapLoader mapLoader = new TmxMapLoader();

    public static void load() {
        playerIdle = loadTexture("textures/player_idle.png");

        playerFront = new TextureRegion(playerIdle, 0, 0, 43, 64);
        playerBack = new TextureRegion(playerIdle, 43, 0, 43, 64);
        playerLeft = new TextureRegion(playerIdle, 86, 0, 49, 64);
        playerRight = new TextureRegion(playerIdle, 135, 0, 49, 64);

        playerWalkingFront = loadAnimation("textures/player_walking_front.png", 2, 42, 0.2f);
        playerWalkingBack = loadAnimation("textures/player_walking_back.png", 2, 42, 0.2f);
        playerWalkingLeft = loadAnimation("textures/player_walking_left.png", 2, 50, 0.2f);
        playerWalkingRight = loadAnimation("textures/player_walking_right.png", 2, 50, 0.2f);

        playerNormal = new TextureSet(playerFront, playerBack, playerLeft, playerRight, playerWalkingFront, playerWalkingBack, playerWalkingLeft, playerWalkingRight);

        projectile = new TextureRegion(loadTexture("textures/projectile.png"));

        levelOneMap = loadTiledMap("maps/map.tmx");
        
        font = new BitmapFont(Gdx.files.internal("font/gamefont.fnt"), Gdx.files.internal("font/gamefont.png"), false);
    }

    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static TiledMap loadTiledMap(String file) {
        return mapLoader.load(file);
    }

    public static Animation loadAnimation(String file, int count, int frameWidth, float frameDuration) {
        Texture texture = loadTexture(file);
        Array<TextureRegion> keyFrames = new Array<TextureRegion>();

        for (int i = 0; i < count; i++) {
            keyFrames.add(new TextureRegion(texture, i * frameWidth, 0, frameWidth, texture.getHeight()));
        }

        return new Animation(frameDuration, keyFrames);
    }
}
