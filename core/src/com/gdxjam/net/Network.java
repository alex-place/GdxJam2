package com.gdxjam.net;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.gdxjam.components.FactionComponent.Faction;

public class Network {

	private static String ip = "127.0.0.1";
	public static boolean isServer = false;

	// This registers objects that are going to be sent over the network.
	static public void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(RequestAddPlayer.class);
		kryo.register(ReplyAddPlayer.class);
		kryo.register(Faction.class);
		kryo.register(Vector2.class);
		kryo.register(RequestUpdate.class);
		kryo.register(ReplyUpdate.class);

	}

	public static String getIP() {
		return ip;
	}

	public static void setIP(String ip) {
		Network.ip = ip;
	}

	// Standard is clients request and Server replies net messages are static
	// classes
	public static class RequestAddPlayer {
		Faction faction;

	}

	public static class ReplyAddPlayer {
		Faction faction;
		Vector2 position;
		long uuid;

	}

	public static class RequestUpdate {
		boolean forward, reverse, left, right;
		Vector2 lookAt;

	}

	public static class ReplyUpdate {
		long uuid;
		Vector2 position;
		float rotation;
	}

}