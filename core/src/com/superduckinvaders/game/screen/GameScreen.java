package com.superduckinvaders.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.superduckinvaders.game.DuckGame;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.entity.Entity;
import com.superduckinvaders.game.entity.PhysicsEntity;
import com.superduckinvaders.game.entity.Player;

/**
 * Screen for interaction with the game.
 */
public class GameScreen extends BaseScreen {

    /**
     * The game camera.
     */
    private OrthographicCamera camera;

    /**
     * The renderer for the tile map.
     */
    private OrthogonalTiledMapRenderer mapRenderer;

    /**
     * The sprite batches for rendering.
     */
    private SpriteBatch spriteBatch, uiBatch;

    /**
     * The Round this GameScreen renders.
     */
    private Round round;
    
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;

    /**
     * Initialises this GameScreen for the specified round.
     *
     * @param game  the main game class
     * @param round the round to be displayed
     */
    public GameScreen(DuckGame game, Round round) {
        super(game);
        round.gameScreen = this;
        this.round = round;
        debugRenderer = new Box2DDebugRenderer();
    }

    /**
     * Converts screen coordinates to world coordinates.
     *
     * @param x the x coordinate on screen
     * @param y the y coordinate on screen
     * @return a Vector3 containing the world coordinates (x and y)
     */
    public Vector3 unproject(int x, int y) {
        return camera.unproject(new Vector3(x, y, 0));
    }

    /**
     * @return the Round currently on this GameScreen
     */
    public Round getRound() {
        return round;
    }

    /**
     * Shows this GameScreen. Called by libGDX to set up the graphics.
     */
    @Override
    public void show() {
        camera = new OrthographicCamera(DuckGame.GAME_WIDTH, DuckGame.GAME_HEIGHT);
        camera.zoom -= 0.5;

        spriteBatch = new SpriteBatch();
        uiBatch     = new SpriteBatch();
        mapRenderer = new OrthogonalTiledMapRenderer(round.getMap(), spriteBatch);
    }

    /**
     * Main game loop.
     *
     * @param delta how much time has passed since the last update
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        round.update(delta);  // TODO(avinash): If round calls dispose, stop here.

        // Centre the camera on the player.
        camera.position.set(
                round.getPlayer().getX() + round.getPlayer().getWidth() / 2,
                round.getPlayer().getY() + round.getPlayer().getHeight() / 2,
                0
        );
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        this.drawGame();
        spriteBatch.end();

        this.drawDebug();

        uiBatch.begin();
        this.drawUI();
        uiBatch.end();
    }

    /**
     * Draw the game level.
     */
    private void drawGame() {
        // Render base and collision layers.
        mapRenderer.setView(camera);
        mapRenderer.renderTileLayer(round.getBaseLayer());
        mapRenderer.renderTileLayer(round.getCollisionLayer());

        // Render randomly-chosen obstacles layer.
        if (round.getObstaclesLayer() != null)
            mapRenderer.renderTileLayer(round.getObstaclesLayer());

        // Draw all entities.
        for (Entity entity : round.getEntities())
            entity.render(spriteBatch);

        // Render overhang layer (draws over the player).
        if (round.getOverhangLayer() != null)
            mapRenderer.renderTileLayer(round.getOverhangLayer());
    }

    /**
     * Draws a debug layer with helpful collision squares and stuff.
     */
    private void drawDebug() {
        debugMatrix = camera.combined.cpy().scl(PhysicsEntity.PIXELS_PER_METRE);
        debugRenderer.render(round.world, debugMatrix);
    }

    /**
     * Draw the static UI.
     * TODO(mallard): Finish UI.
     * TODO(avinash): Use Stage2D, like we are for the static screens?
     */
    private void drawUI() {
        Assets.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        Assets.font.draw(uiBatch, "Objective: " + round.getObjective().getObjectiveString(), 10, 710);
        Assets.font.draw(uiBatch, "Score: " + round.getPlayer().getScore(), 10, 680);
        Assets.font.draw(uiBatch, Gdx.graphics.getFramesPerSecond() + " FPS", 10, 650);

        // Draw stamina bar (for flight);
        uiBatch.draw(Assets.staminaEmpty, 1080, 10);
        if (round.getPlayer().getFlyingTimer() > 0) {
            float remainingFlight = round.getPlayer().getFlyingTimer();
            float barFraction = Math.min(1,  (remainingFlight / Player.PLAYER_FLIGHT_TIME));
            Assets.staminaFull.setRegionWidth((int)Math.max(0f, barFraction*192));
        } else {
            Assets.staminaFull.setRegionWidth(0);
        }
        uiBatch.draw(Assets.staminaFull, 1080, 10);

        // Draw powerup bar.
        uiBatch.draw(Assets.powerupEmpty, 1080, 50);
        Assets.powerupFull.setRegionWidth((int) Math.max(0, round.getPlayer().getPowerupTime() / round.getPlayer().getPowerupInitialTime() * 192));
        uiBatch.draw(Assets.powerupFull, 1080, 50);

        for (int x = 0; x < round.getPlayer().getMaximumHealth(); x += 2) {
            if(x+2 <= round.getPlayer().getCurrentHealth())
                uiBatch.draw(Assets.heartFull, x * 18 + 10, 10);
            else if(x+1 <= round.getPlayer().getCurrentHealth())
                uiBatch.draw(Assets.heartHalf, x * 18 + 10, 10);
            else
                uiBatch.draw(Assets.heartEmpty, x * 18 + 10, 10);
        }
    }

    /**
     * Called to dispose libGDX objects used by this GameScreen.
     */
    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        debugRenderer.dispose();
        mapRenderer.dispose();
        spriteBatch.dispose();
        uiBatch.dispose();
    }
}
