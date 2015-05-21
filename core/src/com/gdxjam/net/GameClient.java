package com.gdxjam.net;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.minlog.Log;
import com.gdxjam.net.Network.AddPlayer;

public class GameClient {

	private Client client;
	public String remoteIP;

	public GameClient() { // final GameMap game,
		client = new Client();
		new Thread(client).start();

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(client);

		client.addListener(new ThreadedListener(new Listener() {
			public void connected(Connection connection) {
				handleConnect(connection);
			}

			public void received(Connection connection, Object object) {
				handleMessage(connection.getID(), object);
			}

			public void disconnected(Connection connection) {
				handleDisonnect(connection);
			}
		}));

	}

	protected void handleDisonnect(Connection connection) {
	}

	protected void handleConnect(Connection connection) {
		remoteIP = connection.getRemoteAddressTCP().toString();
		client.updateReturnTripTime();
	}

	public void connectLocal() {
		connect("127.0.0.1");
	}

	public void connect(String host) {
		try {
			client.connect(10000, "127.0.0.1", 1881, 1881);// ,
			System.out.println("Client connected " + client.isConnected()); // Network.portUdp);
		} catch (IOException e) {
			e.printStackTrace();
			logInfo("Can't connect to " + host);
		}
	}

	private void logInfo(String string) {
		Log.info(string);
	}

	public void sendMessage(Object message) {
		if (client.isConnected()) {
			client.sendTCP(message);
		}
	}

	public void sendMessageUDP(Object message) {
		if (client.isConnected()) {
			client.sendUDP(message);
		}
	}

	public void handleMessage(int playerId, Object message) {

		if (message instanceof AddPlayer) {
		}

	}

	public void ping() {
		if (client.isConnected()) {
			this.client.updateReturnTripTime();
		}
	}

	public void shutdown() {
		client.stop();
		client.close();
	}

	public Client getClient() {
		return client;
	}

}
