package com.gdxjam.screens;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.InputMultiplexer;

public class InputSystem extends EntitySystem {

	private InputMultiplexer input = new InputMultiplexer();

	public InputSystem() {
		init();
	}

	public void init() {
		input = new InputMultiplexer();
	}

	public InputMultiplexer getMultiplexer() {
		return input;
	}

}
