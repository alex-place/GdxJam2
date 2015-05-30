package com.gdxjam.screens;

import java.io.IOException;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.gdxjam.EntityManager;
import com.gdxjam.GameManager;
import com.gdxjam.GameManager.GameConfig;
import com.gdxjam.GameManager.GameConfig.BUILD;
import com.gdxjam.components.Components;
import com.gdxjam.input.DesktopGestureListener;
import com.gdxjam.input.DesktopInputProcessor;
import com.gdxjam.input.DeveloperInputProcessor;
import com.gdxjam.input.EntityController;
import com.gdxjam.net.Network;
import com.gdxjam.systems.CameraSystem;
import com.gdxjam.systems.ClientSystem;
import com.gdxjam.systems.InputSystem;
import com.gdxjam.systems.ServerSystem;
import com.gdxjam.utils.WorldGenerator;

public class GameScreen extends AbstractScreen {

	private EntityManager engine;
	private InputSystem input;

	public GameScreen() {
	}

	@Override
	public void show() {
		engine = GameManager.initEngine();
		input = engine.getSystem(InputSystem.class);

		createWorld(1024, 1024);
		createConnection();

		input.addProcessor(new GestureDetector(new DesktopGestureListener(engine)));
		input.addProcessor(new DesktopInputProcessor(engine));

		if (GameConfig.build == BUILD.DEV) {
			input.addProcessor(new DeveloperInputProcessor());
		}

		input.addProcessor(new EntityController(engine, GameManager.getPlayer()));
		engine.getSystem(CameraSystem.class).smoothFollow(Components.PHYSICS.get(GameManager.getPlayer()).getBody().getPosition());

		Gdx.input.setInputProcessor(input.getInput());

	}

	public void createWorld(int width, int height) {
		long seed = new Random().nextLong();
		WorldGenerator generator = new WorldGenerator(width, height, seed);
		generator.generate();

	}

	public void createConnection() {
		if (Network.isServer) {
			engine.getSystem(ServerSystem.class).init();
		} else {
			engine.removeSystem(engine.getSystem(ServerSystem.class));
		}

		engine.getSystem(ClientSystem.class).init();

		engine.getSystem(CameraSystem.class).setWorldBounds(1024, 1024);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		engine.update(delta);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void hide() {
	}

}
