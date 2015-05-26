package com.gdxjam.behaviors.control;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gdxjam.components.Components;
import com.gdxjam.components.ControlComponent.ControlBehavior;
import com.gdxjam.components.SteerableComponent;
import com.gdxjam.components.SteeringBehaviorComponent;

/**
 * The default implementation of a player controlled unit
 * 
 * @author alex-place / Nate Baker
 * */
public class DefaultControlBehavior implements ControlBehavior {

	PooledEngine engine;
	Entity entity;
	SteeringBehaviorComponent steer;
	SteerableComponent steerable;
	float speed = 15000;
	float rotation;
	float rotationSpeed = 10.0f;

	// TODO make parameters (or a param class) for ship classes (speed...)
	public DefaultControlBehavior(Entity entity, PooledEngine engine, float radius) {
		this.entity = entity;
		this.engine = engine;
		steer = Components.STEERING_BEHAVIOR.get(entity);
		steerable = engine.createComponent(SteerableComponent.class).init(Components.PHYSICS.get(entity).getBody(), radius);
		steerable.setIndependentFacing(true);
		steerable.setMaxLinearSpeed(5);
	}

	@Override
	public void forward(float delta) {
		rotation = steerable.getOrientation();
		Vector2 direction = new Vector2(MathUtils.cos(rotation), MathUtils.sin(rotation));
		if (direction.len() > 0) {
			direction.nor();
		}
		Vector2 acceleration = new Vector2(direction.x * speed * delta, direction.y * speed * delta);
		steerable.getBody().applyForce(acceleration, steerable.getBody().getWorldCenter(), true);
	}

	@Override
	public void reverse(float delta) {
		rotation = steerable.getOrientation();
		Vector2 direction = new Vector2(MathUtils.cos(rotation), MathUtils.sin(rotation));
		if (direction.len() > 0) {
			direction.nor();
		}
		Vector2 velocity = new Vector2(-direction.x * speed * delta, -direction.y * speed * delta);
		steerable.getBody().applyForce(velocity, steerable.getBody().getWorldCenter(), true);
	}

	@Override
	public void left(float delta) {
		steerable.getBody().applyAngularImpulse(0.5f, true);

	}

	@Override
	public void right(float delta) {
		steerable.getBody().applyAngularImpulse(-0.5f, true);
	}

	@Override
	public void lookAt(Vector2 position) {
	}

	@Override
	public Entity getEntity() {
		// TODO Auto-generated method stub
		return entity;
	}

}
