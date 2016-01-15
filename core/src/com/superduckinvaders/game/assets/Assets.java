package com.superduckinvaders.game.assets;

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
    
    // Textures for Hearts
    public static TextureRegion heartFull;
    public static TextureRegion heartHalf;
    public static TextureRegion heartEmpty;

    // Animation for explosion.
    public static Animation explosionAnimation;

    // Tile map for level one.
    public static TiledMap levelOneMap;

    // The font for the UI.
    public static BitmapFont font;

    /**
     * Responsible for loading maps.
     */
    private static TmxMapLoader mapLoader = new TmxMapLoader();

    /**
     * Loads all assets.
     */
    public static void load() {
        loadPlayerTextureSets();

        projectile = new TextureRegion(loadTexture("textures/projectile.png"));

        explosionAnimation = loadAnimation("textures/explosion.png", 2, 32, 0.3f);

        levelOneMap = loadTiledMap("maps/map.tmx");

        font = loadFont("font/gamefont.fnt", "font/gamefont.png");

        Texture hearts = loadTexture("textures/hearts.png");
        heartFull = new TextureRegion(hearts, 0, 0, 32, 28);
        heartHalf = new TextureRegion(hearts, 32, 0, 32, 28);
        heartEmpty = new TextureRegion(hearts, 64, 0, 32, 28);
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
        TextureRegion left = new TextureRegion(playerIdle, 28, 0, 14, 18);
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

    /**
     * Loads the texture from the specified file.
     *
     * @param file the file to load from
     * @return the texture
     */
    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    /**
     * Loads the tile map from the specifed file.
     *
     * @param file the file to load from
     * @return the tile map
     */
    public static TiledMap loadTiledMap(String file) {
        return mapLoader.load(file);
    }

    /**
     * Loads the animation from the specified file.
     *
     * @param file          the file to load from
     * @param count         how many frames are in the file
     * @param frameWidth    how wide each frame is in the file
     * @param frameDuration how long each frame should be shown for in seconds
     * @return the animation
     */
    public static Animation loadAnimation(String file, int count, int frameWidth, float frameDuration) {
        Texture texture = loadTexture(file);
        Array<TextureRegion> keyFrames = new Array<TextureRegion>();

        for (int i = 0; i < count; i++) {
            keyFrames.add(new TextureRegion(texture, i * frameWidth, 0, frameWidth, texture.getHeight()));
        }

        return new Animation(frameDuration, keyFrames);
    }

    /**
     * Loads the bitmap font from the specified files.
     *
     * @param fontFile  the file containing information about the glyphs stored on the image file
     * @param imageFile the file containing the actual glyphs
     * @return the bitmap font
     */
    public static BitmapFont loadFont(String fontFile, String imageFile) {
        return new BitmapFont(Gdx.files.internal(fontFile), Gdx.files.internal(imageFile), false);
    }
}
