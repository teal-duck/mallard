package com.superduckinvaders.game.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.superduckinvaders.game.round.Round;

public class Particles extends Entity {

	/**
	 * How long this particle will remain on the screen.
	 */
	private double initialDuration, duration;

	/**
	 * The animation to use for this Particle.
	 */
	private Animation animation;

	public Particles(Round parent, double x, double y, double duration, Animation animation) {
		super(parent, x, y);

		this.initialDuration = this.duration = duration;
		this.animation = animation;
	}

	@Override
	public void update(float delta) {
		// Do not call super() as particles don't move or collide.
		duration -= delta;

		if (duration <= 0) {
			removed = true;
		}
	}

	@Override
	public int getWidth() {
		return animation.getKeyFrame(0).getRegionWidth();
	}

	@Override
	public int getHeight() {
		return animation.getKeyFrame(0).getRegionHeight();
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		spriteBatch.draw(animation.getKeyFrame((float) (initialDuration - duration)), (int) x, (int) y);
	}
}