package com.superduckinvaders.game.screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
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

    public class CustomClickListener extends ClickListener{
        public TiledMap map;
        public int level;

        public CustomClickListener(TiledMap map, int level){
            this.map = map;
            this.level = level;

        }

        @Override
        public void clicked(InputEvent event, float x, float y){
            dispose();
            DuckGame.session.setLevel(level);
            getGame().setScreen(new GameScreen(getGame(), new Round(getGame(), map)));

        }
    }


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

        Button startButton = new Button(new Button.ButtonStyle(button, button, button));
        startButton.setPosition((stage.getWidth() - startButton.getPrefWidth()) * 45 /100, 585);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                getGame().setScreen(new StartScreen(getGame()));
            }
        });

        Button levelOneButton = new Button(new Button.ButtonStyle(button, button, button));
        levelOneButton.setPosition((stage.getWidth() - levelOneButton.getPrefWidth()) * 2 / 10, 485);
        levelOneButton.addListener(new CustomClickListener(Assets.levelOneMap, 1));

        Button levelTwoButton = new Button(new Button.ButtonStyle(button, button, button));
        levelTwoButton.setPosition((stage.getWidth() - levelTwoButton.getPrefWidth()) * 7 /10, 485);
        levelTwoButton.addListener(new CustomClickListener(Assets.levelOneMap, 2));

        Button levelThreeButton = new Button(new Button.ButtonStyle(button, button, button));
        levelThreeButton.setPosition((stage.getWidth() - levelThreeButton.getPrefWidth()) * 2 /10, 385);
        levelThreeButton.addListener(new CustomClickListener(Assets.levelOneMap, 3));

        Button levelFourButton = new Button(new Button.ButtonStyle(button, button, button));
        levelFourButton.setPosition((stage.getWidth() - levelFourButton.getPrefWidth()) * 7 /10, 385);
        levelFourButton.addListener(new CustomClickListener(Assets.levelOneMap, 4));

        Button levelFiveButton = new Button(new Button.ButtonStyle(button, button, button));
        levelFiveButton.setPosition((stage.getWidth() - levelFiveButton.getPrefWidth()) * 2 /10, 285);
        levelFiveButton.addListener(new CustomClickListener(Assets.levelOneMap, 5));

        Button levelSixButton = new Button(new Button.ButtonStyle(button, button, button));
        levelSixButton.setPosition((stage.getWidth() - levelSixButton.getPrefWidth()) * 7 /10, 285);
        levelSixButton.addListener(new CustomClickListener(Assets.levelOneMap, 6));

        Button levelSevenButton = new Button(new Button.ButtonStyle(button, button, button));
        levelSevenButton.setPosition((stage.getWidth() - levelSevenButton.getPrefWidth()) * 2 / 10, 185);
        levelSevenButton.addListener(new CustomClickListener(Assets.levelOneMap, 7));

        Button levelEightButton = new Button(new Button.ButtonStyle(button, button, button));
        levelEightButton.setPosition((stage.getWidth() - levelEightButton.getPrefWidth()) * 7 /10, 185);
        levelEightButton.addListener(new CustomClickListener(Assets.levelOneMap, 8));

        Label.LabelStyle white = new Label.LabelStyle(Assets.font, Color.WHITE);

        Label startLabel = new Label("RETURN TO START", white);
        startLabel.setPosition((stage.getWidth() - startLabel.getPrefWidth()) * 46 / 100, 600);
        startLabel.setTouchable(Touchable.disabled);

        Label levelOneLabel = new Label("LEVEL 1", white);
        levelOneLabel.setPosition((stage.getWidth() - levelOneLabel.getPrefWidth()) * 27 / 100, 500);
        levelOneLabel.setTouchable(Touchable.disabled);

        Label levelTwoLabel = new Label("LEVEL 2", white);
        levelTwoLabel.setPosition((stage.getWidth() - levelTwoLabel.getPrefWidth())*65 /100, 500);
        levelTwoLabel.setTouchable(Touchable.disabled);

        Label levelThreeLabel = new Label("LEVEL 3", white);
        levelThreeLabel.setPosition((stage.getWidth() - levelThreeLabel.getPrefWidth())* 27/ 100, 400);
        levelThreeLabel.setTouchable(Touchable.disabled);

        Label levelFourLabel = new Label("LEVEL 4", white);
        levelFourLabel.setPosition((stage.getWidth() - levelFourLabel.getPrefWidth())* 65 / 100, 400);
        levelFourLabel.setTouchable(Touchable.disabled);

        Label levelFiveLabel = new Label("LEVEL 5", white);
        levelFiveLabel.setPosition((stage.getWidth() - levelFiveLabel.getPrefWidth())* 27 / 100, 300);
        levelFiveLabel.setTouchable(Touchable.disabled);

        Label levelSixLabel = new Label("LEVEL 6", white);
        levelSixLabel.setPosition((stage.getWidth() - levelSixLabel.getPrefWidth())* 65 / 100, 300);
        levelSixLabel.setTouchable(Touchable.disabled);

        Label levelSevenLabel = new Label("LEVEL 7", white);
        levelSevenLabel.setPosition((stage.getWidth() - levelSevenLabel.getPrefWidth()) * 27 / 100, 200);
        levelSevenLabel.setTouchable(Touchable.disabled);

        Label levelEightLabel = new Label("LEVEL 8", white);
        levelEightLabel.setPosition((stage.getWidth() - levelEightLabel.getPrefWidth())* 65 / 100, 200);
        levelEightLabel.setTouchable(Touchable.disabled);

        stage.addActor(startButton);
        stage.addActor(startLabel);
        stage.addActor(levelOneButton);
        stage.addActor(levelOneLabel);
        stage.addActor(levelTwoButton);
        stage.addActor(levelTwoLabel);
        stage.addActor(levelThreeButton);
        stage.addActor(levelThreeLabel);
        stage.addActor(levelFourButton);
        stage.addActor(levelFourLabel);
        stage.addActor(levelFiveButton);
        stage.addActor(levelFiveLabel);
        stage.addActor(levelSixButton);
        stage.addActor(levelSixLabel);
        stage.addActor(levelSevenButton);
        stage.addActor(levelSevenLabel);
        stage.addActor(levelEightButton);
        stage.addActor(levelEightLabel);

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
