package com.bomberman.game.components;

import com.badlogic.ashley.core.Component;

public class InputComponent implements Component {
    public boolean left, right, up, down, bomb;
    public float timer;
}