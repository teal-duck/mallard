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

    // Player texture sets for normal and flying.
    public static TextureSet playerNormal, playerFlying;

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
        loadPlayerTextureSets();

        projectile = new TextureRegion(loadTexture("textures/projectile.png"));

        levelOneMap = loadTiledMap("maps/map.tmx");
        
        font = new BitmapFont(Gdx.files.internal("font/gamefont.fnt"), Gdx.files.internal("font/gamefont.png"), false);
    }

    /**
     * Loads assets relating to the player in the normal state.
     * If you change the player texture size, be sure to change the values here.
     */
    private static void loadPlayerTextureSets() {
        // Load idle texture map.
        Texture playerIdle = loadTexture("textures/player_idle.png");

        // Cut idle textures from texture map.
        TextureRegion front = new TextureRegion(playerIdle, 0, 0, 14, 18);
        TextureRegion back = new TextureRegion(playerIdle, 14, 0, 14, 18);
        TextureRegion left = new TextureRegion(playerIdle, 28, 0,14, 18);
        TextureRegion right = new TextureRegion(playerIdle, 42, 0, 14, 18);

        // Load walking animations.
        Animation walkingFront = loadAnimation("textures/player_walking_front.png", 4, 12, 0.2f);
        Animation walkingBack = loadAnimation("textures/player_walking_back.png", 4, 12, 0.2f);
        Animation walkingLeft = loadAnimation("textures/player_walking_left.png", 4, 14, 0.2f);
        Animation walkingRight = loadAnimation("textures/player_walking_right.png", 4, 14, 0.2f);

        // Load flying animations.
        Animation flyingFront = loadAnimation("textures/player_flying_front.png", 2, 18, 0.2f);
        Animation flyingBack = loadAnimation("textures/player_flying_back.png", 2, 18, 0.2f);
        Animation flyingLeft = loadAnimation("textures/player_flying_left.png", 2, 21, 0.2f);
        Animation flyingRight = loadAnimation("textures/player_flying_right.png", 2, 21, 0.2f);

        playerNormal = new TextureSet(front, back, left, right, walkingFront, walkingBack, walkingLeft, walkingRight);
        playerFlying = new TextureSet(front, back, left, right, flyingFront, flyingBack, flyingLeft, flyingRight);
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
