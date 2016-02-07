package com.superduckinvaders.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.assets.TextureSet;

import java.util.Map;
import java.util.EnumMap;

/**
 * Represents the player of the game.
 */
public class Player extends Character {

    /**
     * Player's maximum health.
     */
    public static final int PLAYER_HEALTH = 6;
    /**
     * Player's standard movement speed in pixels per second.
     */
    public static final float PLAYER_SPEED = 16f;
    /**
     * Player's standard attack delay (how many seconds between attacks).
     */
    public static final int PLAYER_ATTACK_DELAY = 1;
    /**
     * How much the Player's score increases should be multiplied by if they have the score multiplier powerup.
     */
    public static final float PLAYER_SCORE_MULTIPLIER = 5f;
    /**
     * How much the Player's speed should be multiplied by if they have the super speed powerup.
     */
    public static final float PLAYER_SUPER_SPEED_MULTIPLIER = 3f;
    /**
     * How much the Player's speed should me multiplied by if they are flying.
     */
    public static final float PLAYER_FLIGHT_SPEED_MULTIPLIER = 2f;
    /**
     * How much the Player's attack rate should be multiplied by if they have the rate of fire powerup.
     */
    public static final float PLAYER_ATTACK_DELAY_MULTIPLIER = 0.2f;
    /**
     * How long the Player can fly for, in seconds.
     */
    public static final float PLAYER_FLIGHT_TIME = 1f;
    /**
     * How long after flying before the Player can fly again, in seconds.
     */
    public static final float PLAYER_FLIGHT_COOLDOWN = 5f;

    public State state = State.DEFAULT;

    /**
     * Player's current score.
     */
    private int points = 0;
    
    
    public EnumMap<Pickup, Float> pickupMap = new EnumMap<Pickup, Float>(Pickup.class);
    
    /**
     * Shows if a player is flying. If less than 0, player is flying for -flyingTimer seconds. If less than PLAYER_FLIGHT_COOLDOWN, flying is on cooldown.
     */
    private float flyingTimer = 5;
    /**
     * How long it has been since the Player last attacked.
     */
    private float attackTimer = 0;

    /**
     * Initialises this Player at the specified coordinates and with the specified initial health.
     *
     * @param parent the round this Player belongs to
     * @param x      the initial x coordinate
     * @param y      the initial y coordinate
     */
    public Player(Round parent, float x, float y) {
        super(parent, x, y, PLAYER_HEALTH);
        createDynamicBody(PLAYER_BITS, ALL_BITS, NO_GROUP, false);
        // body.setLinearDamping(10f);
    }

    /**
     * Increases the Player's score by the specified amount.
     *
     * @param amount the amount to increase the score by
     */
    public void addScore(int amount) {
        points += amount;
    }

    /**
     * Gets the Player's current score.
     *
     * @return the current score
     */
    public int getScore() {
        return points;
    }

    /**
     * Gets the Player's current flying timer.
     *
     * @return the current flying timer
     */
    public float getFlyingTimer() {
        return flyingTimer;
    }

    /**
     * Returns if the player is currently flying
     * @return true if the Player is currently flying, false if not
     */
    public boolean isFlying() {
        return flyingTimer > 0 && Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }
    
    /**
     * @return the width of this Player
     */
    @Override
    public float getWidth() {
        return Assets.playerNormal.getWidth();
    }

    /**
     * @return the height of this Player
     */
    @Override
    public float getHeight() {
        return Assets.playerNormal.getHeight();
    }

    /**
     * Damages the Player, taking into account the possibility of invulnerability.
     *
     * @param health the number of points to damage
     */
    @Override
    public void damage(int health) {
        // Only apply damage if we don't have the invulnerability powerup.
        if (!pickupMap.containsKey(Pickup.INVULNERABLE)) {
            super.damage(health);
        }
    }
    
    public void givePickup(Pickup pickup, float duration){
        pickupMap.put(pickup, duration);
    }
    
    public boolean hasPickup(Pickup pickup){
        return pickupMap.containsKey(pickup);
    }

