package com.gdxjam.screens;

import java.io.IOException;
import java.util.Random;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.gdxjam.EntityManager;
import com.gdxjam.GameManager;
import com.gdxjam.GameManager.GameConfig;
import com.gdxjam.GameManager.GameConfig.BUILD;
import com.gdxjam.components.Components;
import com.gdxjam.input.DesktopGestureListener;
import com.gdxjam.input.DesktopInputProcessor;
import com.gdxjam.input.DeveloperInputProcessor;
import com.gdxjam.input.EntityController;
import com.gdxjam.net.GameServer;
import com.gdxjam.systems.CameraSystem;
import com.gdxjam.systems.ClientSystem;
import com.gdxjam.systems.InputSystem;
import com.gdxjam.utils.Constants;
import com.gdxjam.utils.EntityFactory;
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

		input.addProcessor(new GestureDetector(new DesktopGestureListener(engine)));
		input.addProcessor(new DesktopInputProcessor(engine));

		if (GameConfig.build == BUILD.DEV) {
			input.addProcessor(new DeveloperInputProcessor());
		}

		Gdx.input.setInputProcessor(input.getInput());

	}

	public void createWorld(int width, int height) {
		long seed = new Random().nextLong();
		WorldGenerator generator = new WorldGenerator(width, height, seed);
		generator.generate();

		Entity player = EntityFactory.createPlayer(Constants.playerFaction, new Vector2(100, 100), 100l);
		input.addProcessor(new EntityController(engine, player));
		engine.addEntity(player);
		
		Entity astroid = EntityFactory.createAsteroid(new Vector2(50,50), 5);
		engine.addEntity(astroid);
		
		if(Constants.getIP() == "127.0.0.1"){
			try {
				GameServer localServer = new GameServer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (!GameManager.isServer)
		try {
				engine.getSystem(ClientSystem.class).init(player);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		engine.getSystem(CameraSystem.class).smoothFollow(Components.PHYSICS.get(player).getBody().getPosition());
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
