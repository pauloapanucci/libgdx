package com.bomberman.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.bomberman.game.components.HitBoxComponent;
import com.bomberman.game.components.PositionComponent;
import com.bomberman.game.components.StaticColliderComponent;

public class CollisionSystem extends IteratingSystem {
    public CollisionSystem() {
        super(Family.all(PositionComponent.class, HitBoxComponent.class, StaticColliderComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
