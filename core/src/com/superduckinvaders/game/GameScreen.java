package com.superduckinvaders.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.superduckinvaders.game.entity.Player;
import com.superduckinvaders.game.round.Round;

public class GameScreen implements Screen {

	public static int WIDTH = 1280; // 640
	public static int HEIGHT = 720; // 448
	private int xOffset, yOffset;
	
	DuckGame game;
	OrthographicCamera camera;
	Round round;
	Player player;

	double delta2 = 0;
	final double ns = 1000000000.0 / 60;
	long lastTime;
	long now;
	 
	SpriteBatch batch;
	
	public GameScreen (DuckGame game) {
		this.game = game;
		camera = new OrthographicCamera(WIDTH, HEIGHT);
		camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
		lastTime = System.nanoTime();

		batch = new SpriteBatch();
		
	}

	
	@Override
	public void render(float delta) {
		now = System.nanoTime();
		
		delta2 += (now - lastTime) / ns;
		lastTime = now;
		while (delta2 >= 1) {
			update();
			delta2--;
		}
		draw();
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
		round.update();
		player.update();
		
		
	}
	
	public void renderTile(int x, int y, TextureRegion sprite) {
		batch.draw(sprite, x - xOffset, y - yOffset);
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
	public void show() {
		
	}


	@Override
	public void resize(int width, int height) {
		
	}


	@Override
	public void pause() {
		
	}


	@Override
	public void resume() {
		
	}


	@Override
	public void hide() {
		
	}


	@Override
	public void dispose() {
		
	}

}
