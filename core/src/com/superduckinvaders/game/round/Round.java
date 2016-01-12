package com.superduckinvaders.game.round;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.superduckinvaders.game.DuckGame;
import com.superduckinvaders.game.entity.Entity;
import com.superduckinvaders.game.entity.Player;
import com.superduckinvaders.game.entity.Projectile;

import java.util.Iterator;

/**
 * Created by Oliver on 10/01/2016.
 */
public final class Round {

    /**
     * The GameTest instance this Level belongs to.
     */
    private DuckGame parent;

    /**
     * The Level's map.
     */
    private TiledMap map;

    /**
     * The player.
     */
    private Player player;

    /**
     * Array of all entities currently in the Level.
     */
    private Array<Entity> entities;

    /**
     * Array of all projectiles currently in the Level.
     * This is to avoid a bug where you can't next iterators.
     * This also prevents projectiles from having to check collisions with other projectiles, thus saving time.
     */
    private Array<Projectile> projectiles;

    /**
     * Initialises a new Level with the specified map.
     * @param map the Level's map
     */
    public Round(DuckGame parent, TiledMap map) {
        this.parent = parent;
        this.map = map;

        player = new Player(this);

        entities = new Array<Entity>();
        entities.add(player);

        projectiles = new Array<Projectile>();
    }

    /**
     * @return this Level's map
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * @return this Level's main map layer
     */
    public TiledMapTileLayer getMapLayer() {
        return (TiledMapTileLayer) getMap().getLayers().get(0);
    }

    /**
     * @return the width of this Level's map in pixels
     */
    public int getMapWidth() {
        return (int) (getMapLayer().getWidth() * getMapLayer().getTileWidth());
    }

    /**
     * @return the height of this Level's map in pixels
     */
    public int getMapHeight() {
        return (int) (getMapLayer().getHeight() * getMapLayer().getTileHeight());
    }

    /**
     * Gets the map tile at the specified coordinates.
     * @param x the x coordinate of the map tile
     * @param y the y coordinate of the map tile
     * @return the map tile at the specified coordinates
     */
    public TiledMapTileLayer.Cell getTile(int x, int y) {
        return getMapLayer().getCell(x / getTileWidth(), y / getTileHeight());
    }

    /**
     * @return the width of one tile in this Level's map
     */
    public int getTileWidth() {
        return (int) getMapLayer().getTileWidth();
    }

    /**
     * @return the height of one tile in this Level's map
     */
    public int getTileHeight() {
        return (int) getMapLayer().getTileHeight();
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
     * @return the array of all entities currently in the Level
     */
    public Array<Entity> getEntities() {
        return entities;
    }

    /**
     * @return the array of all projectiles currently in the Level
     */
    public Array<Projectile> getProjectiles() {
        return projectiles;
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
        projectiles.add(new Projectile(this, x, y, targetX, targetY, speed, damage, owner));
    }

    /**
     * Updates all entities in this level.
     * @param delta the time elapsed since the last update
     */
    public void update(float delta) {
        Iterator<Entity> entityIterator = entities.iterator();
        Iterator<Projectile> projectileIterator = projectiles.iterator();

        // Update or remove all entities.
        while(entityIterator.hasNext()) {
            Entity entity = entityIterator.next();

            if(entity.isRemoved()) {
                entityIterator.remove();
            } else {
                entity.update(delta);
            }
        }

        // Update or remove all projectiles.
        while(projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();

            if(projectile.isRemoved()) {
                projectileIterator.remove();
            } else {
                projectile.update(delta);
            }
        }
    }
}
