package com.gdxjam.net;

import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.FrameworkMessage.Ping;

public class Network {

	// This registers objects that are going to be sent over the network.
	static public void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(Ping.class);
		kryo.register(Vector2.class);
		kryo.register(ArrayList.class);
		kryo.register(Entity.class);
		kryo.register(AddPlayer.class);
		kryo.register(RemovePlayer.class);
		kryo.register(UpdatePlayer.class);

	}

	static public class AddPlayer {
		protected long uuid;
		protected float x, y;
		protected float rotation;

	}

	static public class RemovePlayer {
		protected long uuid;
	}

	static public class UpdatePlayer {
		protected long uuid;
		protected float x, y;
		protected float rotation;
	}

}