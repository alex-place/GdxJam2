package com.gdxjam.net;

import com.badlogic.ashley.core.Entity;
import com.gdxjam.components.SteerableComponent;

public class UpdatePlayer {

	private Entity entity;
	private SteerableComponent steerable;

	public UpdatePlayer(Entity entity) {
		this.entity = entity;
		steerable = entity.getComponent(SteerableComponent.class);
	}

	public Entity getEntity() {
		return entity;
	}

	public SteerableComponent getSteerable() {
		return steerable;
	}

}
