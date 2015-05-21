package com.gdxjam.net;

import java.io.IOException;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class GameClient {

	private Client client;
	public String remoteIP;

	private Random random = new Random();

	public GameClient() { // final GameMap game,
		client = new Client();
		new Thread(client).start();
		// InetAddress found = client.discoverHost(Network.portUdp, 5000);
		// System.out.println(found.toString());

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(client);

		client.addListener(new Listener() {
			public void connected(Connection connection) {
				handleConnect(connection);
			}

			public void received(Connection connection, Object object) {
				handleMessage(connection.getID(), object);
			}

			public void disconnected(Connection connection) {
				handleDisonnect(connection);
			}
		});

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
			client.connect(0, host, Network.port, Network.portUdp);// ,
													// Network.portUdp);
		} catch (IOException e) {
			// e.printStackTrace();
			logInfo("Can't connect to " + host);
		}
	}

	private void logInfo(String string) {
		Log.info(string);
	}

	public void tick() {
		// nothing to do
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

		// if (message instanceof LogMessage) {
		// LogMessage msg = (LogMessage) message;
		// map.setStatus(msg.message);
		// } else

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
