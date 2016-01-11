package com.superduckinvaders.game.ai;

import com.superduckinvaders.game.entity.Mob;
import com.superduckinvaders.game.entity.Player;
import com.superduckinvaders.game.round.Tile;

public class DummyAI extends AI {

	public DummyAI(Tile[][] tiles, Player playerPointer) {
		super(tiles, playerPointer);
	}

	@Override
	public void execute(Mob mob) {
	}
	
	@Override
	public boolean StillActive(Mob mob){
		//TODO Finish
		return true;
	}

}
