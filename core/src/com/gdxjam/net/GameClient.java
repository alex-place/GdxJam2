package com.gdxjam.net;

import java.io.IOException;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.gdxjam.components.FactionComponent.Faction;
import com.gdxjam.components.IdentifyingComponent;
import com.gdxjam.components.PhysicsComponent;
import com.gdxjam.components.SteerableComponent;
import com.gdxjam.net.Network.AddPlayer;
import com.gdxjam.net.Network.RemovePlayer;
import com.gdxjam.net.Network.UpdatePlayer;
import com.gdxjam.utils.EntityFactory;

public class GameClient {

	Client client;
	private PooledEngine engine;
	private Entity entity;
	private float x, y;

	private Array<Entity> players = new Array<Entity>();

	public GameClient() throws IOException { // final GameMap game,
		client = new Client();
		new Thread(client).start();

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(client);

		client.addListener(new Listener() {
			public void connected(Connection connection) {
				AddPlayer player = new AddPlayer();
				entity.add(engine.createComponent(IdentifyingComponent.class).init(MathUtils.random(0, Long.MAX_VALUE - 1)));
				player.uuid = entity.getComponent(IdentifyingComponent.class).getUuid();
				player.x = entity.getComponent(PhysicsComponent.class).getBody().getPosition().x;
				player.y = entity.getComponent(PhysicsComponent.class).getBody().getPosition().y;
				player.rotation = entity.getComponent(PhysicsComponent.class).getBody().getAngle();
				client.sendTCP(player);
			}

			public void received(Connection connection, Object object) {
				handleRecieved(connection, object);
			}

			public void disconnected(Connection connection) {
				RemovePlayer player = new RemovePlayer();
				player.uuid = entity.getComponent(IdentifyingComponent.class).getUuid();
				client.sendTCP(player);
			}
		});

		client.connect(5000, "192.168.1.5", 1881, 1882);

	}

	protected synchronized void handleRecieved(Connection connection, Object message) {

		if (message instanceof AddPlayer) {
			AddPlayer player = (AddPlayer) message;
			Entity e = EntityFactory.createUnit(Faction.FACTION2, new Vector2(player.x, player.y));
			e.add(engine.createComponent(IdentifyingComponent.class).init(MathUtils.random(0, Long.MAX_VALUE - 1)));
			players.add(e);
			Log.debug("Player 007 added!");

		}

		if (message instanceof UpdatePlayer) {
			UpdatePlayer player = (UpdatePlayer) message;
			for (Entity e : players) {
//				if(e.getId() == )
//				TODO loop through our players to find this one and update
			
			}
		}

		else if (message instanceof RemovePlayer) {
			Log.debug("Player Removed!");
		}

	}

	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new GameClient();
	}

	private Vector2 position;
	private UpdatePlayer updatePlayer;

	public void update() {
		position = entity.getComponent(PhysicsComponent.class).getBody().getPosition();
		if (x != position.x && y != position.y) {
			updatePlayer = new UpdatePlayer();
			updatePlayer.uuid = entity.getId();
			updatePlayer.x = position.x;
			updatePlayer.y = position.y;
			x = position.x;
			y = position.y;
			updatePlayer.rotation = entity.getComponent(PhysicsComponent.class).getBody().getAngle();
			client.sendTCP(updatePlayer);
		}

	}

	public void disconnect() throws IOException {
		client.close();
		client.dispose();
	}

	public synchronized void init(Entity player, PooledEngine engine) {
		this.entity = player;
		this.engine = engine;
	}
}
