package com.gdxjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;

public class PhysicsComponent extends Component implements Poolable {
	private Body body;

	/** Can only be created by PooledEngine */
	private PhysicsComponent() {
		// private constructor
	}

	public PhysicsComponent init(Body body) {
		this.body = body;
		return this;
	}

	public Body getBody() {
		return body;
	}

	@Override
	public void reset() {
		body = null;
	}

}
