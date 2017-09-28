package com.bomberman.game;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

	private void initializeTextures(){
		batch = new SpriteBatch();
		playerOneTexture = new Texture("player.one.png");
		playerOneRegions = new TextureRegion[2];
		for (int i = 0; i < 2; i++){
			playerOneRegions[i] = new TextureRegion(playerOneTexture, i * 16, 0, 16, 16);
		}
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

		player = new PlayerEntity(0, 0, playerOneRegions[0]);
		world.addEntity(player);

		Gdx.input.setInputProcessor(new InputMultiplexer(this, player));

		camera = new OrthographicCamera(17 * 16,17 * 16);
		camera.setToOrtho(false, 17 * 16, 17 * 16);
		camera.update();
	}


	
	@Override
	public void create () {
		initializeTextures();
		initializeWorld();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.update(Gdx.graphics.getDeltaTime());
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
