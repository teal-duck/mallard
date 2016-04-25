package com.superduckinvaders.game.entity;


import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.assets.TextureSet;


/**
 * Represents a character in the game.
 */
public abstract class Character extends PhysicsEntity {

	/**
	 * The direction the Character is facing.
	 */
	protected TextureSet.FaceDirection facing = TextureSet.FaceDirection.FRONT;

	/**
	 * The state time for the animation. Set to 0 for not moving.
	 */
	protected float stateTime = 0;

	/**
	 * Current health and the maximum health of this Character.
	 */
	protected int maximumHealth, currentHealth;

	protected float meleeRange = 30f;
	/**
	 * For use when determining player movement direction
	 */
	private final Vector2 reference = new Vector2(0f, -1f);
	private final Vector2 bias = new Vector2(1.1f, 1);

	public static float RANGED_ATTACK_COOLDOWN = 1f;
	public static float MELEE_ATTACK_COOLDOWN = 1f;
	public static float FACE_ATTACK_DIRECTION_DURATION = 0.5f;
	public static float STUNNED_DURATION = 2f;

	/**
	 * An attack timer to maintain the cooldown.
	 */
	protected float meleeAttackTimer = Character.MELEE_ATTACK_COOLDOWN;
	protected float rangedAttackTimer = Character.RANGED_ATTACK_COOLDOWN;

	private float faceAttackTimer = Character.FACE_ATTACK_DIRECTION_DURATION;
	private float stunnedTimer = Character.STUNNED_DURATION;

	/**
	 * The speed of the launched projectiles.
	 */
	public float projectileSpeed = 20f;

	protected short enemyBits = 0;
	protected ArrayList<PhysicsEntity> enemiesInRange;

	private boolean isDemented;

	// How long being demented applies before it wears off
	protected static final float MAX_DEMENTED_TIME = 20;

	protected float dementedTime = 0;


	/**
	 * Initialises this Character. Defaults demented to false.
	 *
	 * @param parent
	 *                the round this Character belongs to
	 * @param x
	 *                the initial x coordinate
	 * @param y
	 *                the initial y coordinate
	 * @param maximumHealth
	 *                the maximum (and initial) health of this Character
	 */
	public Character(Round parent, float x, float y, int maximumHealth) {
		this(parent, x, y, maximumHealth, false);
	}


	/**
	 * Initialises this Character.
	 *
	 * @param parent
	 *                the round this Character belongs to
	 * @param x
	 *                the initial x coordinate
	 * @param y
	 *                the initial y coordinate
	 * @param maximumHealth
	 *                the maximum (and initial) health of this Character
	 * @param demented
	 *                initial state of character's mental health
	 */
	public Character(Round parent, float x, float y, int maximumHealth, boolean demented) {
		super(parent, x, y);
		this.maximumHealth = currentHealth = maximumHealth;
		enemiesInRange = new ArrayList<>();
		// isDemented = demented;
	}


	@Override
	public void createBody(BodyDef.BodyType bodyType, short categoryBits, short maskBits, short groupIndex,
			boolean isSensor) {
		super.createBody(bodyType, categoryBits, maskBits, groupIndex, isSensor);

		CircleShape meleeSensorShape = new CircleShape();
		meleeSensorShape.setRadius(meleeRange / PhysicsEntity.PIXELS_PER_METRE);

		FixtureDef meleeFixtureDef = new FixtureDef();
		meleeFixtureDef.shape = meleeSensorShape;
		meleeFixtureDef.isSensor = true;

		meleeFixtureDef.filter.categoryBits = categoryBits;
		meleeFixtureDef.filter.maskBits = enemyBits;
		meleeFixtureDef.filter.groupIndex = PhysicsEntity.SENSOR_GROUP;

		Fixture meleeFixture = body.createFixture(meleeFixtureDef);
		meleeFixture.setUserData(this);

		meleeSensorShape.dispose();
	}


	/**
	 * Gets the direction the character is facing
	 *
	 * @return the direction this Character is facing
	 */
	public TextureSet.FaceDirection getFacing() {
		return facing;
	}


	/**
	 * Gets the current state of the character's mental health.
	 *
	 * @return true if character is demented
	 */
	public boolean isDemented() {
		return isDemented;
	}


	/**
	 * Gets the current health of this Character.
	 *
	 * @return the current health of this Character
	 */
	public int getCurrentHealth() {
		return currentHealth;
	}


	/**
	 * Gets the maximum health of this Character.
	 *
	 * @return the maximum health of this Character
	 */
	public int getMaximumHealth() {
		return maximumHealth;
	}


	/**
	 * Heals this Character's current health by the specified number of points.
	 *
	 * @param health
	 *                the number of health points to heal
	 */
	public void heal(int health) {
		currentHealth += health;

		if (currentHealth > maximumHealth) {
			currentHealth = maximumHealth;
		}
	}


	/**
	 * Damages this Character's health by the specified number of points.
	 *
	 * @param health
	 *                the number of points to damage
	 */
	public void damage(int health) {
		currentHealth -= health;
		stunnedTimer = 0f;
	}


	public boolean isStunned() {
		return stunnedTimer < Character.STUNNED_DURATION;
	}


	/**
	 * Returns if the character is dead
	 *
	 * @return whether this Character is dead (i.e. its health is 0)
	 */
	public boolean isDead() {
		return currentHealth <= 0;
	}


