package com.gdxjam;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.gdxjam.utils.Updateable;

/**
 * Super simple input manager
 * 
 * @author alex-place
 * */
public class InputSystem extends EntitySystem {

	private InputMultiplexer input;

	public InputSystem() {
		init();
	}

	@Override
	public void addedToEngine(Engine engine) {
		// TODO Auto-generated method stub
		super.addedToEngine(engine);
		init();
	}

	public void init() {
		input = new InputMultiplexer();
	}

	public void addProcessor(InputProcessor processor) {
		input.addProcessor(processor);
	}

	public InputMultiplexer getInput() {
		return input;
	}

	public void update(float delta) {
		for (InputProcessor inputs : input.getProcessors()) {
			if (inputs instanceof Updateable) {
				((Updateable) inputs).update();
			}
		}
	}

}
