package com.superduckinvaders.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.superduckinvaders.game.ai.ZombieAI;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.assets.TextureSet;
import com.superduckinvaders.game.entity.Character;
import com.superduckinvaders.game.entity.*;
import com.superduckinvaders.game.entity.item.Item;
import com.superduckinvaders.game.entity.item.Powerup;
import com.superduckinvaders.game.entity.item.Upgrade;
import com.superduckinvaders.game.objective.CollectObjective;
import com.superduckinvaders.game.objective.Objective;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;



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

    /**
     * Map layer containing randomly-chosen layer of predefined obstacles.
     */
    private TiledMapTileLayer obstaclesLayer;

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
    
    public World world;
    Mob debugMob;

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
                Object a = contact.getFixtureA().getBody().getUserData();
                Object b = contact.getFixtureB().getBody().getUserData();
                if (a instanceof Entity && b instanceof Entity){
                    Entity ea = (Entity)a;
                    Entity eb = (Entity)b;
                    ea.onCollision(eb);
                    eb.onCollision(ea);
                }
            }
            @Override
            public void endContact(Contact contact) {}
            @Override
            public void postSolve(Contact arg0, ContactImpulse arg1) {}
            @Override
            public void preSolve(Contact arg0, Manifold arg1) {}
        });

        // Choose which obstacles to use.
        obstaclesLayer = chooseObstacles();
        
        TiledMapTileLayer collision = getCollisionLayer();
        
        float tw = collision.getTileWidth();
        float th = collision.getTileHeight();
        
        for (int x = 0 ; x<collision.getWidth() ; x++){
            for (int y = 0 ; y<collision.getHeight() ; y++){
                if (null != collision.getCell(x, y)){
                    float tileX = x*tw;
                    float tileY = y*th;
                    new Obstacle(this, tileX, tileY, tw, th);
                }
            }
        }

        // Determine starting coordinates for player (0, 0 default).
        int startX = Integer.parseInt(map.getProperties().get("StartX", "0", String.class)) * getTileWidth();
        int startY = Integer.parseInt(map.getProperties().get("StartY", "0", String.class)) * getTileHeight();

        player = new Player(this, startX, startY);
        //Obstacle obstacle = new Obstacle(this, startX, startY - 40, 20, 20);

        // Determine where to spawn the objective.
        int objectiveX = Integer.parseInt(map.getProperties().get("ObjectiveX", "10", String.class)) * getTileWidth();
        int objectiveY = Integer.parseInt(map.getProperties().get("ObjectiveY", "10", String.class)) * getTileHeight();

        Item objective = new Item(this, objectiveX, objectiveY, Assets.flag);
        setObjective(new CollectObjective(this, objective));

        entities = new ArrayList<Entity>(128);
        entities.add(player);
        //entities.add(obstacle);
        entities.add(objective);

        createUpgrade(startX + 20, startY, Player.Upgrade.GUN);
        createPowerup(startX + 40, startY, Player.Powerup.RATE_OF_FIRE, 60);
        
        debugMob = spawnZombieMob(startX + 40, startY+50);
        //spawnRandomMobs(500, 200, 200, 1000, 1000);
    }

    /**
     * Randomly selects and returns a set of predefined obstacles from the map.
     *
     * @return the map layer containing the obstacles
     */
    private TiledMapTileLayer chooseObstacles() {
        int count = 0;

        // First count how many obstacle layers we have.
        while (map.getLayers().get(String.format("Obstacles%d", count)) != null) {
            count++;
        }

        // Choose a random layer or return null if there are no layers.
        if (count == 0) {
            return null;
        } else {
            return (TiledMapTileLayer) map.getLayers().get(String.format("Obstacles%d", MathUtils.random(0, count - 1)));
        }
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
        p.scl(Entity.METRES_PER_PIXEL);
        Query q = new QueryPoint(world, p);
        return q.query();
    }
    
    public boolean collideArea(Vector2 pos, Vector2 size) {
        pos.scl(Entity.METRES_PER_PIXEL);
        size.scl(Entity.METRES_PER_PIXEL);
        Query q = new QueryArea(world, pos, size);
        return q.query();
    }
    
    public boolean rayCast(Vector2 pos1, Vector2 pos2){
        return rayCast(pos1, pos2, Entity.WORLD_BITS);
    }
    public boolean rayCast(Vector2 pos1, Vector2 pos2, short maskBits){
        RayCastCB r = new RayCastCB(maskBits);
        //new vectors as they may be modified
        world.rayCast(r,
                new Vector2(pos1).scl(Entity.METRES_PER_PIXEL),
                new Vector2(pos2).scl(Entity.METRES_PER_PIXEL));
        return r.collidesEnvironment;
    }
    
    public boolean isClearTo(Entity entity, Vector2 target){
        Vector2 pos = entity.getCentre();
        
        float width  = entity.getWidth();
        float height = entity.getHeight();
        Vector2 corner = new Vector2(width/2, height/2);
        
        Vector2 direction = new Vector2(target).sub(pos).nor();
        Vector2 perp = direction.rotate90(0); //modifies direction
        
        float min = 0;
        float max = 99999;
        
        for (int i = 0; i < 4; i++){
            float dist = new Vector2(corner).dot(perp);
            min = Math.min(min, dist);
            max = Math.max(max, dist);
            corner.rotate90(0);
        }
        
        Vector2 offset1 = new Vector2(perp).scl(min);
        Vector2 offset2 = new Vector2(perp).scl(max);
        
        return rayCast(new Vector2(offset1).add(pos),
                    new Vector2(offset1).add(target)) ||
               rayCast(new Vector2(offset2).add(pos),
                    new Vector2(offset2).add(target));
        
        
        
    }

    /**
     * Spawns a number of random mobs the specified distance from the player.
     * @param amount how many random mobs to spawn
     * @param minX the minimum x distance from the player to spawn the mobs
     * @param minY the minimum y distance from the player to spawn the mobs
     * @param maxX the maximum x distance from the player to spawn the mobs
     * @param maxY the maximum y distance from the player to spawn the mobs
     */
    private void spawnRandomMobs(int amount, int minX, int minY, int maxX, int maxY) {
        while(amount > 0) {
            int x = MathUtils.random(0, getMapWidth());
            int y = MathUtils.random(0, getMapHeight());
            if (!collidePoint(x, y)){
                spawnZombieMob(getPlayer().getX() + x, getPlayer().getY() + y);
                amount--;
            }
        }
    }
    
    private Mob spawnZombieMob(float x, float y){
        return createMob(x, y, 100, Assets.badGuyNormal, 5);
    }
    
    /**
     * Creates a mob and adds it to the list of entities, but only if it doesn't intersect with another character.
     * @param x the initial x coordinate
     * @param y the initial y coordinate
     * @param health the initial health of the mob
     * @param textureSet the texture set to use
     * @param speed how fast the mob moves in pixels per second
     * @return true if the mob was successfully added, false if there was an intersection and the mob wasn't added
     */
    public Mob createMob(float x, float y, int health, TextureSet textureSet, int speed) {
        Mob mob = new Mob(this, x, y, health, textureSet, speed, new ZombieAI(this, 32));
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
        return parent.getGameScreen().unproject(x, y);
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
     * @param x               the initial x coordinate
     * @param y               the initial y coordinate
     * @param targetX         the target x coordinate
     * @param targetY         the target y coordinate
     * @param speed           how fast the projectile moves
     * @param velocityXOffset the offset to the initial X velocity
     * @param velocityYOffset the offset to the initial Y velocity
     * @param damage          how much damage the projectile deals
     * @param owner           the owner of the projectile (i.e. the one who fired it)
     */
    public void createProjectile(Vector2 pos, Vector2 velocity,  int damage, Entity owner) {
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
     * Creates a powerup on the floor and adds it to the list of entities.
     *
     * @param x       the x coordinate of the powerup
     * @param y       the y coordinate of the powerup
     * @param powerup the powerup to grant to the player
     * @param time    how long the powerup should last for
     */
    public void createPowerup(float x, float y, Player.Powerup powerup, float time) {
        entities.add(new Powerup(this, x, y, powerup, time));
    }

    /**
     * Creates an upgrade on the floor and adds it to the list of entities.
     *
     * @param x       the x coordinate of the upgrade
     * @param y       the y coordinate of the upgrade
     * @param upgrade the upgrade to grant to the player
     */
    public void createUpgrade(float x, float y, Player.Upgrade upgrade) {
        entities.add(new Upgrade(this, x, y, upgrade));
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
                parent.showWinScreen(player.getScore());
            } else if (player.isDead()) {
                parent.showLoseScreen();
            }
        }

        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);

            if (entity.isRemoved()) {
                if (entity instanceof Mob && ((Mob) entity).isDead()) {
                    player.addScore((int) (10 * (player.getPowerup() == Player.Powerup.SCORE_MULTIPLIER ? Player.PLAYER_SCORE_MULTIPLIER : 1)));
                }
                if (entity.body != null) {
                    world.destroyBody(entity.body);
                }
                entities.remove(i);
            } else if (entity.distanceTo(player.getX(), player.getY()) < UPDATE_DISTANCE){
                // Don't bother updating entities that aren't on screen.
                entity.update(delta);
            }
        }
    }
        
    public static abstract class Query implements QueryCallback {
        public boolean result = false;
        public World world;
        
        public Query(World world){
            this.world=world;
        }
        public abstract boolean query();
        public abstract boolean reportFixture(Fixture fixture);
        
    }
    public static class QueryPoint extends Query {
        public Vector2 p;
        
        public QueryPoint(World world, Vector2 p){
            super(world);
            this.p = p;
        }
        
        public boolean query(){
            world.QueryAABB(this, p.x, p.y, p.x+1, p.y+1);
            return result;
        }
        
        public boolean reportFixture(Fixture fixture){
            if (fixture.testPoint(p)) { // if ((fixture.getFilterData().categoryBits | Entity.WORLD_BITS) != 0 && 
                result = true; // we collided
                return false; // ends the query
            }
            return true; // keep searching
        }
    }
    public static class QueryArea extends Query {
        Vector2 p1;
        Vector2 p2;
        
        public QueryArea(World world, Vector2 pos, Vector2 size){
            super(world);
            this.p1 = pos;
            this.p2 = size.add(pos);
        }
        
        public boolean query(){
            world.QueryAABB(this, p1.x, p1.y, p2.x, p2.y);
            return result;
        }
        
        public boolean reportFixture(Fixture fixture){
            result = true; //AABB gave us ANY fixture, BB overlaps.
            return false;
        }
    }
    
    
    
    class RayCastCB implements RayCastCallback {
        public float fraction;
        public boolean collidesEnvironment=false;
        public short maskBits;
        
        public RayCastCB(short maskBits){
            fraction = 1f;
            this.maskBits = maskBits;
            
        }
        @Override
        public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction){
            //if (!(fixture.getUserData() instanceof Player)){
            if ((fixture.getFilterData().categoryBits & maskBits) != 0){
                this.collidesEnvironment = true;
                this.fraction = fraction;
                return 0f;
            }
            /* this reduces the length of the ray to the currently found intersection
             * this is done because fixtures are not necessarily reported in
             * in any order, and we only care about the closest intersection
             */
            return fraction;
        }
    }
    
}
