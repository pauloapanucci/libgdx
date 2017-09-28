package com.bomberman.game;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bomberman.game.entity.PlayerEntity;
import com.bomberman.game.systems.MovementSystem;
import com.bomberman.game.systems.RenderSystem;

public class BombermanGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion img;
	private Texture playerOneTexture;
	TextureRegion[] playerOneRegions;
	private Engine world, render;
	private PlayerEntity player;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

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

		playerOneTexture = new Texture("player.one.png");
		playerOneRegions = new TextureRegion[2];
		for (int i = 0; i < 2; i++){
			playerOneRegions[i] = new TextureRegion(playerOneTexture, i * 16, 0, 16, 16);
		}

		player = new PlayerEntity(0, 0, playerOneRegions[0]);
		world.addEntity(player);

		Gdx.input.setInputProcessor(player);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.update(Gdx.graphics.getDeltaTime());
		batch.begin();
		render.update(Gdx.graphics.getDeltaTime());
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		playerOneTexture.dispose();
	}
}
