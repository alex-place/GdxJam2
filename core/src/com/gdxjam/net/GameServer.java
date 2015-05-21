package com.gdxjam.net;

import java.io.IOException;
import java.util.Random;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.gdxjam.net.Network.AddPlayer;

public class GameServer {
	Server server;
	private Random random = new Random();

	public GameServer() throws IOException {
		Log.set(Log.LEVEL_DEBUG);

		server = new Server() {
			protected Connection newConnection() {
				// By providing our own connection implementation, we
				// can store
				// per
				// connection state without a connection ID to state
				// look up.
				return new GameConnection();
			}
		};

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(server);

		server.addListener(new Listener() {
			public void received(Connection c, Object message) {
				System.out.println("Something");

				GameConnection connection = (GameConnection) c;

				if (message instanceof AddPlayer) {
					AddPlayer player = (AddPlayer) message;
					System.out.println("Player added " + player.id + " " + player.position.x + " " + player.position.y);

				} else {
					System.out.println("Server recieved unhandled message");

				}

			}

			public void disconnected(Connection c) {
				System.out.println("Someone disconnected");

				GameConnection connection = (GameConnection) c;
				if (connection.name != null) {
					// Announce to everyone that someone has left.

				}
			}
		});

		server.bind(Network.port, Network.portUdp);
		server.start();
	}

	protected void logInfo(String string) {
		Log.info(string);
	}

	// Connection specific attributes
	static class GameConnection extends Connection {
		public String name;
	}

	public void shutdown() {
		server.close();
		server.stop();
		System.out.println("Shutting down");

	}

	public void sendMessage(Object message) {
		server.sendToAllTCP(message);
	}
}
