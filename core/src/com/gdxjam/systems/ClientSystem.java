package com.gdxjam.systems;

import java.io.IOException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.gdxjam.net.GameClient;

public class ClientSystem extends EntitySystem {

	private GameClient client;
	private Entity player;
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
		try {
			client.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void init(Entity player) throws IOException {
		this.player = player;
		client = new GameClient();
		client.init(player, engine);
	}

}
