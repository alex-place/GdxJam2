package com.gdxjam.net;

import java.io.IOException;
import java.util.HashMap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive;
import com.esotericsoftware.kryonet.FrameworkMessage.Ping;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.gdxjam.components.PhysicsComponent;
import com.gdxjam.net.Network.ReplyAddPlayer;
import com.gdxjam.net.Network.ReplyUpdate;
import com.gdxjam.net.Network.RequestAddPlayer;
import com.gdxjam.net.Network.RequestUpdate;
import com.gdxjam.utils.EntityFactory;

public class GameServer {
	Server server;
	HashMap<Long, Entity> entities = new HashMap<Long, Entity>(100);
	long count = 0;

	public GameServer() throws IOException {
		Log.set(Log.LEVEL_DEBUG);

		server = new Server() {

			@Override
			protected Connection newConnection() {
				return new ClientConnection();
			}

		};

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(server);

		server.addListener(new Listener() {
			public void received(Connection c, Object message) {

				if (message instanceof RequestAddPlayer) {
					RequestAddPlayer request = (RequestAddPlayer) message;
					Entity e = EntityFactory.createPlayer(request.faction, new Vector2(100, 100), count);
					entities.put(count, e);
					count++;
					ReplyAddPlayer reply = new ReplyAddPlayer();
					reply.faction = request.faction;
					reply.position = new Vector2(100, 100);
					reply.uuid = count;
					server.sendToAllTCP(reply);
				}

				if (message instanceof RequestUpdate) {
					RequestUpdate request = (RequestUpdate) message;
					// Entity e = entities.get(request.uuid);
					// e.getComponent(PhysicsComponent.class).getBody().setTransform(request.position,
					// request.rotation);

					ReplyUpdate reply = new ReplyUpdate();
					reply.position = request.position;
					reply.rotation = request.rotation;
					reply.uuid = request.uuid;
					server.sendToAllExceptTCP(c.getID(), reply);
				}

				if ((message instanceof Ping) || (message instanceof KeepAlive)) {

				}

				else {
					System.out.println("Server recieved unhandled message");

				}

			}

			public void disconnected(Connection c) {

			}
		});

		server.bind(1881, 1882);
		server.start();
	}

	protected void logInfo(String string) {
		Log.info(string);
	}

	public void shutdown() {
		server.close();
		server.stop();
		System.out.println("Shutting down");

	}

	public void sendMessage(Object message) {
		server.sendToAllTCP(message);

	}

	public void update() {
	}

	public class ClientConnection extends Connection {

		public ClientConnection() {
		}

	}

	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new GameServer();
	}
}
