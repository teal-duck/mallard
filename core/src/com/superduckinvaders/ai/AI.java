package com.superduckinvaders.ai;

import com.superduckinvaders.game.entity.Character;
import com.superduckinvaders.game.round.Tile;
import com.superduckinvaders.game.entity.Player;

public abstract class AI {
	
	public static enum AIType {
		ZOMBIE, SPREADER, SNIPER
	}
	
	Tile[][] mapPointer;//possible pointing error!!!
	Player playerPointer;
	
	public AI(Tile[][] tiles, Player playerPointer)
	{
		mapPointer = tiles;
		this.playerPointer = playerPointer;
	}
	
	public boolean StillActive(Character character)
	{
		//TODO finish me
		return false;
	}
	
	public abstract void execute(Character character);
}
