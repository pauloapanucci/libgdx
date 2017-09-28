package com.bomberman.game.util;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;


public class MapLoader {
    private TiledMap tiledMap;
    private MapLayer spawnLayer, obstacleLayer, collisionLayer;
    private TiledMapTileLayer floorLayer, layoutLayer;

    public List<Vector2> spawnList, obstacleList;
    public List<Rectangle> collisionList;

    public OrthogonalTiledMapRenderer renderer;

    private void loadMap (String filename) {
        tiledMap = new TmxMapLoader().load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
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
            collisionList.add(Pools.rectPool.obtain());
        }

    }

    private void createObstacles (Engine world) {

    }

    private void dispose () {
        for (Rectangle rectangle : collisionList) {
            Pools.rectPool.free(rectangle);
        }
        tiledMap.dispose();
    }

}
