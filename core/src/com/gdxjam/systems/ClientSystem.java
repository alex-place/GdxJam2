package com.gdxjam.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.gdxjam.net.GameClient;
import com.gdxjam.net.Network.AddPlayer;
import com.gdxjam.net.Network.RemovePlayer;

public class ClientSystem extends EntitySystem {

	private GameClient client;
	private Entity entity;

	public synchronized void addPlayer(Entity entity) {
		this.entity = entity;
	}

	@Override
	public synchronized void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		client = new GameClient();
		if (entity != null)
			client.sendMessage(new AddPlayer(new Vector2(100, 100), entity.getId()));
		else
			System.err.println("You must add a player to the system before starting the game.");
	}

	@Override
	public synchronized void update(float deltaTime) {
		super.update(deltaTime);
		// client.sendMessage(new UpdatePlayer());
	}

	@Override
	public synchronized void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		client.sendMessage(new RemovePlayer(entity.getId()));
	}
}
