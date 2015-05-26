package com.gdxjam.systems;

import java.io.IOException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.esotericsoftware.minlog.Log;
import com.gdxjam.net.GameServer;

public class ServerSystem extends EntitySystem {
	GameServer server;

	Engine engine;

	public ServerSystem() {
		try {
			Log.set(Log.LEVEL_DEBUG);
			this.server = new GameServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		this.engine = engine;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}

	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
	}

}
