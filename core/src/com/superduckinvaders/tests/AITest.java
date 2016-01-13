/*package com.superduckinvaders.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.superduckinvaders.game.ai.AI;
import com.superduckinvaders.game.graphics.Sprite;
import com.superduckinvaders.game.round.Tile;
import com.superduckinvaders.game.entity.Mob;
import com.superduckinvaders.game.entity.Player;


public class AITest {
	
	public static class TestScenario
	{
		Player player;
		Mob mob;
		Tile[][] map;
		
		public TestScenario(String[] mapData)
		{
			int[] enemyCoord =  new int[2];
			int[] playerCoord = new int[2]; 
			
			map = new Tile[mapData.length][mapData[0].length()];
			
			//set up placeholder map class, according to the mapData above
			for (int i=0; i<mapData.length; i++)
			{
				for(int j=0; j<mapData[i].length(); j++)
				{
					switch (mapData[i].charAt(j))
					{
					case 'o': 	map[i][j] = new Tile(false, Sprite.VOID);
								break;
					case 'x':	map[i][j] = new Tile(true, Sprite.VOID);
								break;
					case 'g':	map[i][j] = new Tile(true, Sprite.VOID);
								enemyCoord[0]=i;
								enemyCoord[1]=j;
								break;
					case 'd':	map[i][j] = new Tile(true, Sprite.VOID);
								playerCoord[0]=i;
								playerCoord[1]=j;
								break;
					}
				}
			}
			
			player = new Player();
			player.move(playerCoord[0], playerCoord[1]);
			
			mob = new Mob();
			mob.move(enemyCoord[0], enemyCoord[1]);
		}
	}
	
	@Test
	public void pathFindingTest1() {
		String[] mapData = new String[5];
		mapData[0]="oooog"; 
		mapData[1]="ooooo";
		mapData[2]="ooooo";
		mapData[3]="ooooo";
		mapData[4]="doooo";
		
		TestScenario test = new TestScenario(mapData);
		
		assertNotNull(test.map);
		assertNotNull(test.player);
		assertNotNull(test.mob);
		
		test.mob.setAI(AI.AIType.ZOMBIE, test.map, test.player);
		test.mob.update();
		
		int[] destCoord = {test.mob.getDestX(), test.mob.getDestY()};
		int[] expectedCoord = {4,4};
		
		assertArrayEquals(expectedCoord, destCoord);		
	}

}
*/