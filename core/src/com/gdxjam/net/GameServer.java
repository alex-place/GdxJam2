package com.gdxjam.net;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive;
import com.esotericsoftware.kryonet.FrameworkMessage.Ping;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.gdxjam.net.Network.AddPlayer;
import com.gdxjam.net.Network.RemovePlayer;
import com.gdxjam.net.Network.UpdatePlayer;

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
					server.sendToAllExceptTCP(c.getID(), player);
					Log.debug("Player: " + player.uuid + " connected @ x: " + player.x + " y : " + player.y + " angle: " + player.rotation);
					return;
				} else if (message instanceof RemovePlayer) {
					RemovePlayer player = (RemovePlayer) message;
					server.sendToAllExceptTCP(c.getID(), player);
					Log.debug("Player: " + player.uuid + " disconnected!");
					c.close();
					return;
				} else if (message instanceof UpdatePlayer) {
					UpdatePlayer player = (UpdatePlayer) message;
					server.sendToAllExceptTCP(c.getID(), player);
					// Log.debug("Player: " + player.uuid +
					// " connected @ x: " + player.x + " y : " +
					// player.y + " angle: " + player.rotation);
					return;
				}

				else if ((message instanceof Ping) || (message instanceof KeepAlive)) {

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

	public class ClientConnection extends Connection {

		public ClientConnection() {
		}

	}

	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new GameServer();
	}
}
