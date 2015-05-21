package com.gdxjam.net;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.minlog.Log;

public class GameClient {

	Client client;

	public GameClient() throws IOException { // final GameMap game,
		client = new Client();
		new Thread(client).start();

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(client);

		client.addListener(new Listener() {
			public void connected(Connection connection) {
				System.out.print("Client connected");
			}

			public void received(Connection connection, Object object) {
				System.out.print("Client recieved unknown message");
			}

			public void disconnected(Connection connection) {
				System.out.print("Client disconnected");
				System.exit(0);
			}
		});

		client.connect(5000, "192.168.1.5", 1881, 1882);

	}

	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new GameClient();
	}
}
