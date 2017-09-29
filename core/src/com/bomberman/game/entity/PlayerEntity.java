package com.bomberman.game.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bomberman.game.components.HitBoxComponent;
import com.bomberman.game.components.InputComponent;
import com.bomberman.game.components.PositionComponent;
import com.bomberman.game.components.RenderComponent;

public class PlayerEntity extends Entity implements InputProcessor{

    private InputComponent input;

    public PlayerEntity(int x, int y, Animation<TextureRegion> animation) {
        HitBoxComponent hitBox = new HitBoxComponent();
        this.add(hitBox);
        hitBox.x = 0;
        hitBox.y = 0;
        hitBox.width = 16;
        hitBox.height = 16;
        PositionComponent position = new PositionComponent();
        position.x = x;
        position.y = y;
        this.add(position);
        input = new InputComponent();
        this.add(input);
        RenderComponent render = new RenderComponent();
        render.animation = animation;
        this.add(render);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                input.up = true;
                break;

            case Input.Keys.S:
            case Input.Keys.DOWN:
                input.down = true;
                break;

            case Input.Keys.A:
            case Input.Keys.LEFT:
                input.left = true;
                break;

            case Input.Keys.D:
            case Input.Keys.RIGHT:
                input.right = true;

            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {

            case Input.Keys.W:
            case Input.Keys.UP:
                input.up = false;
                break;

            case Input.Keys.S:
            case Input.Keys.DOWN:
                input.down = false;
                break;

            case Input.Keys.A:
            case Input.Keys.LEFT:
                input.left = false;
                break;

            case Input.Keys.D:
            case Input.Keys.RIGHT:
                input.right = false;

            default:
                return true;
        }
        return true;
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
