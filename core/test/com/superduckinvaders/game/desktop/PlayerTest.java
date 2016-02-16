package com.superduckinvaders.game.desktop;

import com.badlogic.gdx.math.Vector2;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.desktop.util.LwjglTestRunner;
import com.superduckinvaders.game.entity.Player;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Test the Player's methods.
 */
@RunWith(LwjglTestRunner.class)
@Ignore // FIXME: java.lang.UnsatisfiedLinkError :(
public class PlayerTest {
    private Player player;

    @Before
    public void setUp() {
        Player player = new Player(mock(Round.class), 10, 15);
    }

    @Test
    public void StartsAtCorrectPosition() {
        Player positionedPlayer = new Player(mock(Round.class), 10, 15);
        assertEquals(positionedPlayer.getPosition(), new Vector2(10, 15));
    }

    @Test
    public void ScoreStartsAtZero() {
        assertEquals(player.getScore(), 0);
    }

    @Test
    public void CanAddScore() {
        player.addScore(10);
        player.addScore(15);
        assertEquals(player.getScore(), 25);
    }

    @Test
    public void CanAddPickup() {
        player.givePickup(Player.Pickup.GUN, 5f);
        assertTrue(player.hasPickup(Player.Pickup.GUN));
    }

    public void ScoreMultiplierWorks() {
        player.givePickup(Player.Pickup.SCORE_MULTIPLIER, 5f);
        player.addScore(10);
        assertEquals(player.getScore(), 50);
    }

    @Test
    public void CannotBeDamagedWhenInvulnerable() {
        int startHealth = player.getCurrentHealth();
        player.givePickup(Player.Pickup.INVULNERABLE, 5f);
        player.damage(10);
        assertEquals(player.getCurrentHealth(), startHealth);
    }
}
