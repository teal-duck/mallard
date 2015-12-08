package com.superduckinvaders.ai;

import com.superduckinvaders.game.entity.Mob;
import com.superduckinvaders.game.entity.Player;
import com.superduckinvaders.game.round.Tile;

public class SpreaderAI extends AI {
	
	public SpreaderAI(Tile[][] tiles, Player playerPointer)
	{
		super(tiles, playerPointer);
	}

	@Override
	public void execute(Mob mob) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean StillActive(Mob mob){
		//TODO Finish
		return true;
	}

}
