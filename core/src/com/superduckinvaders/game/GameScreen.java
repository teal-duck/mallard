package com.superduckinvaders.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.superduckinvaders.game.entity.Player;
import com.superduckinvaders.game.entity.Projectile;
import com.superduckinvaders.game.round.Round;

public class GameScreen implements Screen {

	public static int WIDTH = 1280; // 640
	public static int HEIGHT = 720; // 448
	final double ns = 1000000000.0 / 60;
	Round round;
	Player player;
	double delta2 = 0;
	long lastTime;
	long now;
	SpriteBatch batch;
	private int xOffset, yOffset;
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

	public GameScreen(Round round) {
		this.parent = parent;

		camera = new OrthographicCamera(WIDTH, HEIGHT);
		mapRenderer = new OrthogonalTiledMapRenderer()

		lastTime = System.nanoTime();

		round = new Round();
		player = new Player();
	}

	@Override
	public void show() {
		camera = new OrthographicCamera(WIDTH, HEIGHT);

		mapRenderer = new OrthogonalTiledMapRenderer(parent.getMap());
		spriteBatch = new SpriteBatch();
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

		for (Projectile projectile : round.getProjectiles()) {
			projecti.render(spriteBatch);
		}

		spriteBatch.end();
	}

	private void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		xOffset = player.x - WIDTH/2;
		yOffset = player.y - HEIGHT/2;
		
		round.render(xOffset, yOffset, this);
		batch.end();
		//player.update();
		
	}

	private void update() {
		parent.update();
	}

	public void renderPlayer(int x, int y, TextureRegion sprite) {
		//TODO Modify for Player
		batch.draw(sprite, x, y);
	}
	
	public void renderProjectile(int x, int y, double angle, TextureRegion sprite) {
		//TODO Modify for Projectile
		batch.draw(sprite, x, y);
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
