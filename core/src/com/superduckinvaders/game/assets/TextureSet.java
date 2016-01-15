package com.superduckinvaders.game.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Represents a standard set of textures for character facing and animation.
 */
public final class TextureSet {

    public static final int FACING_FRONT = 0;
    public static final int FACING_BACK = 1;
    public static final int FACING_LEFT = 2;
    public static final int FACING_RIGHT = 3;

    private TextureRegion[] idleTextures = new TextureRegion[4];

    private Animation[] movementAnimations = new Animation[4];

    public TextureSet(TextureRegion front) {
        this(front, front, front, front);
    }

    public TextureSet(TextureRegion front, TextureRegion back, TextureRegion left, TextureRegion right) {
        this(front, back, left, right, new Animation(0, front),
                new Animation(0, back), new Animation(0, left), new Animation(0, right));
    }

    public TextureSet(TextureRegion front, TextureRegion back, TextureRegion left, TextureRegion right,
                      Animation movingFront, Animation movingBack, Animation movingLeft, Animation movingRight) {
        idleTextures[FACING_FRONT] = front;
        idleTextures[FACING_BACK] = back;
        idleTextures[FACING_LEFT] = left;
        idleTextures[FACING_RIGHT] = right;

        movementAnimations[FACING_FRONT] = movingFront;
        movementAnimations[FACING_BACK] = movingBack;
        movementAnimations[FACING_LEFT] = movingLeft;
        movementAnimations[FACING_RIGHT] = movingRight;
    }

    public int getWidth() {
        return idleTextures[0].getRegionWidth();
    }

    public int getHeight() {
        return idleTextures[0].getRegionHeight();
    }

    public TextureRegion getTexture(int facing, float stateTime) {
        if (stateTime > 0) {
            return movementAnimations[facing].getKeyFrame(stateTime, true);
        } else {
            return idleTextures[facing];
        }
    }
}
