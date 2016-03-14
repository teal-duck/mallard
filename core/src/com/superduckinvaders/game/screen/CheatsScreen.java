package com.superduckinvaders.game.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.superduckinvaders.game.DuckGame;
import com.superduckinvaders.game.assets.Assets;


/**
 * Shows a list of buttons that take you to unlocked levels.
 */
public class CheatsScreen extends BaseScreen {

	/**
	 * A Scene2D stage to add UI elements to.
	 */
	private Stage stage;


	/**
	 * Start off the BaseScreen
	 *
	 * @param game
	 *                The main game class
	 * @see BaseScreen#getGame
	 */
	public CheatsScreen(DuckGame game) {
		super(game);
	}


	/**
	 * Shows this GameScreen. Called by libGDX to set up the graphics.
	 */
	@Override
	public void show() {
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);

		Drawable button = new TextureRegionDrawable(Assets.button);
		Drawable checkButton = new TextureRegionDrawable(Assets.checkButton);
		Drawable checkButtonChecked = new TextureRegionDrawable(Assets.checkButtonChecked);

		Label.LabelStyle white = new Label.LabelStyle(Assets.font, Color.WHITE);

		Button startButton = new Button(new Button.ButtonStyle(button, button, button));
		startButton.setPosition((stage.getWidth() - startButton.getPrefWidth()) / 2, 585);
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				getGame().setScreen(new StartScreen(getGame()));
			}
		});

		Label startLabel = new Label("RETURN TO START", white);
		startLabel.setPosition((stage.getWidth() - startLabel.getPrefWidth()) / 2, 600);
		startLabel.setTouchable(Touchable.disabled);

		Label cheatsLabel = new Label("ENABLE CHEATS", white);
		cheatsLabel.setPosition((stage.getWidth() - cheatsLabel.getPrefWidth()) / 2, 450);
		cheatsLabel.setTouchable(Touchable.disabled);

		Button infiniteFlightButton = new Button(checkButton, checkButton, checkButtonChecked);
		Label infiniteFlightLabel = new Label("Infinite Flight", white);

		infiniteFlightButton.setPosition((stage.getWidth() + infiniteFlightLabel.getPrefWidth() + 10) / 2, 300);
		infiniteFlightButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				DuckGame.session.setInfiniteFlight();
			}
		});
		infiniteFlightButton.setChecked(DuckGame.session.isInfiniteFlight());

		infiniteFlightLabel.setPosition((stage.getWidth() - infiniteFlightLabel.getPrefWidth() - 10) / 2, 315);
		infiniteFlightLabel.setTouchable(Touchable.disabled);
		
		stage.addActor(startButton);
		stage.addActor(startLabel);
		stage.addActor(cheatsLabel);
		stage.addActor(infiniteFlightButton);
		stage.addActor(infiniteFlightLabel);
	}


	/**
	 * Main screen loop.
	 *
	 * @param delta
	 *                how much time has passed since the last update
	 */
	@Override
	public void render(float delta) {
		super.render(delta);
		stage.draw();
	}


	/**
	 * Called to dispose libGDX objects used by this LoseScreen.
	 */
	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		stage.dispose();
	}
}
