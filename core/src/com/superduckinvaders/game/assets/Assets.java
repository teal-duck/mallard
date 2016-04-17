package com.superduckinvaders.game.assets;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;


/**
 * Responsible for loading game assets.
 */
public class Assets {

	/**
	 * The texture for the icon that represents the player on the minimap.
	 */
	public static TextureRegion minimapHead;

	/**
	 * Player texture sets for normal and flying.
	 */
	public static TextureSet playerNormal, playerGun, playerSwimming, playerFlying, playerSaber;
	public static TextureSet playerStaticAttackGun, playerStaticAttackSaber;
	public static TextureSet playerWalkingAttackGun, playerWalkingAttackSaber;

	public static TextureSet boss;

	/**
	 * Bad guy texture set.
	 */
	public static TextureSet rangedBadGuyNormal, badGuyNormal;

	/**
	 * Texture for Projectile.
	 */
	public static TextureRegion projectile;

	/**
	 * Texture for demented icon.
	 */
	public static TextureRegion dementedIcon;

	/**
	 * Textures for Hearts
	 */
	public static TextureRegion heartFull, heartHalf, heartEmpty;

	/**
	 * Textures for stamina.
	 */
	public static TextureRegion staminaFull, staminaEmpty;

	/**
	 * Animation for explosion.
	 */
	public static Animation explosionAnimation;

	/**
	 * Tile maps for levels.
	 */
	public static TiledMap levelOneMap, levelTwoMap, levelThreeMap, levelFourMap, levelFiveMap, levelSixMap,
			levelSevenMap, levelEightMap;

	public static TiledMap[] maps;
	/**
	 * The font for the UI.
	 */
	public static BitmapFont font;

	/**
	 * The texture for the button.
	 */
	public static TextureRegion button;

	/**
	 * The textures for the mute/unmute buttons.
	 */
	public static TextureRegion muteButtonMute, muteButtonUnmute;

	/**
	 * Textures for check buttons.
	 */
	public static TextureRegion checkButton, checkButtonChecked;

	/**
	 * Textures for floor items.
	 */
	public static TextureRegion floorItemGun, floorItemSaber, floorItemSpeed, floorItemInvulnerable, floorItemScore,
			floorItemFireRate, floorItemHeart;

	/**
	 * Texture for objective flag.
	 */
	public static TextureRegion flag;

	/**
	 * Texture for the game logo.
	 */
	public static TextureRegion logo;

	public static Sound gunShot, swimming;
	public static Music menuTheme;

	/**
	 * Responsible for loading maps.
	 */
	private static TmxMapLoader mapLoader = new TmxMapLoader();


	/**
	 * Loads all assets.
	 */
	public static void load() {
		Assets.loadPlayerTextureSets();
		Assets.loadBadGuyTextureSet();
		Assets.loadFloorItems();
		Assets.loadSFX();

		Assets.minimapHead = new TextureRegion(Assets.loadTexture("textures/minimap_head.png"));
		Assets.dementedIcon = new TextureRegion(Assets.loadTexture("textures/demented_debuff.png"));
		Assets.projectile = new TextureRegion(Assets.loadTexture("textures/projectile.png"));

		Assets.explosionAnimation = Assets.loadAnimation("textures/explosion.png", 2, 0.3f);

		Assets.levelOneMap = Assets.loadTiledMap("maps/map.tmx");
		Assets.levelTwoMap = Assets.loadTiledMap("maps/James.tmx");
		Assets.levelThreeMap = Assets.loadTiledMap("maps/Halifax.tmx");
		Assets.levelFourMap = Assets.loadTiledMap("maps/QuietPlace.tmx");
		Assets.levelFiveMap = Assets.loadTiledMap("maps/bridges.tmx");
		Assets.levelSixMap = Assets.loadTiledMap("maps/Library.tmx");
		Assets.levelSevenMap = Assets.loadTiledMap("maps/HesEast.tmx");
		Assets.levelEightMap = Assets.loadTiledMap("maps/Compsci.tmx");

		Assets.maps = new TiledMap[] { Assets.levelOneMap, Assets.levelTwoMap, Assets.levelThreeMap,
				Assets.levelFourMap, Assets.levelFiveMap, Assets.levelSixMap, Assets.levelSevenMap,
				Assets.levelEightMap };

		Assets.font = Assets.loadFont("Lato-Regular.ttf");

		TextureRegion[] hearts = TextureRegion.split(Assets.loadTexture("textures/hearts.png"), 32, 28)[0];
		Assets.heartFull = hearts[0];
		Assets.heartHalf = hearts[1];
		Assets.heartEmpty = hearts[2];

		TextureRegion[][] stamina = TextureRegion.split(Assets.loadTexture("textures/stamina.png"), 192, 28);
		Assets.staminaFull = stamina[0][0];
		Assets.staminaEmpty = stamina[1][0];

		Assets.button = new TextureRegion(Assets.loadTexture("textures/button.png"));

		Assets.muteButtonMute = new TextureRegion(Assets.loadTexture("textures/muteButtonMute.png"));
		Assets.muteButtonUnmute = new TextureRegion(Assets.loadTexture("textures/muteButtonUnmute.png"));

		Assets.checkButton = new TextureRegion(Assets.loadTexture("textures/checkButton.png"));
		Assets.checkButtonChecked = new TextureRegion(Assets.loadTexture("textures/checkButtonChecked.png"));

		Assets.flag = new TextureRegion(Assets.loadTexture("textures/flag.png"));
		Assets.logo = new TextureRegion(Assets.loadTexture("textures/logo.png"));
	}


