package com.gdxjam.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.gdxjam.GameManager;
import com.gdxjam.GameManager.GameConfig;
import com.gdxjam.GameManager.GameConfig.BUILD;
import com.gdxjam.InputManager;
import com.gdxjam.ecs.EntityManager;
import com.gdxjam.input.DesktopGestureListener;
import com.gdxjam.input.DeveloperInputProcessor;
import com.gdxjam.systems.CameraSystem;
import com.gdxjam.utils.WorldGenerator;

public class GameScreen extends AbstractScreen {

	private EntityManager engine;

	public GameScreen() {
	}

	@Override
	public void show() {
		engine = GameManager.initEngine();

		createWorld(256, 256);

		InputManager.addProcessor(new GestureDetector(
				new DesktopGestureListener(engine)));

		if (GameConfig.build == BUILD.DEV) {
			InputManager.addProcessor(new DeveloperInputProcessor());
		}

		Gdx.input.setInputProcessor(InputManager.getInput());

	}

	public void createWorld(int width, int height) {
		long seed = new Random().nextLong();
		WorldGenerator generator = new WorldGenerator(width, height, seed);
		generator.generate();
		engine.getSystem(CameraSystem.class).getCamera().position.set(
				width * 0.5f, height * 0.5f, 0);
		engine.getSystem(CameraSystem.class).setWorldBounds(width, height);

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
