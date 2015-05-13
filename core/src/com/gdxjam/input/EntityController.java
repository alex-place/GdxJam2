package com.gdxjam.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.InputProcessor;
import com.gdxjam.components.Components;
import com.gdxjam.components.SteerableComponent;

public class EntityController implements InputProcessor {

	Entity entity;
	PooledEngine engine;

	public EntityController(PooledEngine engine) {
		this(engine, engine.createEntity());
	}

	public EntityController(PooledEngine engine, Entity entity) {
		this.entity = entity;
	}

	@Override
	public boolean keyDown(int keycode) {

		switch (keycode) {
		case Keybinds.FORWARD:
			break;
		case Keybinds.LEFT:
			break;
		case Keybinds.REVERSE:
			break;
		case Keybinds.RIGHT:
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
