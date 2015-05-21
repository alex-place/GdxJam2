package com.gdxjam.systems;

import java.io.IOException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.gdxjam.net.GameServer;

public class ServerSystem extends EntitySystem {
	private GameServer server;

	@Override
	public synchronized void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		try {
			server = new GameServer();
			System.out.println("Server added to engine");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void update(float delta) {
		super.update(delta);
	}

	@Override
	public synchronized void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		server.shutdown();
	}
}
