
package com.superduckinvaders.game.entity.mob;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.ai.AI;
import com.superduckinvaders.game.assets.TextureSet;
import com.superduckinvaders.game.entity.Character;
import com.superduckinvaders.game.entity.PhysicsEntity;
import com.superduckinvaders.game.entity.Player;


/**
 * A baddie. Not a goodie, a baddie. Bad-die.
 */
public class Mob extends Character {

	/**
	 * The texture set to use for this Mob.
	 */
	private TextureSet textureSet;

	/**
	 * AI class for the mob
	 */
	private AI ai;

	/**
	 * speed of the mob in pixels per second
	 */
	private float speed;

	protected Character target;

	private static final double MOB_DEMENTED_CHANCE = 0.00015f;
	private static final float MOB_NEW_DEMENTED_EFFECT_TIME = 5f;
	private int defaultMoveSpeed;
	private float dementedNewEffectTimer = 0;
	private DementedMobBehaviour dementedBehaviour;
	// private float maxDementedTargetSwitchTime = 5;


	/**
	 * Create a new Mob.
	 *
	 * @param parent
	 *                the round parent.
	 * @param x
	 *                the initial x position.
	 * @param y
	 *                the initial y position.
	 * @param health
	 *                the starting health.
	 * @param textureSet
	 *                a TextureSet to use for displaying.
	 * @param speed
	 *                the speed to approach the player.
	 * @param ai
	 *                the AI type to use.
	 * @param demented
	 *                the mob's initial mental state
	 */
	public Mob(Round parent, float x, float y, int health, TextureSet textureSet, int speed, AI ai,
			boolean demented) {
		super(parent, x, y, health, demented);

		meleeRange = 20f;

		this.textureSet = textureSet;
		this.speed = speed;
		defaultMoveSpeed = speed;
		this.ai = ai;

		categoryBits = PhysicsEntity.MOB_BITS;
		enemyBits = PhysicsEntity.PLAYER_BITS | PhysicsEntity.DEMENTED_BITS;
		createDynamicBody(PhysicsEntity.MOB_BITS, (short) (PhysicsEntity.ALL_BITS & (~PhysicsEntity.MOB_BITS)),
				PhysicsEntity.MOB_GROUP, false);

		if (demented) {
			//			categoryBits = PhysicsEntity.DEMENTED_BITS;
			//			enemyBits = PhysicsEntity.PLAYER_BITS | PhysicsEntity.MOB_BITS;
			//			createDynamicBody(PhysicsEntity.DEMENTED_BITS, (short) (PhysicsEntity.ALL_BITS & (~PhysicsEntity.DEMENTED_BITS)), PhysicsEntity.NO_GROUP, false);
			becomeDemented();
		}
		//		} else {
		//			categoryBits = PhysicsEntity.MOB_BITS;
		//			enemyBits = PhysicsEntity.PLAYER_BITS | DEMENTED_BITS;
		//			createDynamicBody(PhysicsEntity.MOB_BITS, (short) (PhysicsEntity.ALL_BITS & (~PhysicsEntity.MOB_BITS)),
		//					PhysicsEntity.NO_GROUP, false);
		//		}

		body.setLinearDamping(20f);

		target = parent.getPlayer();
	}


	/**
	 * Create a new mob, defaulting demented to false
	 *
	 * @param parent
	 *                the round parent.
	 * @param x
	 *                the initial x position.
	 * @param y
	 *                the initial y position.
	 * @param health
	 *                the starting health.
	 * @param textureSet
	 *                a TextureSet to use for displaying.
	 * @param speed
	 *                the speed to approach the player.
	 * @param ai
	 *                the AI type to use.
	 */
	public Mob(Round parent, float x, float y, int health, TextureSet textureSet, int speed, AI ai) {
		this(parent, x, y, health, textureSet, speed, ai, false);
	}


	/**
	 * Replace the AI for this Mob.
	 *
	 * @param ai
	 *                the new AI to use
	 */
	public void setAI(AI ai) {
		this.ai = ai;
	}


	public void setAITarget(Character c) {
		ai.setTarget(c);
		target = c;
	}


	/**
	 * Resets the speed of the mob
	 *
	 * @param speed
	 *                the updated speed
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}


	/**
	 * @return the speed of the mob
	 */
	public float getSpeed() {
		return speed;
	}


	@Override
	public float getWidth() {
		return textureSet.getHeight();
	}


	@Override
	public float getHeight() {
		return textureSet.getHeight();
	}


