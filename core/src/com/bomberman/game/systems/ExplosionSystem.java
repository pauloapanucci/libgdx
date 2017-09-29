package com.bomberman.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.bomberman.game.components.DestructibleComponent;
import com.bomberman.game.components.ExplosionComponent;
import com.bomberman.game.components.HitBoxComponent;
import com.bomberman.game.components.PositionComponent;
import com.bomberman.game.util.Mappers;

public class ExplosionSystem extends IteratingSystem {
    private Family destructibleFamily;
    private ImmutableArray<Entity> destructibleEntities;

    public ExplosionSystem() {
        super(Family.all(PositionComponent.class, ExplosionComponent.class).get());
        destructibleFamily = Family.all(PositionComponent.class, HitBoxComponent.class, DestructibleComponent.class).get();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        destructibleEntities = engine.getEntitiesFor(destructibleFamily);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        destructibleEntities = null;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ExplosionComponent explosion = Mappers.explosionMapper.get(entity);
        explosion.timer -= deltaTime;

        if (explosion.timer <= 0f){

        }

    }
}
