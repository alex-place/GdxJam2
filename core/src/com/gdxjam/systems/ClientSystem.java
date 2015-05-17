package com.gdxjam.systems;

import java.io.IOException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.gdxjam.net.SomeRequest;
import com.gdxjam.net.SomeResponse;

public class ClientSystem extends EntitySystem {

	private Client client;

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		client = new Client();
		Kryo kryo = client.getKryo();
		kryo.register(SomeRequest.class);
		kryo.register(SomeResponse.class);
		client.start();

		try {
			client.connect(5000, "127.0.0.1", 54555, 54777);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SomeRequest request = new SomeRequest();
		request.text = "Here is the request";
		client.sendTCP(request);

	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}

	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
	}

}
