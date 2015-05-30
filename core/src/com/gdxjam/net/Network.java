package com.gdxjam.net;

import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.FrameworkMessage.Ping;

public class Network {

	private static String ip = "127.0.0.1";
	public static boolean isServer = false;

	// This registers objects that are going to be sent over the network.
	static public void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(Ping.class);
		kryo.register(Vector2.class);
		kryo.register(ArrayList.class);
		kryo.register(Entity.class);

	}

	public static String getIP() {
		return ip;
	}

	public static void setIP(String ip) {
		Network.ip = ip;
	}

}