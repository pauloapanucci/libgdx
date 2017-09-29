package com.bomberman.game.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Textures {
    public static TextureRegion img;

    public static Animation playerOneAnimation;
    public static  Texture playerOneTexture;
    public static  TextureRegion[] playerOneRegions;

    public static  Texture obstacleTexture;
    public static Animation obstacleAnimation;
    public static TextureRegion obstacleRegion;

    public static Texture bombTexture;
    public static  TextureRegion[] bombRegions;
    public static Animation<TextureRegion> bombAnimation;
    public static TextureRegion bombUp, bombDown, bombLeft, bombRight, bombVertical, bombHorizontal, bombCenter;
}