	@Override
	public void update(float delta) {

		// Low chance of becoming demented each frame.
		// Each mob has roughly 54% chance of becoming demented by 60 seconds.
		if (MathUtils.random() < (float) Mob.MOB_DEMENTED_CHANCE) {
			becomeDemented();
		}
		// If duck is demented and is currently walking north or standing still, do not use AI to move.
		if ((!isDemented()) || (!(dementedBehaviour.equals(DementedMobBehaviour.WALK_NORTH))
				|| (dementedBehaviour.equals(DementedMobBehaviour.STAND_STILL)))) {
			ai.update(this, delta);
		}
		// Chance of spawning a random powerup.
		if (isDead()) {
			Player.Pickup pickup = Player.Pickup.random();
			if (pickup != null) {
				parent.createPickup(getX(), getY(), pickup);
			}
		}

		if (isDemented()) {
			if (dementedTime > Character.MAX_DEMENTED_TIME) {
				stopDemented();
			} else if (dementedNewEffectTimer > Mob.MOB_NEW_DEMENTED_EFFECT_TIME) {
				dementedNewEffectTimer = 0;
				clearDementedEffect();
				newDementedEffect();
			} else {
				dementedNewEffectTimer += delta;
				dementedTime += delta;
				if (dementedBehaviour.equals(DementedMobBehaviour.WALK_NORTH)) {
					applyVelocity(getCentre().sub(new Vector2(0, 500)));
				}
			}
		}
		super.update(delta);
	}


	@Override
	public void becomeDemented() {
		if (isDemented()) {
			// If mob is already demented, reset timer
			dementedTime = 0;
			dementedNewEffectTimer = 0;
		} else {
			super.becomeDemented();
			dementedBehaviour = DementedMobBehaviour.randomBehaviour();
			categoryBits = PhysicsEntity.DEMENTED_BITS;
			enemyBits = PhysicsEntity.PLAYER_BITS | PhysicsEntity.MOB_BITS;
			for (Fixture fix : body.getFixtureList()) {
				Filter filter = fix.getFilterData();
				filter.categoryBits = categoryBits;
				if (fix.isSensor()) {
					filter.maskBits = enemyBits;
				} else {
					filter.maskBits = PhysicsEntity.ALL_BITS & (~PhysicsEntity.DEMENTED_BITS);
				}
				fix.setFilterData(filter);
			}
		}
	}


	public void newDementedEffect() {
		dementedBehaviour = DementedMobBehaviour.randomBehaviour();
		if (dementedBehaviour.equals(DementedMobBehaviour.ATTACK_CLOSEST)) {
			setAITarget(parent.getNearestCharacter(this));
		} else if (dementedBehaviour.equals(DementedMobBehaviour.RUN_AWAY)) {
			setSpeed(-defaultMoveSpeed);
		} else if (dementedBehaviour.equals(DementedMobBehaviour.STAND_STILL)) {
			//			setSpeed(0);
		} else if (dementedBehaviour.equals(DementedMobBehaviour.WALK_NORTH)) {
			// Do nothing here, handled in update().
		}
	}


	public void clearDementedEffect() {
		if (dementedBehaviour.equals(DementedMobBehaviour.ATTACK_CLOSEST)) {
			setAITarget(parent.getPlayer());
		} else if (dementedBehaviour.equals(DementedMobBehaviour.RUN_AWAY)) {
			setSpeed(defaultMoveSpeed);
		} else if (dementedBehaviour.equals(DementedMobBehaviour.STAND_STILL)) {
			setSpeed(defaultMoveSpeed);
		}
	}


	@Override
	public void stopDemented() {
		super.stopDemented();
		clearDementedEffect();
		categoryBits = PhysicsEntity.MOB_BITS;
		enemyBits = PhysicsEntity.PLAYER_BITS | PhysicsEntity.DEMENTED_BITS;
		for (Fixture fix : body.getFixtureList()) {
			Filter filter = fix.getFilterData();
			filter.categoryBits = categoryBits;
			filter.maskBits = enemyBits;
			if (fix.isSensor()) {
				filter.maskBits = enemyBits;
			} else {
				filter.maskBits = PhysicsEntity.ALL_BITS & (~PhysicsEntity.MOB_BITS);
			}
			fix.setFilterData(filter);
		}
	}


	@Override
	public void render(SpriteBatch spriteBatch) {
		TextureRegion tex = textureSet.getTexture(facing, stateTime);
		spriteBatch.draw(textureSet.getTexture(facing, stateTime), getX(), getY());

		if (isDemented()) {
			dementedRender(spriteBatch, tex, 0, 15);
		}
	}


	/**
	 * Move towards a specific point, basically.
	 *
	 * @param destination
	 *                the destination vector.
	 */
	public void applyVelocity(Vector2 destination) {
		Vector2 velocity = destination.sub(getCentre()).nor().scl(getSpeed());
		if (isStunned()) {
			velocity.scl(0.4f);
		}
		setVelocityClamped(velocity);
	}


	public enum DementedMobBehaviour {
		ATTACK_CLOSEST, SHOOT_AIMLESSLY, WALK_NORTH, RUN_AWAY, STAND_STILL;

		public static DementedMobBehaviour randomBehaviour() {
			DementedMobBehaviour[] behaviours = DementedMobBehaviour.values();
			int n = MathUtils.random(behaviours.length - 1);
			return behaviours[n];
		}
	}
}
