package com.superduckinvaders.game;

import com.badlogic.gdx.audio.Sound;

public class SoundPlayer {
	private SoundPlayer() {}
	
	private static boolean playSounds = false;
	
	public static long play(Sound snd){
		if (playSounds)
			return snd.play();
		else
			return -1;
	}

	public static void mute() {
		playSounds = false;
	}

	public static void unmute() {
		playSounds = true;
	}
	
	public static boolean isMuted() {
		return !playSounds;
	}
	
}
