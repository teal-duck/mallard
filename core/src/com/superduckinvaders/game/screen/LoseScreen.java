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
 * Screen that is displayed when a player loses.
 */
public class LoseScreen extends BaseScreen {

	/**
	 * Stage for containing the button.
	 */
	private Stage stage;


	/**
	 * Initialises this LoseScreen.
	 *
	 * @param game
	 *                the game the screen is associated with
	 */
	public LoseScreen(DuckGame game) {
		super(game);
	}


	/**
	 * Shows this GameScreen. Called by libGDX to set up the graphics.
	 */
	@Override
	public void show() {
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);

		Drawable drawable = new TextureRegionDrawable(Assets.button);

		Button backButton = new Button(new Button.ButtonStyle(drawable, drawable, drawable));
		backButton.setPosition((stage.getWidth() - backButton.getPrefWidth()) / 2, 220);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				getGame().setScreen(new StartScreen(getGame()));
			}
		});

		Label.LabelStyle red = new Label.LabelStyle(Assets.font, Color.RED);
		Label.LabelStyle white = new Label.LabelStyle(Assets.font, Color.WHITE);

		Label titleLabel = new Label("You lose!", red);
		titleLabel.setPosition((stage.getWidth() - titleLabel.getPrefWidth()) / 2, 500);

		Label backLabel = new Label("Back to start screen", white);
		backLabel.setPosition((stage.getWidth() - backLabel.getPrefWidth()) / 2, 235);
		backLabel.setTouchable(Touchable.disabled);

		stage.addActor(backButton);
		stage.addActor(titleLabel);
		stage.addActor(backLabel);
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
