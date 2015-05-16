package com.gdxjam.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gdxjam.components.ControlComponent;
import com.gdxjam.systems.CameraSystem;
import com.gdxjam.utils.Updateable;

public class EntityController implements InputProcessor, Updateable {

	Entity entity;
	PooledEngine engine;
	ControlComponent controller;
	CameraSystem cameraSystem;

	public EntityController(PooledEngine engine) {
		this(engine, engine.createEntity());
	}

	public EntityController(PooledEngine engine, Entity entity) {
		this.entity = entity;
		controller = entity.getComponent(ControlComponent.class);
		this.cameraSystem = engine.getSystem(CameraSystem.class);

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	Vector3 mouse;

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mouse = cameraSystem.getCamera().unproject(
				new Vector3(screenX, screenY, 0));
		controller.lookAt(new Vector2(mouse.x, mouse.y));
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update() {
		if (Gdx.input.isKeyPressed(Keybinds.FORWARD)) {
			controller.forward(Gdx.graphics.getDeltaTime());
		}
		if (Gdx.input.isKeyPressed(Keybinds.REVERSE)) {
			controller.reverse(Gdx.graphics.getDeltaTime());
		}
		if (Gdx.input.isKeyPressed(Keybinds.LEFT)) {
			controller.left(Gdx.graphics.getDeltaTime());
		}
		if (Gdx.input.isKeyPressed(Keybinds.RIGHT)) {
			controller.right(Gdx.graphics.getDeltaTime());
		}
	}
}
