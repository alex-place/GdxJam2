package com.gdxjam.systems;

import java.io.IOException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.gdxjam.net.GameClient;

public class ClientSystem extends EntitySystem {

	private GameClient client;
	private PooledEngine engine;

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		this.engine = (PooledEngine) engine;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		client.update();
	}

	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		client.disconnect();
	}

	public void init() {
		try {
			client = new GameClient();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
