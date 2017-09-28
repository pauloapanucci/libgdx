package com.bomberman.game.util;

import com.badlogic.ashley.core.ComponentMapper;
import com.bomberman.game.components.InputComponent;
import com.bomberman.game.components.PositionComponent;
import com.bomberman.game.components.RenderComponent;

public class Mappers {
    public static final ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<InputComponent> inputMapper = ComponentMapper.getFor(InputComponent.class);
    public static final ComponentMapper<RenderComponent> renderMapper = ComponentMapper.getFor(RenderComponent.class);
}
