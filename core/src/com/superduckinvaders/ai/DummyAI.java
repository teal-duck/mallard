package com.superduckinvaders.ai;

import com.superduckinvaders.game.entity.Character;
import com.superduckinvaders.game.entity.Player;
import com.superduckinvaders.game.round.Tile;

public class DummyAI extends AI {

	public DummyAI(Tile[][] tiles, Player playerPointer) {
		super(tiles, playerPointer);
	}

	@Override
	public void execute(Character character) {
	}

}