	/**
	 * Causes this Character to fire a projectile at the specified coordinates.
	 *
	 * @param direction
	 *                the location of the target
	 * @param damage
	 *                how much damage the projectile deals
	 */
	protected void fireAt(Vector2 direction, int damage) {
		Vector2 velocity = direction.setLength(projectileSpeed);
		parent.createProjectile(getCentre(), velocity, damage, this);
	}


	protected void lookDirection(Vector2 direction) {
		float angle = direction.scl(bias).angle(reference);
		int index = (2 + (int) Math.rint(angle / 90f)) % 4;

		// Update Character facing.
		switch (index) {
		case 0:
			facing = TextureSet.FaceDirection.BACK;
			break;
		case 1:
			facing = TextureSet.FaceDirection.RIGHT;
			break;
		case 2:
			facing = TextureSet.FaceDirection.FRONT;
			break;
		case 3:
			facing = TextureSet.FaceDirection.LEFT;
			break;
		}
	}


	/**
	 * Causes this Character to use a melee attack.
	 *
	 * @param direction
	 *                the attack direction.
	 * @param damage
	 *                how much damage the attack deals.
	 * @return whether the attack has occurred.
	 */
	protected boolean meleeAttack(Vector2 direction, int damage) {
		if (isStunned()) {
			return false;
		}

		// if (meleeAttackTimer > MELEE_ATTACK_COOLDOWN && !enemiesInRange.isEmpty()){
		if (meleeAttackTimer > Character.MELEE_ATTACK_COOLDOWN) {
			for (PhysicsEntity entity : enemiesInRange) {
				if (Math.abs(vectorTo(entity.getCentre()).angle(direction)) < 45) {
					if (entity instanceof Character) {
						Character character = (Character) entity;
						character.damage(damage);
						character.setVelocity(direction.cpy().setLength(40f));
						if (isDemented() && (character instanceof Player)) {
							character.becomeDemented();
						}
					} else if (entity instanceof Projectile) {
						Projectile projectile = (Projectile) entity;
						float speed = projectile.getPhysicsVelocity().len();
						Vector2 newVelocity = vectorTo(projectile.getCentre())
								.setLength(speed * 2);
						projectile.setOwner(this);
						projectile.setVelocity(newVelocity);
					}
				}
			}
			meleeAttackTimer = 0f;
			faceAttackTimer = 0f;
			lookDirection(direction.cpy().nor());
			return true;
		}

		return false;
	}


	protected boolean rangedAttack(Vector2 direction, int damage) {
		if (isStunned()) {
			return false;
		}
		if (rangedAttackTimer > Character.RANGED_ATTACK_COOLDOWN) {
			rangedAttackTimer = 0f;
			faceAttackTimer = 0f;
			fireAt(direction, damage);
			lookDirection(direction.cpy().nor());
			return true;
		}
		return false;
	}


	@Override
	public void beginSensorContact(PhysicsEntity other, Contact contact) {
		super.beginSensorContact(other, contact);
		if ((other instanceof Character)
				|| ((other instanceof Projectile) && (((Projectile) other).getOwner() != this))) {
			enemiesInRange.add(other);
		}

	}


	@Override
	public void endSensorContact(PhysicsEntity other, Contact contact) {
		super.endSensorContact(other, contact);
		if (enemiesInRange.contains(other)) {
			enemiesInRange.remove(other);
		}

	}


	/**
	 * Updates the state of this Character.
	 *
	 * @param delta
	 *                how much time has passed since the last update
	 */
	@Override
	public void update(float delta) {
		rangedAttackTimer += delta;
		meleeAttackTimer += delta;

		stunnedTimer += delta;
		faceAttackTimer += delta;
		Vector2 velocity = getVelocity();

		if (!velocity.isZero() && (faceAttackTimer > Character.FACE_ATTACK_DIRECTION_DURATION)) {
			lookDirection(velocity);
		}

		// Update animation state time.
		if (velocity.isZero()) {
			stateTime = 0;
		} else {
			stateTime += delta;
		}

		if (isDead()) {
			removed = true;
		}

		super.update(delta);
	}


	/**
	 * Sets this entity to be demented.
	 */
	public void becomeDemented() {
		isDemented = true;
	}


	/**
	 * Sets this entity to stop being demented.
	 */
	public void stopDemented() {
		isDemented = false;
	}


	/**
	 * Renders the demented icon above the character.
	 *
	 * @param spriteBatch
	 * @param tex
	 */
	protected void dementedRender(SpriteBatch spriteBatch, TextureRegion tex, float xOffset, float yOffset) {
		dementedRender(spriteBatch, tex, xOffset, yOffset, 1, 1);
	}


	/**
	 * @param spriteBatch
	 * @param tex
	 * @param xOffset
	 * @param yOffset
	 */
	protected void dementedRender(SpriteBatch spriteBatch, TextureRegion tex, float xOffset, float yOffset,
			float wScale, float hScale) {
		TextureRegion dem = Assets.dementedIcon;

		float width = dem.getRegionWidth() * wScale;
		float height = dem.getRegionHeight() * hScale;

		float x = getX() + (0.5f * (tex.getRegionWidth() - width)) + xOffset;
		float y = getY() + height + yOffset;

		spriteBatch.draw(dem, x, y, width, height);
	}
}
