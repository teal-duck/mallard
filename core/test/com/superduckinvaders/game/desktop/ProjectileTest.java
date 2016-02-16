package com.superduckinvaders.game.desktop;

import com.badlogic.gdx.math.Vector2;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.desktop.util.LwjglTestRunner;
import com.superduckinvaders.game.entity.PhysicsEntity;
import com.superduckinvaders.game.entity.Projectile;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Test mallard's "unique" implementation of A* search.
 */
@RunWith(LwjglTestRunner.class)
@Ignore // Issues with asset loading :(
public class ProjectileTest {
    private Round round;
    private PhysicsEntity owner;

    @Before
    public void setUp() {
        Assets.load();
        round = mock(Round.class);
        owner = mock(PhysicsEntity.class);
    }

    @Test
    public void StartsAtCorrectPosition() {
        Projectile proj = new Projectile(round, new Vector2(100, 200), new Vector2(10, 20), 0, owner);
        assertEquals(proj.getPosition(), new Vector2(100, 200));
    }
}
