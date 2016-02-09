package com.superduckinvaders.game.desktop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.desktop.util.LwjglTestRunner;
import com.superduckinvaders.game.entity.Character;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test the Character class's special methods.
 */
@RunWith(LwjglTestRunner.class)
public class CharacterTest {
    private class MyCharacter extends Character {
        MyCharacter(Round round, int x, int y, int maxHealth) {
            super(round, x, y, maxHealth);
        }

        @Override
        public void render(SpriteBatch spriteBatch) {}
    }

    private Round round;

    @Before
    public void setUp() {
        round = mock(Round.class);
    }

    @Test
    public void CanAccessMaxHealth() {
        MyCharacter ch = new MyCharacter(round, 10, 10, 50);
        assertEquals(ch.getCurrentHealth(), 50);
        assertEquals(ch.getMaximumHealth(), 50);
    }

    @Test
    public void CanBeDamaged() {
        MyCharacter ch = new MyCharacter(round, 10, 10, 50);
        ch.damage(15);
        assertEquals(ch.getCurrentHealth(), 35);
    }

    @Test
    public void CanBeKilled() {
        MyCharacter ch = new MyCharacter(round, 10, 10, 50);
        ch.damage(55);
        assertTrue(ch.isDead());
    }

    @Test
    public void IsHealable() {
        MyCharacter ch = new MyCharacter(round, 10, 10, 50);
        ch.damage(30);
        ch.heal(22);
        assertEquals(ch.getCurrentHealth(), 42);
    }

    @Test
    public void CannotBeHealedPastMax() {
        MyCharacter ch = new MyCharacter(round, 10, 10, 50);
        ch.damage(10);
        ch.heal(25);
        assertEquals(ch.getCurrentHealth(), ch.getMaximumHealth());
    }
}
