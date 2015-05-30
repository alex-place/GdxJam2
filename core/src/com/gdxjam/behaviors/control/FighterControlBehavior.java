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
 * This is a new movement type I came up with. Will work like a fps move movement where the ship only fires frontwards. still working on it. 
 * 
 * 
 * @author alex-place / Nate Baker
 * */
public class FighterControlBehavior implements ControlBehavior {

	PooledEngine engine;
	Entity entity;
	SteeringBehaviorComponent steer;
	SteerableComponent steerable;
	float speed = 15000;
	float rotation;
	float rotationSpeed = 10.0f;

	

	// TODO make parameters (or a param class) for ship classes (speed...)
	public FighterControlBehavior(Entity entity, PooledEngine engine, float radius) {
		this.entity = entity;
		this.engine = engine;
		steer = Components.STEERING_BEHAVIOR.get(entity);
		steerable = engine.createComponent(SteerableComponent.class).init(Components.PHYSICS.get(entity).getBody(), radius);
		steerable.setIndependentFacing(true);
		steerable.setMaxLinearSpeed(5);
	}
// I will inherited this form DefaultControlBehavior if I don't need to change it.
	@Override
	public void forward(float delta) {
		rotation = steerable.getOrientation();
		Vector2 direction = new Vector2(MathUtils.cos(rotation), MathUtils.sin(rotation));
		if (direction.len() > 0) {
			direction.nor();
		}

		steerable.getBody().applyForce(new Vector2(direction.x * speed * delta, direction.y * speed * delta), 
				steerable.getBody().getWorldCenter(), true);
	}

	@Override
	public void reverse(float delta) {
		rotation = steerable.getOrientation();
		Vector2 direction = new Vector2(MathUtils.cos(rotation), MathUtils.sin(rotation));
		if (direction.len() > 0) {
			direction.nor();
		}

		steerable.getBody().applyForce(new Vector2(direction.x * -speed * delta, direction.y * -speed * delta), 
				steerable.getBody().getWorldCenter(), true);
	}
//this will have to be overridden in any case. Should be strafing but haven'r figured it up yet.
	@Override
	public void left(float delta) {
		rotation = (float)(steerable.getOrientation()+Math.PI/2);
		Vector2 direction = new Vector2(MathUtils.cos(rotation), MathUtils.sin(rotation));
		if (direction.len() > 0) {
			direction.nor();
		}

		steerable.getBody().applyForce(new Vector2(direction.x * speed * delta, direction.y * speed * delta), 
				steerable.getBody().getWorldCenter(), true);
	}

	@Override
	public void right(float delta) {
		rotation = (float)(steerable.getOrientation()-Math.PI/2);
		Vector2 direction = new Vector2(MathUtils.cos(rotation), MathUtils.sin(rotation));
		if (direction.len() > 0) {
			direction.nor();
		}

		steerable.getBody().applyForce(new Vector2(direction.x * speed * delta, direction.y * speed * delta), 
				steerable.getBody().getWorldCenter(), true);
	
	}

 	@Override
 	public void lookAt(Vector2 position) {
		float angle = MathUtils.degreesToRadians * position.sub(steerable.getPosition()).angle();
		steerable.setOrientation(angle);
 	}
	@Override
	public Entity getEntity() {
		// TODO Auto-generated method stub
		return entity;
	}

}