	private static void loadSFX() {
		Assets.gunShot = Gdx.audio.newSound(Gdx.files.internal("Gun.mp3"));
		Assets.menuTheme = Gdx.audio.newMusic(Gdx.files.internal("MenuTheme.ogg"));
		Assets.swimming = Gdx.audio.newSound(Gdx.files.internal("swimming.mp3"));
		// MenuTheme.ogg is credited to SIMG, originally name Passionate.
	}


	/**
	 * Loads assets relating to the player in the normal state. If you change the player texture size, be sure to
	 * change the values here.
	 */
	private static void loadPlayerTextureSets() {
		// Load idle texture map.
		TextureRegion[][] idleAll = TextureRegion.split(Assets.loadTexture("textures/player_idle_all.png"), 28,
				18);

		TextureRegion[] idle = idleAll[0];
		TextureRegion[] idleSwimming = idleAll[1];
		TextureRegion[] idleGun = idleAll[2];
		TextureRegion[] idleSaber = idleAll[3];

		// Load walking animations.
		Animation[] baseWalks = Assets.loadAnimations("textures/player_walk_base_all.png", 28, 18, 0.2f);
		Animation[] gunWalks = Assets.loadAnimations("textures/player_walk_gun_all.png", 28, 18, 0.2f);
		Animation[] saberWalks = Assets.loadAnimations("textures/player_walk_saber_all.png", 28, 18, 0.08f);
		Animation[] flyingWalks = Assets.loadAnimations("textures/player_flying_all.png", 28, 18, 0.2f);
		Animation[] swimmingWalks = Assets.loadAnimations("textures/player_swimming_all.png", 28, 18, 0.2f);
		Animation[] SaberAttacks = Assets.loadAnimations("textures/player_walk_attack_saber_all.png", 28, 18,
				0.08f);
		Animation[] SaberAttacksStatic = Assets.loadAnimations("textures/player_static_attack_saber_all.png",
				28, 18, 0.08f);
		Animation[] GunAttacks = Assets.loadAnimations("textures/player_walk_attack_gun_all.png", 28, 18,
				0.08f);
		Animation[] GunAttacksStatic = Assets.loadAnimations("textures/player_static_attack_gun_all.png", 28,
				18, 0.08f);

		Assets.playerNormal = new TextureSet(idle, baseWalks);
		Assets.playerFlying = new TextureSet(idle, flyingWalks);
		Assets.playerSwimming = new TextureSet(idleSwimming, swimmingWalks);
		Assets.playerGun = new TextureSet(idleGun, gunWalks);
		Assets.playerSaber = new TextureSet(idleSaber, saberWalks);
		Assets.playerStaticAttackSaber = new TextureSet(SaberAttacksStatic);
		Assets.playerStaticAttackGun = new TextureSet(GunAttacksStatic);
		Assets.playerWalkingAttackSaber = new TextureSet(SaberAttacks);
		Assets.playerWalkingAttackGun = new TextureSet(GunAttacks);
	}


