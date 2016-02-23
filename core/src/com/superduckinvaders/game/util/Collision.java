package com.superduckinvaders.game.util;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;


/**
 * Collision utilities.
 */
public class Collision {

	public abstract static class Query implements QueryCallback {
		public boolean result = false;
		public World world;
		public short maskBits;


		public Query(World world, short maskBits) {
			this.maskBits = maskBits;
			this.world = world;
		}


		public abstract boolean query();


		@Override
		public abstract boolean reportFixture(Fixture fixture);

	}


	public static class QueryPoint extends Query {
		public Vector2 p;


		public QueryPoint(World world, Vector2 p, short maskBits) {
			super(world, maskBits);
			this.p = p;
		}


		@Override
		public boolean query() {
			world.QueryAABB(this, p.x, p.y, p.x + 1, p.y + 1);
			return result;
		}


		@Override
		public boolean reportFixture(Fixture fixture) {
			if (((fixture.getFilterData().categoryBits & maskBits) != 0) && fixture.testPoint(p)) {
				result = true; // we collided
				return false; // ends the query
			}
			return true; // keep searching
		}
	}


	public static class QueryArea extends Query {
		Vector2 p1;
		Vector2 p2;


		public QueryArea(World world, Vector2 pos, Vector2 size, short maskBits) {
			super(world, maskBits);
			p1 = pos;
			p2 = size.add(pos);
		}


		@Override
		public boolean query() {
			world.QueryAABB(this, p1.x, p1.y, p2.x, p2.y);
			return result;
		}


		@Override
		public boolean reportFixture(Fixture fixture) {
			// TODO: check fixture categoryBits
			result = true; // AABB gave us ANY fixture, BB overlaps.
			return false;
		}
	}
}