    /**
     * Updates the state of this Player.
     *
     * @param delta how much time has passed since the last update
     */
    @Override
    public void update(float delta) {
        if (isFlying()){
            state = State.FLYING;
        }
        else if (hasPickup(Pickup.GUN)) {
            state = State.HASGUN;
        }
        else {
            state = State.DEFAULT;
        }


        // Decrement pickup timer.
        for (Map.Entry<Pickup, Float> entry : pickupMap.entrySet()){
            float value = entry.getValue();
            if (value < 0) {
                pickupMap.remove(entry.getKey());
            }
            else {
                entry.setValue(value - delta);
            }
        }

        if (hasPickup(Pickup.HEALTH)) {
            heal(1);
        }
        
        // Update attack timer.
        attackTimer += delta;

        // Left mouse to attack.
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (attackTimer >= PLAYER_ATTACK_DELAY * ( hasPickup(Pickup.RATE_OF_FIRE) ? PLAYER_ATTACK_DELAY_MULTIPLIER : 1)) {
                attackTimer = 0;

                if (hasPickup(Pickup.GUN)) {
                    Vector3 target = parent.unproject(Gdx.input.getX(), Gdx.input.getY());

                    // Face target when firing gun.
                    //facing = directionTo(target.x, target.y);
                    fireAt(new Vector2(target.x, target.y), 1);
                } else {
                    // TODO: tweak melee range
                    //meleeAttack(some_mob, 1);
                }
            }
        }


        // Calculate speed at which to move the player.
        float speed = PLAYER_SPEED * (hasPickup(Pickup.SUPER_SPEED) ? PLAYER_SUPER_SPEED_MULTIPLIER : 1);

        // Left/right movement.
        
        Vector2 targetVelocity = new Vector2();
        
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            targetVelocity.x += -1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            targetVelocity.x += 1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            targetVelocity.y = 1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            targetVelocity.y = -1f;
        }
        
        targetVelocity.setLength(speed);
        
        // Press space to start flying, but only if flying isn't cooling down and we're moving.
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (flyingTimer > 0){
                flyingTimer -= delta;
                targetVelocity.scl(PLAYER_FLIGHT_SPEED_MULTIPLIER);
            }
        }
        else {
            flyingTimer = Math.min((flyingTimer+(delta*0.2f)), PLAYER_FLIGHT_TIME);
        }
        setVelocity(targetVelocity, 4f);
        

        // Update movement.
        super.update(delta);
    }

    /**
     * Renders this Player.
     *
     * @param spriteBatch the sprite batch on which to render
     */
    @Override
    public void render(SpriteBatch spriteBatch) {
        // Use the right texture set.
//        TextureSet textureSet = isFlying() ? Assets.playerFlying : ( hasPickup(Pickup.GUN) ? Assets.playerGun : Assets.playerNormal);
        TextureSet textureSet = state.getTextureSet();

        Vector2 pos = getPosition();
        spriteBatch.draw(textureSet.getTexture(facing, stateTime), pos.x, pos.y);
    }

    public enum State {
        DEFAULT    (Assets.playerNormal),
        HASGUN     (Assets.playerGun),
        HASSABER   (Assets.playerGun), // for now, we don't have all the saber assets!
        FLYING     (Assets.playerFlying);

        private final TextureSet textureSet;

        State (TextureSet textureSet){
            this.textureSet = textureSet;
        }

        public TextureSet getTextureSet(){
            return this.textureSet;
        }
    }

    /**
     * Available pickups.
     */
    public enum Pickup {
        GUN               (Assets.floorItemGun         ),
        LIGHTSABER        (Assets.floorItemSaber       ),
        SCORE_MULTIPLIER  (Assets.floorItemScore       ),
        SUPER_SPEED       (Assets.floorItemSpeed       ),
        RATE_OF_FIRE      (Assets.floorItemFireRate    ),
        HEALTH            (Assets.floorItemHeart       ),
        INVULNERABLE      (Assets.floorItemInvulnerable);

    
        private final TextureRegion texture;
        
        Pickup(TextureRegion texture){
            this.texture = texture;
        }
        
        public TextureRegion getTexture() {
            return texture;
        }

        public static Pickup random(){
            float random = MathUtils.random();
            Pickup pickup = null;

            if (random < 0.05) {
                pickup = Player.Pickup.SCORE_MULTIPLIER;
            } else if (random >= 0.05 && random < 0.1) {
                pickup = Player.Pickup.INVULNERABLE;
            } else if (random >= 0.1 && random < 0.15) {
                pickup = Player.Pickup.SUPER_SPEED;
            } else if (random >= 0.15 && random < 0.2) {
                pickup = Player.Pickup.RATE_OF_FIRE;
            }

            return pickup;
        }
    }
    
}
