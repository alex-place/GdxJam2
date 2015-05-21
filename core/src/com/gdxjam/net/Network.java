package com.gdxjam.net;

import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.FrameworkMessage.Ping;

public class Network {

	static public final int port = 6464;
	static public final int portUdp = 6466;
	static public final int version = 13;

	// This registers objects that are going to be sent over the network.
	static public void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(Ping.class);
		kryo.register(Vector2.class);
		kryo.register(ArrayList.class);
		kryo.register(Entity.class);
		kryo.register(AddPlayer.class);
		kryo.register(RemovePlayer.class);

	}

	static public class AddPlayer {
		Vector2 position;
		long id;

		public AddPlayer(Vector2 position, long id) {
			this.position = position;
			this.id = id;
		}
	}

	static public class RemovePlayer {
		long id;

		public RemovePlayer(long id) {
			this.id = id;
		}
	}

}