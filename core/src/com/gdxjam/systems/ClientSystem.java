package com.gdxjam.systems;

import java.io.IOException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.gdxjam.net.GameClient;

public class ClientSystem extends EntitySystem {

	private GameClient client;
	private Entity entity;

	@Override
	public synchronized void addedToEngine(Engine engine) {
		super.addedToEngine(engine);

	}

	public synchronized void init(Entity entity) {
		this.entity = entity;

		try {
			client = new GameClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void update(float deltaTime) {
		super.update(deltaTime);
	}

	@Override
	public synchronized void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		System.out.println("Removing this clients player id:" + entity.getId());

	}
}
