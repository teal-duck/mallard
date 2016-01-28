package com.superduckinvaders.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.TextureSet;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents an object in the game.
 */
public abstract class Entity {
    public static final short WORLD_BITS = 0x1;
    public static final short PLAYER_BITS   = 0x2;
    public static final short MOB_BITS   = 0x4;
    public static final short PROJECTILE_BITS   = 0x8;
    public static final short ALL_BITS   = WORLD_BITS | PLAYER_BITS | MOB_BITS | PROJECTILE_BITS;
    public static final short NO_GROUP  = 0;
    public static final short MOB_GROUP  = -1;

    /**
     * The round that this Entity is in.
     */
    protected Round parent;

    /**
     * The x and y coordinates of this Entity.
     */
    protected float x, y;
    protected float width, height;
    public Body body;
    public static final float METRES_PER_PIXEL = 1/16f;
    public static final float PIXELS_PER_METRE = 1/METRES_PER_PIXEL;

    /**
     * Whether or not to remove this Entity on the next frame.
     */
    protected boolean removed = false;

    /**
     * Initialises this Entity with zero initial coordinates.
     *
     * @param parent the round this Entity belongs to
     */
    public Entity(Round parent) {
        this(parent, 0, 0);
    }

    /**
     * Initialises this Entity with the specified initial coordinates.
     *
     * @param parent the round this Entity belongs to
     * @param x      the initial x coordinate
     * @param y      the initial y coordinate
     */
    public Entity(Round parent, Vector2 pos) {
        this(parent, pos.x, pos.y);
    
    }
    public Entity(Round parent, float x, float y) {
        this.parent = parent;
        this.x = x;
        this.y = y;
    }
    
    public void createStaticBody(short categoryBits, short maskBits, short groupIndex, boolean isSensor){
        createBody(BodyDef.BodyType.StaticBody, categoryBits, maskBits, groupIndex, isSensor);
    }
    public void createDynamicBody(short categoryBits, short maskBits, short groupIndex, boolean isSensor){
        createBody(BodyDef.BodyType.DynamicBody, categoryBits, maskBits, groupIndex, isSensor);
    }
    public void createBody(BodyDef.BodyType bodyType){
        createBody(bodyType, WORLD_BITS, WORLD_BITS, NO_GROUP, false);
    }
    public void createBody(BodyDef.BodyType bodyType, short categoryBits, short maskBits, short groupIndex, boolean isSensor){
        float width = getWidth();
        float height = getHeight();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        // bodyDef.linearDamping = 20f;
        bodyDef.position.set((x+(width/2))*METRES_PER_PIXEL,
                             (y+(height/2))*METRES_PER_PIXEL);

        PolygonShape boundingBox = new PolygonShape();
        boundingBox.setAsBox(((width/2))*METRES_PER_PIXEL,
                             ((height/2))*METRES_PER_PIXEL);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boundingBox;
        fixtureDef.isSensor = isSensor;
        
        fixtureDef.filter.categoryBits = categoryBits;
        fixtureDef.filter.maskBits = maskBits;
        fixtureDef.filter.groupIndex = groupIndex;

        body = parent.world.createBody(bodyDef);
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        body.setUserData(this);
        boundingBox.dispose();
    }
    

    /**
     * Returns the x coordinate of the entity
     * @return the x coordinate of this Entity
     */
    public float getX() {
        return getPosition().x;
    }

    /**
     * Returns the y coordinate of the entity
     * @return the y coordinate of this Entity
     */
    public float getY() {
        return getPosition().y;
    }
    
    public Vector2 getPosition() {
        return getCentre()
        .sub(getWidth()/2f, getHeight()/2f);
    }
    
    public Vector2 getCentre(){
        return body.getPosition().scl(PIXELS_PER_METRE);
    }

    /**
     * Returns the x velocity of the entity
     * @return the x velocity of this MobileEntity in pixels per second
     */
    public float getVelocityX() {
        return getVelocity().x;
    }

    /**
     * Returns the y velocity of the entity
     * @return the y coordinate of this MobileEntity in pixels per second
     */
    public float getVelocityY() {
        return getVelocity().y;
    }
    
    public Vector2 getVelocity() {
        return body.getLinearVelocity().scl(PIXELS_PER_METRE);
    }
    
    public void setVelocity(Vector2 targetVelocity) {
        setVelocity(targetVelocity, 0f);
    }
    public void setVelocityClamped(Vector2 targetVelocity) {
        setVelocity(targetVelocity, 4f);
    }
    public void setVelocity(Vector2 targetVelocity, float limit) {
        Vector2 deltaVelocity = targetVelocity.sub(body.getLinearVelocity());
        if (limit>0){
            deltaVelocity.clamp(0, limit);
        }
        Vector2 impulse = deltaVelocity.scl(body.getMass());
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
    }

    /**
     * Returns the distance between this Entity and the specified coordinates.
     *
     * @param x the x coordinate to compare with
     * @param y the y coordinate to compare with
     * @return the distance between this Entity and the coordinates, in pixels
     */
    public float distanceTo(Vector2 dest) {
        return vectorTo(dest).len();
    }
    public float distanceTo(float x, float y) {
        return distanceTo(new Vector2(x, y));
    }
    
    public Vector2 vectorTo(Vector2 dest){
        return new Vector2(dest).sub(getPosition());
    }

    /**
     * Returns the angle between this Entity and the specified coordinates.
     *
     * @param x the x coordinate to compare with
     * @param y the y coordinate to compare with
     * @return the angle between this Entity and the coordinates, in radians
     */
     
    public float angleTo(Vector2 dest){
        return vectorTo(dest).angleRad();
    }
    // public float angleTo(float x, float y) {
        // return (float)Math.atan2(y - getY(), x - getX());
    // }
    public float angleTo(float x, float y) {
        return angleTo(new Vector2(x, y));
    }

    /**
     * Returns the direction to the specified coordinates from this Entity (one of the FACING_ constants in TexutreSet).
     *
     * @param x the x coordinate to compare with
     * @param y the y coordinate to compare with
     * @return the direction the coordinates are in relative to this Entity
     */
    public int directionTo(float x, float y) {
        float angle = angleTo(x, y);

        if (angle < Math.PI * 3 / 4 && angle >= Math.PI / 4) {
            return TextureSet.FACING_BACK;
        } else if (angle < Math.PI / 4 && angle >= -Math.PI / 4) {
            return TextureSet.FACING_RIGHT;
        } else if (angle < -Math.PI / 4 && angle >= -Math.PI * 3 / 4) {
            return TextureSet.FACING_FRONT;
        } else {
            return TextureSet.FACING_LEFT;
        }
    }

    /**
     * Returns the width of the entity
     * @return the width of this Entity
     */
    public float getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the entity
     * @return the height of this Entity
     */
    public float getHeight() {
        return this.height;
    }
    
    public Vector2 getSize() {
        return new Vector2(getWidth(), getHeight());
    }

    /**
     * Returns if this entity should be removed
     * @return whether this Entity has been removed
     */
    public boolean isRemoved() {
        return removed;
    }

    /**
     * Ensures that this MobileEntity stays within the map area.
     */
    protected void checkBounds() {
    }
    
    public void onCollision(Entity other) {
    }
    
    /**
     * Updates the state of this Entity.
     *
     * @param delta how much time has passed since the last update
     */
    public void update(float delta) {
    }

    /**
     * Renders this Entity.
     *
     * @param spriteBatch the sprite batch on which to render
     */
    public abstract void render(SpriteBatch spriteBatch);
}
