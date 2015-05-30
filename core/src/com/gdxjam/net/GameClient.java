package com.gdxjam.net;

import java.io.IOException;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.gdxjam.GameManager;
import com.gdxjam.components.Components;
import com.gdxjam.net.Network.ReplyAddPlayer;
import com.gdxjam.net.Network.RequestAddPlayer;
import com.gdxjam.systems.CameraSystem;
import com.gdxjam.utils.EntityFactory;

public class GameClient {

	Client client;
	private PooledEngine engine;

	public GameClient() throws IOException { // final GameMap game,
		client = new Client();
		new Thread(client).start();

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(client);

		client.addListener(new Listener() {
			public void connected(Connection connection) {
				RequestAddPlayer request = new RequestAddPlayer();
				request.faction = GameManager.playerFaction;
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
			if (GameManager.getPlayer() == null) {
				GameManager.setPlayer(e);
				engine.getSystem(CameraSystem.class).smoothFollow(Components.PHYSICS.get(GameManager.getPlayer()).getBody().getPosition());
			}

		}

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

	public synchronized void init(PooledEngine engine) {
		this.engine = engine;
	}
}
