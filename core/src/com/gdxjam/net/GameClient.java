package com.gdxjam.net;

import java.io.IOException;
import java.util.HashMap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.gdxjam.GameManager;
import com.gdxjam.components.IdentifyingComponent;
import com.gdxjam.components.PhysicsComponent;
import com.gdxjam.components.FactionComponent.Faction;
import com.gdxjam.net.Network.ReplyAddPlayer;
import com.gdxjam.net.Network.ReplyUpdate;
import com.gdxjam.net.Network.RequestAddPlayer;
import com.gdxjam.net.Network.RequestUpdate;
import com.gdxjam.utils.EntityFactory;

public class GameClient {

	Client client;
	HashMap<Long, Entity> entities = new HashMap<Long, Entity>(100);

	Vector2 position;
	float x, y;

	public GameClient() throws IOException { // final GameMap game,
		client = new Client();
		new Thread(client).start();

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(client);

		client.addListener(new Listener() {
			public void connected(Connection connection) {
				RequestAddPlayer request = new RequestAddPlayer();
				request.faction = Faction.FACTION0;
				if (client.isConnected())
					client.sendTCP(request);
			}

			public void received(Connection connection, Object object) {
				handleRecieved(connection, object);
			}

			public void disconnected(Connection connection) {
				disconnect();
			}
		});

		client.connect(5000, Network.getIP(), 1881, 1882);

	}

	protected synchronized void handleRecieved(Connection connection, Object message) {
		if (message instanceof ReplyAddPlayer) {
			ReplyAddPlayer reply = (ReplyAddPlayer) message;
			Entity e = EntityFactory.createPlayer(reply.faction, reply.position, reply.uuid);
			entities.put(e.getComponent(IdentifyingComponent.class).getUuid(), e);
			if (GameManager.getPlayer() == null) {
				GameManager.setPlayer(e);
			} else {
				Log.debug("DERPING_________________________________________");
			}

		}

		if (message instanceof ReplyUpdate) {
			ReplyUpdate reply = (ReplyUpdate) message;
			if (!(GameManager.getPlayer().getComponent(IdentifyingComponent.class).getUuid() == reply.uuid)) {

				Entity e = entities.get(reply.uuid);
				if (reply.position != null)
					e.getComponent(PhysicsComponent.class).getBody().setTransform(reply.position, reply.rotation);
			}
		}

	}

	public void update() {
		RequestUpdate request = new RequestUpdate();
		request.uuid = GameManager.getPlayer().getComponent(IdentifyingComponent.class).getUuid();
		request.position = GameManager.getPlayer().getComponent(PhysicsComponent.class).getBody().getPosition();
		request.rotation = GameManager.getPlayer().getComponent(PhysicsComponent.class).getBody().getAngle();
		if (client.isConnected())
			client.sendTCP(request);
	}

	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new GameClient();
	}

	public void disconnect() {
		try {
			client.close();
			client.dispose();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
