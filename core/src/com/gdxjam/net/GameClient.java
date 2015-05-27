package com.gdxjam.net;

import java.io.IOException;
import java.util.HashMap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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

	private HashMap<Long, Entity> players = new HashMap<Long, Entity>();

	public GameClient(String ip) throws IOException { // final GameMap game,
		client = new Client();
		new Thread(client).start();

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(client);

		client.addListener(new Listener() {
			public void connected(Connection connection) {
				AddPlayer player = new AddPlayer();
				entity.add(engine.createComponent(IdentifyingComponent.class).init(connection.getID()));
				player.uuid = entity.getComponent(IdentifyingComponent.class).getUuid();
				player.x = entity.getComponent(PhysicsComponent.class).getBody().getPosition().x;
				player.y = entity.getComponent(PhysicsComponent.class).getBody().getPosition().y;
				player.rotation = entity.getComponent(PhysicsComponent.class).getBody().getAngle();

				players.put(entity.getComponent(IdentifyingComponent.class).getUuid(), entity);

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

		client.connect(5000, ip, 1881, 1882);

	}

	protected synchronized void handleRecieved(Connection connection, Object message) {

		if (message instanceof AddPlayer) {
			AddPlayer player = (AddPlayer) message;

			if (players.containsKey(player.uuid)) {
				// Do nothing
			} else {

				Entity e = EntityFactory.createShip(Faction.FACTION2, new Vector2(player.x, player.y), player.uuid);
				players.put(e.getComponent(IdentifyingComponent.class).getUuid(), e);
				e.add(engine.createComponent(IdentifyingComponent.class).init(MathUtils.random(0, Long.MAX_VALUE - 1)));
				Log.debug("Player" + player.uuid + "added!");

				AddPlayer reply = new AddPlayer();
				reply.uuid = entity.getComponent(IdentifyingComponent.class).getUuid();
				reply.x = entity.getComponent(PhysicsComponent.class).getBody().getPosition().x;
				reply.y = entity.getComponent(PhysicsComponent.class).getBody().getPosition().y;
				reply.rotation = entity.getComponent(PhysicsComponent.class).getBody().getAngle();

				connection.sendTCP(reply);
				Log.debug("Returning true found a match already!");

			}
		}

		else if (message instanceof UpdatePlayer) {
			Log.debug("derping");

			UpdatePlayer player = (UpdatePlayer) message;
			Entity e = players.get(player.uuid);
			SteerableComponent steer = e.getComponent(SteerableComponent.class);
			steer.getBody().setTransform(new Vector2(player.x, player.y), player.rotation);
			Log.debug("derping");
		}

		else if (message instanceof RemovePlayer) {
			Log.debug("Player Removed!");
		}

	}

	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new GameClient("localhost");
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
