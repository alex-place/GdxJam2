package com.gdxjam.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.InputProcessor;
import com.gdxjam.components.ControlComponent;

public class EntityController implements InputProcessor {

	Entity entity;
	PooledEngine engine;
	ControlComponent controller;

	public EntityController(PooledEngine engine) {
		this(engine, engine.createEntity());
	}

	public EntityController(PooledEngine engine, Entity entity) {
		this.entity = entity;
		controller = entity.getComponent(ControlComponent.class);

	}

	@Override
	public boolean keyDown(int keycode) {

		switch (keycode) {
		case Keybinds.FORWARD:
			controller.forward();
			break;
		case Keybinds.LEFT:
			controller.left();
			break;
		case Keybinds.REVERSE:
			controller.reverse();
			break;
		case Keybinds.RIGHT:
			controller.right();
			break;
		default:
			break;

		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
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

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
