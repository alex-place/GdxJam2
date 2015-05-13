package com.gdxjam;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

/**
 * Super simple input manager
 * @author alex-place
 * */
public class InputManager {

	private static InputMultiplexer input;

	public InputManager() {
		init();
	}

	public static void init() {
		input = new InputMultiplexer();
	}

	public static void addProcessor(InputProcessor processor) {
		input.addProcessor(processor);
	}

	public static InputMultiplexer getInput() {
		return input;
	}

}
