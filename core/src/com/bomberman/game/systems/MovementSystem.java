package com.bomberman.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.bomberman.game.components.HitBoxComponent;
import com.bomberman.game.components.InputComponent;
import com.bomberman.game.components.PositionComponent;
import com.bomberman.game.components.StaticColliderComponent;
import com.bomberman.game.util.Mappers;
import com.bomberman.game.util.Pools;


public class MovementSystem extends IteratingSystem {

    private static final float DELAY = 0.1f;
    private Family collisionFamily;
    private ImmutableArray<Entity> collisionEntities;

    public MovementSystem() {
        super(Family
                .all(PositionComponent.class, InputComponent.class)
                .get());
        collisionFamily = Family
                .all(PositionComponent.class,
                        HitBoxComponent.class,
                        StaticColliderComponent.class).get();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        collisionEntities = getEngine().getEntitiesFor(collisionFamily);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        collisionEntities = null;
    }

    private boolean collides(Rectangle player) {
        for (Entity collisionEntity : collisionEntities) {
            PositionComponent colliderPosition = Mappers.positionMapper.get(collisionEntity);
            HitBoxComponent colliderHitbox = Mappers.hitBoxMapper.get(collisionEntity);
            Rectangle collisionRectangle = Pools.rectPool.obtain();
            collisionRectangle.set(
                    colliderPosition.x + colliderHitbox.x,
                    colliderPosition.y + colliderHitbox.y,
                    colliderHitbox.width,
                    colliderHitbox.height);
            if (collisionRectangle.overlaps(player))
                return true;
        }
        return false;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.positionMapper.get(entity);
        HitBoxComponent hitbox = Mappers.hitBoxMapper.get(entity);
        InputComponent input = Mappers.inputMapper.get(entity);

        input.timer -= deltaTime;

        Rectangle playerHitbox = Pools.rectPool.obtain();
        playerHitbox.set(
                position.x + hitbox.x,
                position.y + hitbox.y,
                hitbox.width,
                hitbox.height);

        if (input.timer <= 0f) {

            if (input.up) {
                int py = position.y + 16;
                playerHitbox.setY(py);
                if (!collides(playerHitbox)) {
                    position.y = py;
                    input.timer = DELAY;
                }

            } else if (input.down) {
                int py = position.y - 16;
                playerHitbox.setY(py);
                input.timer = DELAY;
                if (!collides(playerHitbox)) {
                    position.y = py;
                    input.timer = DELAY;
                }

            } else if (input.left) {
                int px = position.x - 16;
                playerHitbox.setX(px);
                input.timer = DELAY;
                if (!collides(playerHitbox)) {
                    position.y = px;
                    input.timer = DELAY;
                }

            } else if (input.right) {
                int px = position.x + 16;
                input.timer = DELAY;
                playerHitbox.setX(px);
                if (!collides(playerHitbox)) {
                    position.y = px;
                    input.timer = DELAY;
                }
            }

        }
    }
}
