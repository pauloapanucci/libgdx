package com.bomberman.game;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bomberman.game.entity.PlayerEntity;
import com.bomberman.game.systems.MovementSystem;
import com.bomberman.game.systems.RenderSystem;

public class BombermanGame extends ApplicationAdapter implements InputProcessor{
	SpriteBatch batch;
	TextureRegion img;
	private Texture playerOneTexture;
	TextureRegion[] playerOneRegions;
	private Engine world, render;
	private PlayerEntity player;
	private OrthographicCamera camera;
	private FitViewport fitViewport;
	private ShapeRenderer shape;
	private Animation playerOneAnimation;

	private void initializeTextures(){
		shape = new ShapeRenderer();
		batch = new SpriteBatch();
		playerOneTexture = new Texture("player.one.png");
		playerOneRegions = new TextureRegion[2];
		for (int i = 0; i < 2; i++){
			playerOneRegions[i] = new TextureRegion(playerOneTexture, i * 16, 0, 16, 16);
		}

		playerOneAnimation = new Animation<TextureRegion>(0.4f, playerOneRegions);
	}

	private void initializeWorld(){
		world = new Engine();
		world.addSystem(new MovementSystem());
		world.addEntityListener(new EntityListener() {
			@Override
			public void entityAdded(Entity entity) {
				render.addEntity(entity);
			}

			@Override
			public void entityRemoved(Entity entity) {
				render.removeEntity(entity);
			}
		});

		render = new Engine();
		render.addSystem(new RenderSystem(batch));

		player = new PlayerEntity(0, 0, playerOneAnimation);
		world.addEntity(player);

		Gdx.input.setInputProcessor(new InputMultiplexer(this, player));

		camera = new OrthographicCamera();
		fitViewport = new FitViewport(17 * 16, 17 * 16, camera);
		fitViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		fitViewport.apply();

		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

//		camera.setToOrtho(false, 17 * 16, 17 * 16);
//		camera.update();
	}


	
	@Override
	public void create () {
		initializeTextures();
		initializeWorld();
	}

	@Override
	public  void resize (int width, int height){
		fitViewport.update(width, height);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.update(Gdx.graphics.getDeltaTime());

		camera.update();

		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.setColor(Color.WHITE);
		shape.rect(0, 0, 16 * 17, 16 * 17);
		shape.end();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		render.update(Gdx.graphics.getDeltaTime());
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		playerOneTexture.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Input.Keys.ESCAPE:
				Gdx.app.exit();
				break;

			case Input.Keys.R:
				initializeWorld();
				break;

			default:
				return false;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
