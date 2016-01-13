package com.superduckinvaders.game.round;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.superduckinvaders.game.DuckGame;
import com.superduckinvaders.game.entity.Entity;
import com.superduckinvaders.game.entity.Item;
import com.superduckinvaders.game.entity.Player;
import com.superduckinvaders.game.entity.Projectile;
import com.superduckinvaders.game.objective.Objective;

import java.util.ArrayList;
import java.util.List;

public final class Round {

    /**
     * The GameTest instance this Round belongs to.
     */
    private DuckGame parent;

    /**
     * The Round's map.
     */
    private TiledMap map;

    /**
     * The player.
     */
    private Player player;

    /**
     * Array of all entities currently in the Round.
     */
    private List<Entity> entities;

    /**
     * The current objective.
     */
    private Objective objective;

    /**
     * Initialises a new Round with the specified map.
     * @param map the Round's map
     */
    public Round(DuckGame parent, TiledMap map) {
        this.parent = parent;
        this.map = map;

        player = new Player(this, 0, 0);
        Item item = new Item(this, 100, 100, 1);

        entities = new ArrayList<Entity>();
        entities.add(player);
        entities.add(item);
    }

    /**
     * @return this Round's map
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * @return this Round's base layer (used for calculating map width/height).
     */
    private TiledMapTileLayer getBaseLayer() {
        return (TiledMapTileLayer) getMap().getLayers().get("Base");
    }

    /**
     * @return this Round's collision map layer
     */
    private TiledMapTileLayer getCollisionLayer() {
        return (TiledMapTileLayer) getMap().getLayers().get("Collisions");
    }

    /**
     * @return the width of this Round's map in pixels
     */
    public int getMapWidth() {
        return (int) (getBaseLayer().getWidth() * getBaseLayer().getTileWidth());
    }

    /**
     * @return the height of this Round's map in pixels
     */
    public int getMapHeight() {
        return (int) (getBaseLayer().getHeight() * getBaseLayer().getTileHeight());
    }

    /**
     * Gets the map tile at the specified coordinates on the map's collision layer.
     * @param x the x coordinate of the map tile
     * @param y the y coordinate of the map tile
     * @return the map tile at the specified coordinates
     */
    public TiledMapTileLayer.Cell getTile(int x, int y) {
        return getCollisionLayer().getCell(x / getTileWidth(), y / getTileHeight());
    }

    /**
     * @return the width of one tile in this Round's map
     */
    public int getTileWidth() {
        return (int) getBaseLayer().getTileWidth();
    }

    /**
     * @return the height of one tile in this Round's map
     */
    public int getTileHeight() {
        return (int) getBaseLayer().getTileHeight();
    }

    /**
     * Gets whether the map tile at the specified coordinates is blocked or not.
     * @param x the x coordinate of the map tile
     * @param y the y coordinate of the map tile
     * @return whether or not the map tile is blocked
     */
    public boolean isTileBlocked(int x, int y) {
        int tileX = x / getTileWidth();
        int tileY = y / getTileHeight();

        return getCollisionLayer().getCell(tileX, tileY) != null;
    }

    /**
     * Converts screen coordinates to world coordinates.
     * @param x the x coordinate on screen
     * @param y the y coordinate on screen
     * @return a Vector3 containing the world coordinates (x and y)
     */
    public Vector3 unproject(int x, int y) {
        return parent.getGameScreen().unproject(x, y);
    }

    /**
     * @return this Round's player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the list of all entities currently in the Round
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * Gets the current objective of this Round.
     * @return the current objective
     */
    public Objective getObjective() {
        return objective;
    }

    /**
     * Sets the current objective of this Round.
     *
     * @param objective the new objective
     */
    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    /**
     * Creates a new projectile and adds it to the list of entities.
     * @param x the initial x coordinate
     * @param y the initial y coordinate
     * @param targetX the target x coordinate
     * @param targetY the target y coordinate
     * @param speed how fast the projectile moves
     * @param damage how much damage the projectile deals
     * @param owner the owner of the projectile (i.e. the one who fired it)
     */
    public void createProjectile(double x, double y, double targetX, double targetY, double speed, double velocityXOffset, double velocityYOffset, int damage, Entity owner) {
        entities.add(new Projectile(this, x, y, targetX, targetY, speed, velocityXOffset, velocityYOffset, damage, owner));
    }

    /**
     * Updates all entities in this round.
     * @param delta the time elapsed since the last update
     */
    public void update(float delta) {
        if (objective != null) {
            objective.update(delta);

            // TODO: code for winning/losing goes here.
        }

		for(int i = 0; i < entities.size(); i++) {
			if(entities.get(i).isRemoved()) {
				entities.remove(i);
			} else {
				entities.get(i).update(delta);
			}
		}
    }
}
