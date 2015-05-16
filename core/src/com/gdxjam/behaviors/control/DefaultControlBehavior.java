package com.gdxjam.behaviors.control;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gdxjam.components.Components;
import com.gdxjam.components.ControlComponent.ControlBehavior;
import com.gdxjam.components.SteerableComponent;
import com.gdxjam.components.SteeringBehaviorComponent;

/**
 * The default implementation of a player controlled unit
 * 
 * @author alex-place
 * */
public class DefaultControlBehavior implements ControlBehavior {

	PooledEngine engine;
	Entity entity;
	SteeringBehaviorComponent steer;
	SteerableComponent steerable;
	float speed = 500;
	float rotation;

	// TODO make parameters (or a param class) for ship classes (speed...)
	public DefaultControlBehavior(Entity entity, PooledEngine engine,
			float radius) {
		this.entity = entity;
		this.engine = engine;
		steer = Components.STEERING_BEHAVIOR.get(entity);
		steerable = engine.createComponent(SteerableComponent.class).init(
				Components.PHYSICS.get(entity).getBody(), radius);
		steerable.setIndependentFacing(true);
		steerable.setMaxLinearSpeed(5);
	}

	@Override
	public void forward(float delta) {
		rotation = steerable.getOrientation();
		Vector2 direction = new Vector2(MathUtils.cos(rotation),
				MathUtils.sin(rotation));
		if (direction.len() > 0) {
			direction.nor();
		}
		Vector2 velocity = new Vector2(direction.x * speed * delta, direction.y
				* speed * delta);
		steerable.getBody().applyForce(velocity,
				steerable.getBody().getWorldCenter(), true);
		entity.add(steerable);
	}

	@Override
	public void left(float delta) {
	}

	@Override
	public void reverse(float delta) {
		rotation = steerable.getOrientation();
		Vector2 direction = new Vector2(MathUtils.cos(rotation),
				MathUtils.sin(rotation));
		if (direction.len() > 0) {
			direction.nor();
		}
		Vector2 velocity = new Vector2(-direction.x * speed * delta,
				-direction.y * speed * delta);
		steerable.getBody().applyForce(velocity,
				steerable.getBody().getWorldCenter(), true);
		entity.add(steerable);
	}

	@Override
	public void right(float delta) {

	}

	@Override
	public void lookAt(Vector2 position) {
		float angle = MathUtils.degreesToRadians
				* position.sub(steerable.getPosition()).angle();

		steerable.setOrientation(angle);
	}

	@Override
	public Entity getEntity() {
		// TODO Auto-generated method stub
		return entity;
	}

}
