package com.superduckinvaders.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.superduckinvaders.game.entity.*;
import com.superduckinvaders.game.entity.item.*;
import com.superduckinvaders.game.entity.mob.GunnerMob;
import com.superduckinvaders.game.entity.mob.Mob;
import com.superduckinvaders.game.entity.mob.ZombieMob;
import com.superduckinvaders.game.objective.CollectObjective;
import com.superduckinvaders.game.objective.KillObjective;
import com.superduckinvaders.game.objective.Objective;
import com.superduckinvaders.game.objective.SurviveObjective;
import com.superduckinvaders.game.screen.GameScreen;
import com.superduckinvaders.game.screen.LoseScreen;
import com.superduckinvaders.game.screen.WinScreen;
import com.superduckinvaders.game.util.Collision;
import com.superduckinvaders.game.util.RayCast;

import java.util.ArrayList;
import java.util.List;



/**
 * Represents a round of the game played on one level with a single objective.
 */
public final class Round {

    /**
     * How near entities must be to the player to get updated in the game loop.
     */
    public static final int UPDATE_DISTANCE = DuckGame.GAME_WIDTH / 2;

    /**
     * The GameTest instance this Round belongs to.
     */
    private DuckGame parent;

    /**
     * The Round's map.
     */
    private TiledMap map;

    private float tileWidth;
    private float tileHeight;

    /**
     * Map layer containing randomly-chosen layer of predefined obstacles.
     */
    private TiledMapTileLayer obstaclesLayer;
    private TiledMapTileLayer collisionLayer;

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
     * The Box2D world that the round uses.
     */
    public World world;

    /**
     * The screen this round belongs to.
     */
    public GameScreen gameScreen;

    interface Callback {
        void callback(PhysicsEntity entity, PhysicsEntity other);
    }

    /**
     * Initialises a new Round with the specified map.
     *
     * @param parent the game the round is associated with
     * @param map the Round's map
     */
    public Round(DuckGame parent, TiledMap map) {
        this.parent = parent;
        this.map = map;
        
        world = new World(Vector2.Zero, true);
        
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                applyCallback(contact,
                        (PhysicsEntity ea, PhysicsEntity eb) -> ea.beginContact(eb, contact)
                );
            }
            @Override
            public void endContact(Contact contact) {
                applyCallback(contact,
                        (PhysicsEntity ea, PhysicsEntity eb) -> ea.endContact(eb, contact)
                );
            }
            @Override
            public void preSolve(Contact contact, Manifold manifold) {
                applyCallback(contact,
                        (PhysicsEntity ea, PhysicsEntity eb) -> ea.preSolve(eb, contact, manifold)
                );
            }
            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {
                applyCallback(contact,
                        (PhysicsEntity ea, PhysicsEntity eb) -> ea.postSolve(eb, contact, contactImpulse)
                );
            }

