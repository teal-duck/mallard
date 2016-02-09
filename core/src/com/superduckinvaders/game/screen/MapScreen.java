package com.superduckinvaders.game.screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.superduckinvaders.game.DuckGame;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.Assets;



public class MapScreen extends BaseScreen {

    private Stage stage;


    /**
     * Start off the BaseScreen
     *
     * @param game The main game class
     * @see BaseScreen#getGame
     */
    public MapScreen(DuckGame game) {
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

        Button levelOneButton = new Button(new Button.ButtonStyle(button, button, button));
        levelOneButton.setPosition((stage.getWidth() - levelOneButton.getPrefWidth()) * 2 /10, 485);
        levelOneButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                getGame().setScreen(new GameScreen(getGame(), new Round(getGame(), Assets.levelOneMap)));

            }
        });

        Button levelTwoButton = new Button(new Button.ButtonStyle(button, button, button));
        levelTwoButton.setPosition((stage.getWidth() - levelTwoButton.getPrefWidth()) * 7 /10, 485);
        levelTwoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                getGame().setScreen(new GameScreen(getGame(), new Round(getGame(), Assets.levelOneMap)));

            }
        });

        Button levelThreeButton = new Button(new Button.ButtonStyle(button, button, button));
        levelThreeButton.setPosition((stage.getWidth() - levelThreeButton.getPrefWidth()) * 2 /10, 385);
        levelThreeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                getGame().setScreen(new GameScreen(getGame(), new Round(getGame(), Assets.levelOneMap)));

            }
        });

        Button levelFourButton = new Button(new Button.ButtonStyle(button, button, button));
        levelFourButton.setPosition((stage.getWidth() - levelFourButton.getPrefWidth()) * 7 /10, 385);
        levelFourButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                getGame().setScreen(new GameScreen(getGame(), new Round(getGame(), Assets.levelOneMap)));

            }
        });

        Label.LabelStyle white = new Label.LabelStyle(Assets.font, Color.WHITE);

        Label levelOneLabel = new Label("Level 1", white);
        levelOneLabel.setPosition((stage.getWidth() - levelOneLabel.getPrefWidth())* 27 / 100, 500);

        Label levelTwoLabel = new Label("Level 2", white);
        levelTwoLabel.setPosition((stage.getWidth() - levelTwoLabel.getPrefWidth())*65 /100, 500);
        levelTwoLabel.setTouchable(Touchable.disabled);

        Label levelThreeLabel = new Label("Level 3", white);
        levelThreeLabel.setPosition((stage.getWidth() - levelThreeLabel.getPrefWidth())* 27/ 100, 400);
        levelThreeLabel.setTouchable(Touchable.disabled);

        Label levelFourLabel = new Label("Level 4", white);
        levelFourLabel.setPosition((stage.getWidth() - levelFourLabel.getPrefWidth())* 65 / 100, 400);
        levelFourLabel.setTouchable(Touchable.disabled);

        stage.addActor(levelOneButton);
        stage.addActor(levelOneLabel);
        stage.addActor(levelTwoButton);
        stage.addActor(levelTwoLabel);
        stage.addActor(levelThreeButton);
        stage.addActor(levelThreeLabel);
        stage.addActor(levelFourButton);
        stage.addActor(levelFourLabel);
    }

    /**
     * Main screen loop.
     *
     * @param delta how much time has passed since the last update
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
