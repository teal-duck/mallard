package com.superduckinvaders.game.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.EnumMap;

/**
 * Represents a standard set of textures for character facing and animation.
 */
public final class TextureSet {

    /**
     * Enum for which way the character is facing.
     */
    public enum FaceDirection { FRONT, BACK, LEFT, RIGHT }

    /**
     * Textures to use when the character isn't moving.
     */
    private EnumMap<FaceDirection, TextureRegion> idleTextures = new EnumMap<FaceDirection, TextureRegion>(FaceDirection.class);

    /**
     * Animations to use when the character is moving.
     */
    private EnumMap<FaceDirection, Animation> movementAnimations = new EnumMap<FaceDirection, Animation>(FaceDirection.class);

    /**
     * Initialises this TextureSet with a single texture used for everything.
     *
     * @param all the texture to use for everything
     */
    public TextureSet(TextureRegion all) {
        this(all, all, all, all);
    }

    /**
     * Initialises this TextureSet with textures for facing. No movement animations will be used.
     *
     * @param front the foward facing texture
     * @param back  the backward facing texture
     * @param left  the left facing texture
     * @param right the right facing texture
     */
    public TextureSet(TextureRegion front, TextureRegion back, TextureRegion left, TextureRegion right) {
        this(
                front, back, left, right,
                new Animation(0, front), new Animation(0, back), new Animation(0, left), new Animation(0, right)
        );
    }

    /**
     * Initilaises this TextureSet with textures for facing and movement animations.
     *
     * @param front       the foward facing texture
     * @param back        the backward facing texture
     * @param left        the left facing texture
     * @param right       the right facing texture
     * @param movingFront the moving forward animation
     * @param movingBack  the moving backward animation
     * @param movingLeft  the moving left animation
     * @param movingRight the moving right animation
     */
    public TextureSet(TextureRegion front, TextureRegion back, TextureRegion left, TextureRegion right,
                      Animation movingFront, Animation movingBack, Animation movingLeft, Animation movingRight) {
        idleTextures.put(FaceDirection.FRONT,  front);
        idleTextures.put(FaceDirection.BACK,    back);
        idleTextures.put(FaceDirection.LEFT,  left);
        idleTextures.put(FaceDirection.RIGHT, right);

        movementAnimations.put(FaceDirection.FRONT,  movingFront);
        movementAnimations.put(FaceDirection.BACK,    movingBack);
        movementAnimations.put(FaceDirection.LEFT,  movingLeft);
        movementAnimations.put(FaceDirection.RIGHT, movingRight);
    }

    /**
     * Gets the representative width of this TextureSet (the front idle texture).
     *
     * @return the width
     */
    public float getWidth() {
        return idleTextures.get(FaceDirection.FRONT).getRegionWidth();
    }

    /**
     * Gets the representative height of this TextureSet (the front idle texture).
     *
     * @return the height
     */
    public float getHeight() {
        return idleTextures.get(FaceDirection.FRONT).getRegionHeight();
    }

    /**
     * Gets the texture for the specified facing at the specified state time. A state time of 0 means not moving; idle.
     *
     * @param facing    the facing
     * @param stateTime the state time
     * @return the appropriate texture
     */
    public TextureRegion getTexture(FaceDirection facing, float stateTime) {
        if (stateTime > 0) {
            return movementAnimations.get(facing).getKeyFrame(stateTime, true);
        } else {
            return idleTextures.get(facing);
        }
    }
}