            public void applyCallback(Contact contact, Callback cb) {
                Object a = contact.getFixtureA().getBody().getUserData();
                Object b = contact.getFixtureB().getBody().getUserData();
                if (a instanceof PhysicsEntity && b instanceof PhysicsEntity){
                    PhysicsEntity ea = (PhysicsEntity)a;
                    PhysicsEntity eb = (PhysicsEntity)b;
                    cb.callback(ea, eb);
                    cb.callback(eb, ea);
                }
            }
        });

        // Choose which obstacles to use.
        obstaclesLayer = chooseObstacles();
        collisionLayer = getCollisionLayer();

        tileWidth = collisionLayer.getTileWidth();
        tileHeight = collisionLayer.getTileHeight();

        createEnvironmentBodies();

        // Determine starting coordinates for player (0, 0 default).
        int startX = Integer.parseInt(map.getProperties().get("StartX", "0", String.class)) * getTileWidth();
        int startY = Integer.parseInt(map.getProperties().get("StartY", "0", String.class)) * getTileHeight();

        player = new Player(this, startX, startY);

        entities = new ArrayList<Entity>(128);
        entities.add(player);
        //entities.add(objective);

        Mob debugMob = addMob(new ZombieMob(this, startX + 40, startY+50));
        Mob debugMob2 = addMob(new GunnerMob(this, startX - 40, startY-50));

        ArrayList<Mob> targets = new ArrayList<Mob>();
        targets.add(debugMob);
        targets.add(debugMob2);

        if(parent.session.levelCounter < 3 || parent.session.levelCounter == 5 || parent.session.levelCounter == 7 ) {

            // Determine where to spawn the objective.
            int objectiveX = Integer.parseInt(map.getProperties().get("ObjectiveX", "10", String.class)) * getTileWidth();
            int objectiveY = Integer.parseInt(map.getProperties().get("ObjectiveY", "10", String.class)) * getTileHeight();

            Item objective = new CollectItem(this, objectiveX, objectiveY);
            setObjective(new CollectObjective(this, objective));
            entities.add(objective);

        } else if (parent.session.levelCounter == 4 || parent.session.levelCounter == 6  ) {

            setObjective (new SurviveObjective(this));

        } else{
            setObjective(new KillObjective(
                            this,
                            targets,
                            "Kill the Enemies!"
                    )
            );
        }
        //FIXME:the score from the last enemy is not added.



        createPickup(startX + 20, startY, Player.Pickup.GUN, Float.POSITIVE_INFINITY);
        createPickup(startX + 40, startY, Player.Pickup.RATE_OF_FIRE, 60);
        createPickup(startX - 40, startY-40, Player.Pickup.HEALTH, 0);
        createPickup(startX - 60, startY-40, Player.Pickup.LIGHTSABER, Float.POSITIVE_INFINITY);


        //spawnRandomMobs(10, 0, 0, getMapWidth(), getMapHeight());
    }

    /**
     * Randomly selects and returns a set of predefined obstacles from the map.
     *
     * @return the map layer containing the obstacles
     */
    private TiledMapTileLayer chooseObstacles() {
        int count = 0;

        // First count how many obstacle layers we have.
        while (map.getLayers().get(String.format("Obstacles%d", count)) != null)
            count++;

        // Choose a random layer or return null if there are no layers.
        if (count == 0) {
            return null;
        } else {
            return (TiledMapTileLayer) map.getLayers().get(String.format("Obstacles%d", MathUtils.random(0, count - 1)));
        }
    }

    private interface Constructor {
        Entity construct(float x, float y, float w, float h);
    }

    private void layerMap(TiledMapTileLayer layer, Constructor constructor){
        if (layer == null){
            return;
        }

        float tw = collisionLayer.getTileWidth();
        float th = collisionLayer.getTileHeight();

        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                if (layer.getCell(x, y) != null) {
                    float tileX = x * tw;
                    float tileY = y * th;
                    // obstacleEntities.add(new Obstacle(this, tileX, tileY, tw, th));
                    constructor.construct(tileX, tileY, tw, th);
                }
            }
        }
    }


    private void createEnvironmentBodies() {
        Constructor createObstacle = (float x, float y, float w, float h) -> (new Obstacle(this, x, y, w, h));
        Constructor createWater = (float x, float y, float w, float h) -> (new WaterEntity(this, x, y, w, h));

        layerMap(getCollisionLayer(), createObstacle);
        layerMap(getObstaclesLayer(), createObstacle);
        layerMap(getWaterLayer(),     createWater   );

        
        float mapHeight = getMapHeight();
        float mapWidth = getMapWidth();

        //Assumes square tiles!
        float tw = collisionLayer.getTileWidth();
        
        // 4 map edge objects
        new Obstacle(this, -tw,      -tw,       tw,          mapHeight+tw);
        new Obstacle(this, -tw,      -tw,       mapWidth+tw, tw          );
        new Obstacle(this, -tw,      mapHeight, mapWidth+tw, tw          );
        new Obstacle(this, mapWidth, -tw,       tw,          mapHeight+tw);
    }
    
    
    /**
     * Tests if a point resides inside a body
     * @param x x
     * @param y y 
     */
    public boolean collidePoint(float x, float y) {
        return collidePoint(new Vector2(x, y));
    }
    public boolean collidePoint(Vector2 p) {
        return collidePoint(p, PhysicsEntity.WORLD_BITS);
    }
    public boolean collidePoint(Vector2 p, short maskBits) {
        p.scl(PhysicsEntity.METRES_PER_PIXEL);
        Collision.Query q = new Collision.QueryPoint(world, p, maskBits);
        return q.query();
    }
    
    public boolean collideArea(Vector2 pos, Vector2 size) {
        return collideArea(pos, size, PhysicsEntity.WORLD_BITS);
    }
    public boolean collideArea(Vector2 pos, Vector2 size, short maskBits) {
        pos.scl(PhysicsEntity.METRES_PER_PIXEL);
        size.scl(PhysicsEntity.METRES_PER_PIXEL);
        Collision.Query q = new Collision.QueryArea(world, pos, size, maskBits);
        return q.query();
    }
    
    public boolean rayCast(Vector2 pos1, Vector2 pos2){
        return rayCast(pos1, pos2, PhysicsEntity.WORLD_BITS);
    }
    public boolean rayCast(Vector2 pos1, Vector2 pos2, short maskBits) {
        RayCast.RayCastCB r = new RayCast.RayCastCB(maskBits);
        world.rayCast(
                r,
                pos1.cpy().scl(PhysicsEntity.METRES_PER_PIXEL),
                pos2.cpy().scl(PhysicsEntity.METRES_PER_PIXEL)
        );
        return r.clear;
    }
    
    public boolean pathIsClear(Vector2 pos, Vector2 size, Vector2 target){
        float width  = size.x;
        float height = size.y;
        Vector2[] corners = {new Vector2( width/2,  height/2),
                             new Vector2(-width/2,  height/2),
                             new Vector2(-width/2,  -height/2),
                             new Vector2(width/2,  -height/2)
                         };
        boolean result = true;
        
        for (Vector2 corner : corners){
            result = result && rayCast(corner.cpy().add(pos), corner.cpy().add(target));
            
        }
        
        return result;
    }


    private void spawnRandomMobs(int amount, int minX, int minY, int maxX, int maxY) {
        for (int i = 0; i < amount;) {
            int x = MathUtils.random(minX, maxX);
            int y = MathUtils.random(minY, maxY);
            if (!collidePoint(x, y))
                addMob(new ZombieMob(this, x, y));
                i++;
        }
    }


    public Mob addMob(Mob mob) {
        entities.add(mob);
        return mob;
    }

    /**
     * Gets the current map
     * @return this Round's map
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * Gets the base layer of the map
     * @return this Round's base layer (used for calculating map width/height)
     */
    public TiledMapTileLayer getBaseLayer() {
        return (TiledMapTileLayer) getMap().getLayers().get("Base");
    }

    /**
     * Gets the collision layer of the map
     * @return this Round's collision map layer
     */
    public TiledMapTileLayer getCollisionLayer() {
        return (TiledMapTileLayer) getMap().getLayers().get("Collision");
    }
    public TiledMapTileLayer getWaterLayer() {
        return (TiledMapTileLayer) getMap().getLayers().get("Water");
    }

    /**
     * Gets the obstacles layer of the map
     * @return this Round's obstacles map layer or null if there isn't one
     */
    public TiledMapTileLayer getObstaclesLayer() {
        return obstaclesLayer;
    }

    /**
     * gets the overhang layer of the map
     * @return this Round's overhang map layer (rendered over entities)
     */
    public TiledMapTileLayer getOverhangLayer() {
        return (TiledMapTileLayer) getMap().getLayers().get("Overhang");
    }

    /**
     * Gets the width of the map in pixels
     * @return the width of this Round's map in pixels
     */
    public int getMapWidth() {
        return (int) (getBaseLayer().getWidth() * getBaseLayer().getTileWidth());
    }

    /**
     * Gets the height of the map in pixels
     * @return the height of this Round's map in pixels
     */
    public int getMapHeight() {
        return (int) (getBaseLayer().getHeight() * getBaseLayer().getTileHeight());
    }

    /**
     * Gets the width of each tile
     * @return the width of one tile in this Round's map
     */
    public int getTileWidth() {
        return (int) getBaseLayer().getTileWidth();
    }

    /**
     * Gets the height of each tile
     * @return the height of one tile in this Round's map
     */
    public int getTileHeight() {
        return (int) getBaseLayer().getTileHeight();
    }

    /**
     * Gets whether the map tile at the specified coordinates is blocked or not.
     *
     * @param x the x coordinate of the map tile
     * @param y the y coordinate of the map tile
     * @return whether or not the map tile is blocked
     */
    public boolean isTileBlocked(int x, int y) {
        int tileX = x / getTileWidth();
        int tileY = y / getTileHeight();

        return getCollisionLayer().getCell(tileX, tileY) != null || (getObstaclesLayer() != null && getObstaclesLayer().getCell(tileX, tileY) != null);
    }

    /**
     * Converts screen coordinates to world coordinates.
     *
     * @param x the x coordinate on screen
     * @param y the y coordinate on screen
     * @return a Vector3 containing the world coordinates (x and y)
     */
    public Vector3 unproject(int x, int y) {
        return gameScreen.unproject(x, y);
    }

    public DuckGame getGame() {
        return parent;
    }

    /**
     * Gets the player in the round
     * @return this Round's player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets all entities in the round
     * @return the list of all entities currently in the Round
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * Adds an entity to the entity list.
     *
     * @param newEntity new entity of any type
     */
    // TODO: remove this once tests are complete
    public void addEntity(Entity newEntity) {
        entities.add(newEntity);
    }

    /**
     * Gets the current objective of this Round.
     *
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
     *
     * @param pos      the projectile's starting position
     * @param velocity the projectile's velocity
     * @param damage          how much damage the projectile deals
     * @param owner           the owner of the projectile (i.e. the one who fired it)
     */
    public void createProjectile(Vector2 pos, Vector2 velocity, int damage, PhysicsEntity owner) {
        entities.add(new Projectile(this, pos, velocity, damage, owner));
    }

    /**
     * Creates a new particle effect and adds it to the list of entities.
     *
     * @param x         the x coordinate of the center of the particle effect
     * @param y         the y coordinate of the center of the particle effect
     * @param duration  how long the particle effect should last for
     * @param animation the animation to use for the particle effect
     */
    public void createParticle(float x, float y, float duration, Animation animation) {
        entities.add(new Particle(this, x - animation.getKeyFrame(0).getRegionWidth() / 2, y - animation.getKeyFrame(0).getRegionHeight() / 2, duration, animation));
    }

    /**
     * Creates a new particle effect and adds it to the list of entities.
     *
     * @param position  the particle's starting position
     * @param duration  how long the particle effect should last for
     * @param animation the animation to use for the particle effect
     */
    public void createParticle(Vector2 position, float duration, Animation animation) {
        createParticle(position.x, position.y, duration, animation);
    }

    /**
     * Creates a pickup on the floor and adds it to the list of entities.
     *
     * @param x       the x coordinate of the powerup
     * @param y       the y coordinate of the powerup
     * @param pickup the powerup to grant to the player
     * @param time    how long the powerup should last for
     */
    public void createPickup(float x, float y, Player.Pickup pickup, float time) {
        entities.add(new PickupItem(this, x, y, pickup, time));
    }

    /**
     * Updates all entities in this Round.
     *
     * @param delta the time elapsed since the last update
     */
    public void update(float delta) {
        world.step(delta, 6, 2);
        if (objective != null) {
            objective.update(delta);

            if (objective.getStatus() == Objective.ObjectiveStatus.COMPLETED) {
                parent.setScreen(new WinScreen(parent, player.getScore()));
            } else if (player.isDead()) {
                parent.setScreen(new LoseScreen(parent));
            }
        }

        for (int i = 0; i < entities.size();i++) {
            Entity entity = entities.get(i);

            if (entity.isRemoved()) {
                if (entity instanceof Mob && ((Mob) entity).isDead()) {
                    player.addScore((int) (10 * (player.hasPickup(Player.Pickup.SCORE_MULTIPLIER) ? Player.PLAYER_SCORE_MULTIPLIER : 1)));
                }
                entity.dispose();
                entities.remove(i--);
            } else if (entity.distanceTo(player.getX(), player.getY()) < UPDATE_DISTANCE){
                // Don't bother updating entities that aren't on screen.
                entity.update(delta);
            }
        }
    }


}
