package com.gdxjam.net;

import com.badlogic.ashley.core.Entity;

public class AddPlayer {

	private Entity entity;

	public AddPlayer(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

}
