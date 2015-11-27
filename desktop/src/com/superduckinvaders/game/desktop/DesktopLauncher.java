package com.superduckinvaders.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.superduckinvaders.game.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.title = "SUPER DUCK INVADERS! - v0.01";
		config.foregroundFPS = 60;
		new LwjglApplication(new Game(), config);
	}
}