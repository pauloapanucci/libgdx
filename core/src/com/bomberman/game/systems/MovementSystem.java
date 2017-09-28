package com.bomberman.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.bomberman.game.components.InputComponent;
import com.bomberman.game.components.PositionComponent;
import com.bomberman.game.util.Mappers;

public class MovementSystem extends IteratingSystem {

    private float DELAY = 0.25f;

    public MovementSystem() {
        super(Family.all(PositionComponent.class, InputComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.positionMapper.get(entity);
        InputComponent input = Mappers.inputMapper.get(entity);

        input.timer -= deltaTime;

        if (input.timer <= 0){
            if (input.up) {
                position.y += 16;
                input.timer = DELAY;
            }
            else if (input.down) {
                position.y -= 16;
                input.timer = DELAY;
            }
            else if (input.left) {
                position.x -= 16;
                input.timer = DELAY;
            }
            else if (input.right) {
                position.x += 16;
                input.timer = DELAY;
            }
        }
        
    }
}
