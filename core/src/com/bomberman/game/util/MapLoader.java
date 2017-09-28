package com.bomberman.game.util;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.List;

public class MapLoader {
    private TiledMap tiledMap;
    private MapLayer spawnLayer, obstacleLayer, collisionLayer;
    private TiledMapTileLayer floorLayer, layoutLayer;

    public List<Vector2> spawnList, obstacleList;
    public List<Rectangle>  collisionList;

    public OrthogonalTiledMapRenderer renderer;

    private void loadMap (String filename) {

    }

    private void createObstacles (Engine world) {

    }

    private void dispose () {}

}
