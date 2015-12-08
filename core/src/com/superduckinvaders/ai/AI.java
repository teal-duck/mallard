package com.superduckinvaders.ai;

import com.superduckinvaders.game.entity.Mob;
import com.superduckinvaders.game.round.Tile;
import com.superduckinvaders.game.entity.Player;

public abstract class AI {
	
	public static enum AIType {
		DUMMY, ZOMBIE, SPREADER, SNIPER
	}
	
	protected Tile[][] mapPointer;//possible pointing error!!!
	protected Player playerPointer;
	
	public AI(Tile[][] tiles, Player playerPointer)
	{
		mapPointer = tiles;
		this.playerPointer = playerPointer;
	}
	
	/**
	 * checks whether the character is still active
	 * left to AI as some AI may still be active off screen 
	 * (i.e. zombie once active should never deactivate)
	 * 
	 * @param mob - pointer to the Mob
	 * @return whether character is still active 
	 */
	public abstract boolean StillActive(Mob mob);
	
	/**
	 * execute the AI on a per-frame basis
	 * @param mob pointer to the Mob
	 */
	public abstract void execute(Mob mob);
}
