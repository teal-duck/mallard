package com.superduckinvaders.game.round;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.superduckinvaders.game.DuckGame;
import com.superduckinvaders.game.entity.Entity;
import com.superduckinvaders.game.entity.Player;
import com.superduckinvaders.game.entity.Projectile;

import java.util.ArrayList;
import java.util.Iterator;
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
     * Initialises a new Level with the specified map.
     * @param map the Level's map
     */
    public Round(DuckGame parent, TiledMap map) {
        this.parent = parent;
        this.map = map;

        player = new Player(this, 0, 0);

        entities = new ArrayList<Entity>();
        entities.add(player);
    }

    /**
     * @return this Level's map
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * @return this Level's collision map layer
     */
    public TiledMapTileLayer getCollisionLayer() {
        return (TiledMapTileLayer) getMap().getLayers().get(0);
    }

    /**
     * @return the width of this Level's map in pixels
     */
    public int getMapWidth() {
        return (int) (getCollisionLayer().getWidth() * getCollisionLayer().getTileWidth());
    }

    /**
     * @return the height of this Level's map in pixels
     */
    public int getMapHeight() {
        return (int) (getCollisionLayer().getHeight() * getCollisionLayer().getTileHeight());
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
     * @return the width of one tile in this Level's map
     */
    public int getTileWidth() {
        return (int) getCollisionLayer().getTileWidth();
    }

    /**
     * @return the height of one tile in this Level's map
     */
    public int getTileHeight() {
        return (int) getCollisionLayer().getTileHeight();
    }

    /**
     * Gets whether the map tile at the specified coordinates is blocked or not.
     * @param x the x coordinate of the map tile
     * @param y the y coordinate of the map tile
     * @return whether or not the map tile is blocked
     */
    public boolean isTileBlocked(int x, int y) {
        return getTile(x, y) != null && getTile(x, y).getTile().getProperties().containsKey("blocked");
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
     * @return this Level's player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the array of all entities currently in the Round
     */
    public List<Entity> getEntities() {
        return entities;
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
    public void createProjectile(double x, double y, double targetX, double targetY, double speed, int damage, Entity owner) {
        entities.add(new Projectile(this, x, y, targetX, targetY, speed, damage, owner));
    }

    /**
     * Updates all entities in this level.
     * @param delta the time elapsed since the last update
     */
    public void update(float delta) {
        Iterator<Entity> entityIterator = entities.iterator();

        // Update or remove all entities.
        while(entityIterator.hasNext()) {
            Entity entity = entityIterator.next();

            if(entity.isRemoved()) {
                entityIterator.remove();
            } else {
                entity.update(delta);
            }
        }
    }
}
