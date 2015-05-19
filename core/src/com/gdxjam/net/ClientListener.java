package com.gdxjam.net;

import com.badlogic.ashley.core.Engine;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.gdxjam.GameManager;
import com.gdxjam.screens.MainMenuScreen;

public class ClientListener extends Listener {

	Engine engine;

	public ClientListener(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void connected(Connection connection) {
		super.connected(connection);

	}

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

		if (object instanceof AddPlayer) {
			engine.addEntity(((AddPlayer) object).getEntity());
		}

		if (object instanceof RemovePlayer) {

		}

		if (object instanceof UpdatePlayer) {

		}

	}

	@Override
	public void disconnected(Connection arg0) {
		super.disconnected(arg0);
		GameManager.setScreen(new MainMenuScreen());
	}

}
