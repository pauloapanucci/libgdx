package com.bomberman.game.util;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.bomberman.game.components.DestructibleComponent;
import com.bomberman.game.components.HitBoxComponent;
import com.bomberman.game.components.PositionComponent;
import com.bomberman.game.components.StaticColliderComponent;

import java.util.ArrayList;
import java.util.List;


public class MapLoader {
    private TiledMap tiledMap;
    private MapLayer spawnLayer, obstacleLayer, collisionLayer, obstacleLAyer;
    private TiledMapTileLayer floorLayer, layoutLayer;

    public List<Vector2> spawnList, requiredObstacleList, randomObstacleList;
    public List<Rectangle> collisionList;

    public OrthogonalTiledMapRenderer renderer;

    public void loadMap (String filename) {
        tiledMap = new TmxMapLoader().load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
        collisionList = new ArrayList<Rectangle>();
        floorLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Floor");
        layoutLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Layout");
        collisionLayer = tiledMap.getLayers().get("Collision");
        for (MapObject mapObject : collisionLayer.getObjects()) {
            Rectangle obtain = Pools.rectPool.obtain();
            MapProperties properties = mapObject.getProperties();
            int x = properties.get("x", Float.class).intValue();
            int y = properties.get("y", Float.class).intValue();
            int w = properties.get("width", Float.class).intValue();
            int h = properties.get("height", Float.class).intValue();
            obtain.set(x, y, w, h);
            collisionList.add(obtain);
        }

        obstacleLAyer = tiledMap.getLayers().get("Obstacles");
        requiredObstacleList = new ArrayList<Vector2>();
        randomObstacleList = new ArrayList<Vector2>();
        for (MapObject mapObject : obstacleLAyer.getObjects()) {
            MapProperties properties = mapObject.getProperties();
            int x = properties.get("x", Float.class).intValue();
            int y = properties.get("y", Float.class).intValue();
            int width = properties.get("width", Float.class).intValue();
            int height = properties.get("height", Float.class).intValue();
            String type = properties.get("type", String.class);
            for (int i = 0; i < width; i++){
                for (int j = 0; j < height; j++){
                    final int px =  i + x, py = j + y;
                    if(collisionList.stream().noneMatch((collider) -> collider.x == px && collider.y == py)){
                        if ("required".equals(type)) {
                            Vector2 obtain = Pools.vector2Pool.obtain();
                            obtain.set(px, py);
                            requiredObstacleList.add(obtain);
                        }
                        else if ("random".equals(type)){
                            Vector2 obtain = Pools.vector2Pool.obtain();
                            obtain.set(px, py);
                            randomObstacleList.add(obtain);
                        }
                    }
                }
            }
        }

    }

    public void createEntities (Engine world) {
        for (Rectangle rect : collisionList) {
            Entity entity = new Entity();

            PositionComponent position = new PositionComponent();
            position.x = ((int) rect.x);
            position.y = ((int) rect.y);
            entity.add(position);

            HitBoxComponent hitBox = new HitBoxComponent();
            hitBox.x = 0;
            hitBox.y = 0;
            hitBox.width = ((int) rect.width);
            hitBox.height = ((int) rect.height);

            entity.add(hitBox);
            entity.add(new StaticColliderComponent());

            world.addEntity(entity);
        }

        for (Vector2 vec2 : requiredObstacleList) {
            world.addEntity(createObstacle(vec2));
        }

        for (Vector2 vec2 : randomObstacleList) {
            if(MathUtils.randomBoolean()) {
                world.addEntity(createObstacle(vec2));
            }
        }
    }

    private Entity createObstacle (Vector2 vec2) {
        Entity entity = new Entity();

        PositionComponent position = new PositionComponent();
        position.x = ((int) vec2.x);
        position.y = ((int) vec2.y);
        entity.add(position);

        HitBoxComponent hitBox = new HitBoxComponent();
        hitBox.x = 0;
        hitBox.y = 0;
        hitBox.width = 16;
        hitBox.height = 16;
        entity.add(hitBox);

        entity.add(new StaticColliderComponent());
        entity.add(new DestructibleComponent());

        return entity;
    }

    public void render (OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.getBatch().begin();
        renderer.renderTileLayer(floorLayer);
        renderer.renderTileLayer(layoutLayer);
        renderer.getBatch().end();
    }

    public void dispose () {
        for (Vector2 vec2: requiredObstacleList) Pools.vector2Pool.free(vec2);
        for (Vector2 vec2: randomObstacleList) Pools.vector2Pool.free(vec2);
        for (Rectangle rectangle : collisionList) Pools.rectPool.free(rectangle);
        renderer.dispose();
        tiledMap.dispose();
    }

}
