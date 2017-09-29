package com.bomberman.game.util;

import com.badlogic.ashley.core.ComponentMapper;
import com.bomberman.game.components.*;

public class Mappers {
    public static final ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<InputComponent> inputMapper = ComponentMapper.getFor(InputComponent.class);
    public static final ComponentMapper<RenderComponent> renderMapper = ComponentMapper.getFor(RenderComponent.class);
    public static final ComponentMapper<HitBoxComponent> hitBoxMapper = ComponentMapper.getFor(HitBoxComponent.class);
    public static final ComponentMapper<ExplosionComponent> explosionMapper = ComponentMapper.getFor(ExplosionComponent.class);
}
