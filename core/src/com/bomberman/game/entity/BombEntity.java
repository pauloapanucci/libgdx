package com.bomberman.game.entity;

import com.badlogic.ashley.core.Entity;
import com.bomberman.game.components.HitBoxComponent;
import com.bomberman.game.components.PositionComponent;
import com.bomberman.game.components.RenderComponent;
import com.bomberman.game.components.StaticColliderComponent;
import com.bomberman.game.util.Textures;

public class BombEntity extends Entity {
    public BombEntity(int x, int y) {
        PositionComponent position = new PositionComponent();
        position.x = x;
        position.y = y;
        this.add(position);

        HitBoxComponent hitBox = new HitBoxComponent();
        hitBox.x = 0;
        hitBox.y = 0;
        hitBox.width = 16;
        hitBox.height = 16;
        this.add(hitBox);

        this.add(new StaticColliderComponent());

        RenderComponent render = new RenderComponent();
        render.animation = Textures.bombAnimation;
        this.add(render);
    }
}
