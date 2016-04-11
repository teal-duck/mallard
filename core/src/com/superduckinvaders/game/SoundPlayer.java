package com.superduckinvaders.game;


import com.badlogic.gdx.audio.Sound;


public class SoundPlayer {
	private SoundPlayer() {
	}


	private static boolean playSounds = false;


	public static long play(Sound snd) {
		if (SoundPlayer.playSounds) {
			return snd.play();
		} else {
			return -1;
		}
	}


	public static void mute() {
		SoundPlayer.playSounds = false;
	}


	public static void unmute() {
		SoundPlayer.playSounds = true;
	}


	public static boolean isMuted() {
		return !SoundPlayer.playSounds;
	}

}
