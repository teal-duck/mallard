package com.superduckinvaders.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.superduckinvaders.game.util.LwjglTestRunner;
import com.superduckinvaders.game.entity.Particle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Test the Character class's special methods.
 */
@RunWith(LwjglTestRunner.class)
public class ParticleTest {
    private Round round;
    private Animation anim;

    @Before
    public void setUp() {
        round = mock(Round.class);
        anim = mock(Animation.class);
    }

    @Test
    public void NotRemovedBeforeDuration() {
        Particle pt = new Particle(round, 0f, 0f, 0.6f, anim);
        pt.update(0.5f);
        assertFalse(pt.isRemoved());
    }

    @Test
    public void RemovedAfterDuration() {
        Particle pt = new Particle(round, 0f, 0f, 0.6f, anim);
        pt.update(0.7f);
        assertTrue(pt.isRemoved());
    }
}
