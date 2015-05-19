package com.gdxjam.systems;

import java.io.IOException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.gdxjam.net.AddPlayer;
import com.gdxjam.net.ClientListener;
import com.gdxjam.net.RemovePlayer;
import com.gdxjam.net.SomeRequest;
import com.gdxjam.net.SomeResponse;
import com.gdxjam.net.UpdatePlayer;

public class ClientSystem extends EntitySystem {

	private Client client;
	Entity player;

	public ClientSystem() {
	}

	public void addPlayer(Entity player) {
		this.player = player;
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		client = new Client();

		Kryo kryo = client.getKryo();
		kryo.register(SomeRequest.class);
		kryo.register(SomeResponse.class);
		kryo.register(AddPlayer.class);
		kryo.register(UpdatePlayer.class);
		kryo.register(RemovePlayer.class);

		client.start();
		client.addListener(new ClientListener(engine));

		try {
			client.connect(5000, "127.0.0.1", 54555, 54777);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SomeRequest request = new SomeRequest();
		request.text = "Here is the request";
		client.sendTCP(request);

		client.sendTCP(new AddPlayer(player));

	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (player != null)
			client.sendUDP(new UpdatePlayer(player));
	}

	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
	}

}
