
package com.superduckinvaders.game.entity;

import com.superduckinvaders.ai.*;
import com.superduckinvaders.game.round.Tile;

public class Mob extends Character {	

	/**
	 * variable that tracks whether enemy physics/behaviour is
	 * active i.e. whether it's off screen etc.
	 */
	private boolean active = false;

	public boolean getActive(){
		return active;
	}

	/**
	 * mob AI, specified in AI class
	 */
	private AI mobAI;
	
	/**
	 * pointer to all the player projectiles (the ones that hurt the mob)
	 */
	private Projectile[] playerProjectilesPointer;

	public void setAI(AI.AIType newType, Tile[][] mapPointer, Player playerPointer)
	{
		switch (newType)
		{
		case DUMMY:
			mobAI = new DummyAI(mapPointer, playerPointer);
			break;
		case ZOMBIE:
			mobAI = new ZombieAI(mapPointer, playerPointer);
			break;
		case SNIPER:
			mobAI = new SniperAI(mapPointer, playerPointer);
			break;
		case SPREADER:
			mobAI = new SpreaderAI(mapPointer, playerPointer);
			break;	
		}
	}

	public void Update()
	{
		if(active)
		{
			if (doesCollide())
			{
				for(Projectile projectile:playerProjectilesPointer)
				{
					//COLLISION DETECION LOGIC HERE???
				}
			}
			mobAI.execute(this);
		}
		else
		{
			if(onScreen())
			{
				active = true;
			}
		}
	}

	public boolean onScreen()
	{
		//TODO FINISH ME
		return false;
	}

}
