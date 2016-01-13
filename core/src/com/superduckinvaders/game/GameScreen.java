package com.superduckinvaders.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.superduckinvaders.game.entity.Entity;
import com.superduckinvaders.game.graphics.Assets;
import com.superduckinvaders.game.round.Round;

public class GameScreen implements Screen {

	public static int WIDTH = 1280; // 640
	public static int HEIGHT = 720; // 448

	/**
	 * The game camera.
	 */
	private OrthographicCamera camera;

	/**
	 * The renderer for the tile map.
	 */
	private OrthogonalTiledMapRenderer mapRenderer;

	/**
	 * The sprite batch for texture rendering.
	 */
	private SpriteBatch spriteBatch;
	private SpriteBatch uiBatch;

	/**
	 * The Round this GameScreen renders.
	 */
	private Round round;

	public GameScreen(Round round) {
		this.round = round;
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

	@Override
	public void show() {
		camera = new OrthographicCamera(WIDTH, HEIGHT);

		mapRenderer = new OrthogonalTiledMapRenderer(round.getMap());
		spriteBatch = new SpriteBatch();
		uiBatch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		round.update(delta);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Centre the camera on the player.
		camera.position.set((int) round.getPlayer().getX() + round.getPlayer().getWidth() / 2, (int) round.getPlayer().getY() + round.getPlayer().getHeight() / 2, 0);
		camera.update();
		
		mapRenderer.setView(camera);
		mapRenderer.render();

		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();

		// Draw all entities.
		for (Entity entity : round.getEntities()) {
			entity.render(spriteBatch);
		}
		spriteBatch.end();
		
		uiBatch.begin();
		Assets.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		Assets.font.draw(uiBatch, "test string please ignore", 25, 160);
		uiBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