	/**
	 * Loads the textures from the bad guy textures file.
	 */
	private static void loadBadGuyTextureSet() {
		// Load idle texture map.
		TextureRegion[] idle = TextureRegion.split(Assets.loadTexture("textures/badguy_idle.png"), 21, 24)[0];

		// Load walking animations.
		Animation walkingFront = Assets.loadAnimation("textures/badguy_walking_front.png", 4, 0.2f);
		Animation walkingBack = Assets.loadAnimation("textures/badguy_walking_back.png", 4, 0.2f);
		Animation walkingLeft = Assets.loadAnimation("textures/badguy_walking_left.png", 4, 0.2f);
		Animation walkingRight = Assets.loadAnimation("textures/badguy_walking_right.png", 4, 0.2f);

		// Load idle texture map.
		TextureRegion[] rangedIdle = TextureRegion.split(Assets.loadTexture("textures/ranged_badguy_idle.png"),
				21, 24)[0];

		// Load walking animations.
		Animation rangedWalkingFront = Assets.loadAnimation("textures/ranged_badguy_walking_front.png", 4,
				0.2f);
		Animation rangedWalkingBack = Assets.loadAnimation("textures/ranged_badguy_walking_back.png", 4, 0.2f);
		Animation rangedWalkingLeft = Assets.loadAnimation("textures/ranged_badguy_walking_left.png", 4, 0.2f);
		Animation rangedWalkingRight = Assets.loadAnimation("textures/ranged_badguy_walking_right.png", 4,
				0.2f);

		Assets.badGuyNormal = new TextureSet(idle[0], idle[1], idle[2], idle[3], walkingFront, walkingBack,
				walkingLeft, walkingRight);
		Assets.rangedBadGuyNormal = new TextureSet(rangedIdle[0], rangedIdle[1], rangedIdle[2], rangedIdle[3],
				rangedWalkingFront, rangedWalkingBack, rangedWalkingLeft, rangedWalkingRight);

		TextureRegion[] idleBoss = TextureRegion.split(Assets.loadTexture("textures/ranged_mechaboss_idle.png"),
				42, 48)[0];

		Assets.boss = new TextureSet(idleBoss);
	}


	/**
	 * Loads the texture from the floor items file.
	 */
	public static void loadFloorItems() {
		TextureRegion[] items = TextureRegion.split(Assets.loadTexture("textures/floor_items.png"), 15, 15)[0];

		Assets.floorItemGun = items[0];
		Assets.floorItemSaber = items[1];
		Assets.floorItemSpeed = items[2];
		Assets.floorItemInvulnerable = items[3];
		Assets.floorItemScore = items[4];
		Assets.floorItemFireRate = items[5];
		Assets.floorItemHeart = items[6];
	}


	/**
	 * Loads the texture from the specified file.
	 *
	 * @param file
	 *                the file to load from
	 * @return the texture
	 */
	public static Texture loadTexture(String file) {
		return new Texture(Gdx.files.internal(file));
	}


	/**
	 * Loads the tile map from the specifed file.
	 *
	 * @param file
	 *                the file to load from
	 * @return the tile map
	 */
	public static TiledMap loadTiledMap(String file) {
		return Assets.mapLoader.load(file);
	}


	/**
	 * Loads the animation from the specified file.
	 *
	 * @param file
	 *                the file to load from
	 * @param count
	 *                how many frames are in the file
	 * @param frameDuration
	 *                how long each frame should be shown for in seconds
	 * @return the animation
	 */
	public static Animation loadAnimation(String file, int count, float frameDuration) {
		Texture texture = Assets.loadTexture(file);
		int frameWidth = texture.getWidth() / count;
		Array<TextureRegion> keyFrames = new Array<>();

		for (int i = 0; i < count; i++) {
			keyFrames.add(new TextureRegion(texture, i * frameWidth, 0, frameWidth, texture.getHeight()));
		}

		return new Animation(frameDuration, keyFrames);
	}


	/**
	 * Loads a set of animations from the specified file.
	 *
	 * @param file
	 *                the file to load from
	 * @param frameWidth
	 *                the width of an individual frame
	 * @param frameHeight
	 *                the height of an individual frame
	 * @param frameDuration
	 *                the time (in seconds) each frame spends on screen.
	 * @return the animation
	 */
	public static Animation[] loadAnimations(String file, int frameWidth, int frameHeight, float frameDuration) {
		Texture texture = Assets.loadTexture(file);

		TextureRegion[][] frames = TextureRegion.split(texture, frameWidth, frameHeight);
		int count = frames.length;

		Animation[] animations = new Animation[count];
		for (int i = 0; i < count; i++) {
			animations[i] = new Animation(frameDuration, new Array<>(frames[i]));
		}
		return animations;
	}


	/**
	 * Loads the bitmap font from the specified files.
	 *
	 * @param fontFile
	 *                the file containing information about the glyphs stored on the image file
	 * @return the bitmap font
	 */
	public static BitmapFont loadFont(String fontFile) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontFile));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 23;
		Assets.font = generator.generateFont(parameter);
		generator.dispose();
		return Assets.font;
	}
}
