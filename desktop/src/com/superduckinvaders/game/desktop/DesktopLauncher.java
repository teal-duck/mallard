/**
 * URL for executable: https://github.com/teal-duck/teal-duck/raw/gh-pages/Assessment%204/mallard_a4.jar
 */

package com.superduckinvaders.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.superduckinvaders.game.DuckGame;

/**
 * Desktop launcher for Super Duck Invaders.
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.resizable = true;
		config.title = "SUPER DUCK INVADERS! - Team Mallard";
		new LwjglApplication(new DuckGame(), config);
	}
}
