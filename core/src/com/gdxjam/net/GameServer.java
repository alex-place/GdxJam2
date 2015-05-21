package com.gdxjam.net;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive;
import com.esotericsoftware.kryonet.FrameworkMessage.Ping;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.gdxjam.net.Network.AddPlayer;
import com.gdxjam.net.Network.RemovePlayer;

public class GameServer {
	Server server;

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

				if (message instanceof AddPlayer) {
					AddPlayer player = (AddPlayer) message;
					System.out.println("Player added " + player.id + " " + player.position.x + " " + player.position.y);

				} else if (message instanceof RemovePlayer) {
					RemovePlayer player = (RemovePlayer) message;
					System.out.println("Player removed " + player.id);

				} else if ((message instanceof Ping) || (message instanceof KeepAlive)) {
					System.out.println("Ping or KeepAlive message recieved");
				}

				else {
					System.out.println("Server recieved unhandled message");

				}

			}

			public void disconnected(Connection c) {
				System.out.println("Player id: " + c.getID() + " dc'ed - return time " + c.getReturnTripTime());

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

	public class ClientConnection extends Connection {

		public ClientConnection() {
		}

	}

	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new GameServer();
	}
}
