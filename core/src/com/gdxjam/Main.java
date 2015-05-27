package com.gdxjam;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.gdxjam.screens.SplashScreen;

public class Main extends Game {

	private final boolean isServer;

	public Main(boolean isServer) {
		this.isServer = isServer;
	}

	@Override
	public void create() {
		GameManager.isServer = isServer;
		GameManager.init(this);
		GameManager.setScreen(new SplashScreen());
	}

}
